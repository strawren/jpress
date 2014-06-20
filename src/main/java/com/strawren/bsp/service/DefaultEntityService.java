/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-9-17 下午4:03:53
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-9-17        Initailized
 */

package com.strawren.bsp.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.core.Model;
import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.util.BspCriterionUtils;
import com.strawren.bsp.util.DateTimeUtils;
import com.strawren.bsp.util.ModelUpdInfoUtils;
import com.strawren.jpress.util.ModelInfoUtils;

/**
 * 领域对象的业务管理类基类.
 *
 * 提供了领域对象的简单CRUD方法.
 *
 * @param <T> 领域对象类型
 * @param <PK> 领域对象的主键类型
 *
 */
@Transactional
public abstract class DefaultEntityService <T extends Model, PK extends Serializable>{
    protected Log log = LogFactory.getLog(getClass());

    /**
     * 在子类实现的回调函数,为EntityManager提供默认CRUD操作所需的DAO.
     */
    protected abstract Dao<T, PK> getDao();

    // CRUD函数 //
    @Transactional(readOnly = true,timeout=30)
    public T get(PK id) {
        return getDao().get(id);
    }

    @Transactional(timeout=30)
    public void save(T entity) {
        getDao().save(entity);
    }

    @Transactional(timeout=30)
    public void update(T entity) {
        update(entity, false);
    }

    @Transactional(timeout=30)
    @SuppressWarnings("unchecked")
    //是否以秒为精度，否则以毫秒为精度，默认以毫秒为精度
    public void update(Model entity, boolean secondPrecision) {
        Serializable pks = entity.getPK();
        if(pks != null){
            try{
                T dbEntity = getDao().get((PK)pks);
                if(dbEntity != null){
                    Date dbDate = ModelInfoUtils.getModelUpdateTime(dbEntity);
                    log.debug("cms dbDate :" + dbDate.getTime());
                    Date date = ModelInfoUtils.getModelUpdateTime(entity);
                    log.debug("cms modelDate :" + date.getTime());
                    if(dbDate != null){
                        //如果不相符，则为脏数据
                        if(!dbDate.equals(date)){
                            if(!secondPrecision){
                                log.warn("the date (not secondPrecision) is dirty :" + entity);
                                throw new IllegalStateException("the date is dirty");
                            }
                            else{
                                //以秒为精度
                                long diff = DateTimeUtils.diffTwoDateWithTime(date, dbDate);
                                if(diff/1000 != 0){
                                    log.warn("the date (secondPrecision) is dirty :" + entity);
                                    throw new IllegalStateException("the date is dirty");
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception e){
                log.info(e.getMessage(), e);
                if(e instanceof IllegalStateException){
                    throw ((IllegalStateException)e);
                }
            }
        }

        //设置更新时间
        ModelInfoUtils.updateModelUpdateTime(entity);
        getDao().update((T)entity);
    }

    @Transactional(timeout=30)
    public void delete(PK id) {
        getDao().delete(id);
    }

    /**
     * 按id逻辑删除对象.
     */
    @Transactional(timeout=30)
    public void logicDel(final PK id){
        T t =get(id);
        ModelUpdInfoUtils.setModelInvalidStatus(t);
        getDao().update(t);
    }

    @Transactional(timeout=30)
    public void delete(List<PK> ids) {
        if(ids != null && ids.size() > 0){
            Dao<T, PK> dao = getDao();
            for(PK id : ids){
                dao.delete(id);
            }
        }
    }

    @Transactional(readOnly = true,timeout=30)
    public List<T> getAll() {
        BspCriterion criterion = new BspCriterion();
        return getDao().find(criterion, null);
    }

    @Transactional(readOnly = true,timeout=30)
    public Page<T> getAll(Page<T> page) {
        return search(page, null);
    }

    @Transactional(readOnly = true)
    public Page<T> search(Page<T> page, List<PropertyFilter> filters) {
        BspCriterion criterion = BspCriterionUtils.createOecsCriterion(page, filters);
        RowBounds rowBounds = BspCriterionUtils.createRowBounds(page);

        //开始查询
        List<T> result = getDao().find(criterion, rowBounds);
        page.setResult(result);

        //处理是否需要统计记录数
        if(page.isAutoCount()){
            page.setTotalCount(getDao().count(criterion));
        }

        return page;
    }

    @Transactional(readOnly = true)
    public List<T> search(List<PropertyFilter> filters) {
        return search(filters, null);
    }

    @Transactional(readOnly = true)
    public List<T> search(List<PropertyFilter> filters ,List<Order> orders) {
        BspCriterion criterion = new BspCriterion(filters);

        if(orders != null){
            StringBuilder orderBySb = new StringBuilder();
            StringBuilder orderSb =  new StringBuilder();

            int i = 0;
            for(Order order : orders){
                if(i != 0){
                    orderBySb.append(Constants.COMMA_SIGN_SPLIT_NAME);
                    orderSb.append(Constants.COMMA_SIGN_SPLIT_NAME);
                }
                i++;
                orderBySb.append(order.getOrderBy());
                orderSb.append(order.getOrder());
            }
            criterion.setOrderBy(orderBySb.toString());
            criterion.setOrder(orderSb.toString());
        }

        return getDao().find(criterion, null);
    }
}
