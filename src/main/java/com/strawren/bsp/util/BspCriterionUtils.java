/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-9-22 下午10:19:23
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-9-22        Initailized
 */

package com.strawren.bsp.util;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;


/**
 * OecsCriterion 工具类
 *
 */
public class BspCriterionUtils {

    private BspCriterionUtils(){

    }

    public static BspCriterion createOecsCriterion(Page<?> page, List<PropertyFilter> filters){
        BspCriterion criterion = new BspCriterion();

        //设置排序参数
        if(page.isOrderBySetted()){
            criterion.setOrderBy(page.getOrderField());
            criterion.setOrder(page.getOrderDirection());
        }
        //设置查询参数
        if(filters != null && filters.size() > 0){
            criterion.setCriteria(filters);
        }

        return criterion;
    }

    public static BspCriterion createOecsCriterion(Page<?> page, String sql){
        BspCriterion criterion = new BspCriterion();

        //设置排序参数
        if(page.isOrderBySetted()){
            criterion.setOrderBy(page.getOrderField());
            criterion.setOrder(page.getOrderDirection());
        }
        //设置查询参数
        if(sql != null && sql !=""){
            criterion.setCustomCriteria(sql);
        }

        return criterion;
    }

    public static RowBounds createRowBounds(Page<?> page){
        RowBounds rowBounds = null;

        //设置分页参数
        if(page.isAutoPage()){
            int limit = (page.getPageNum() - 1) * page.getNumPerPage();
            rowBounds = new RowBounds(limit, page.getNumPerPage());
        }

        return rowBounds;
    }
}
