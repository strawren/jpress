/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-9-16 下午04:31:57
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-9-16        Initailized
 */

package com.strawren.bsp.orm.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.strawren.bsp.core.Constants;


/**
 * 东方亿付查询查询共用类
 *
 */
public class BspCriterion implements Serializable {

    private static final long serialVersionUID = -7164611833406678484L;
    protected Log log = LogFactory.getLog(getClass());

    public static final String ACS = "ACS";
    public static final String DESC = "DESC";

    private String order = DESC;
    private String orderBy;

    @SuppressWarnings("unchecked")
    private List<PropertyFilter> criteria = Collections.EMPTY_LIST;

    private String customSql;

    //嵌套的扩展查询条件
    @SuppressWarnings("unchecked")
    private List<PropertyFilter> extraCriteria = Collections.EMPTY_LIST;

    public BspCriterion(){

    }

    public BspCriterion(List<PropertyFilter> criteria){
        this.criteria = criteria;
    }

    public BspCriterion(List<PropertyFilter> criteria, String orderBy){
        this.criteria = criteria;
        this.orderBy = orderBy;
    }

    public BspCriterion(List<PropertyFilter> criteria, String orderBy, String order){
        this.criteria = criteria;
        this.orderBy = orderBy;
        this.order = order;
    }


    /**
     * Property accessor of order
     *
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Property accessor of orderBy
     *
     * @return the orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy
     *            the orderBy to set
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Property accessor of criteria
     *
     * @return the criteria
     */
    public List<PropertyFilter> getCriteria() {
        return criteria;
    }

    /**
     * @param criteria
     *            the criteria to set
     */
    public void setCriteria(List<PropertyFilter> criteria) {
        this.criteria = criteria;
    }

    public String getWhereSqlString(){
        return constructSqlString(criteria);
    }

    public void setCustomCriteria(String sql) {
        this.customSql = sql;
    }

    public String getCustomSqlString(){
        return customSql;
    }

    public String getExtraWhereSqlString(){
        return constructSqlString(extraCriteria);
    }

    public String getOrderBySqlString(){
        if(StringUtils.isNotBlank(orderBy)){
            //拼装order条件
            StringBuilder sb = new StringBuilder(" ORDER BY ");
            String[] orderBys = StringUtils.split(orderBy, ',');
            if(StringUtils.isBlank(order)){
                order = DESC;
            }
            String [] orders = StringUtils.split(order, ',');

            int i = 0;
            for (String orderBy : orderBys) {
                if(i != 0){
                    sb.append(" " + Constants.COMMA_SIGN_SPLIT_NAME + " ");
                }
                sb.append(orderBy);

                String curOrder = DESC;
                if(orders.length == 1){
                    curOrder = orders[0];
                }
                else{
                    if((i + 1) > orders.length){
                        curOrder = DESC;
                    }
                    else{
                        curOrder = orders[i];
                    }
                }
                sb.append(" " + curOrder);
                i++;
            }
            log.debug("getOrderBySqlString :" + sb.toString());
            return sb.toString();
        }

        return "";
    }

    public List<PropertyFilter> getExtraCriteria() {
        return extraCriteria;
    }

    public void setExtraCriteria(List<PropertyFilter> extraCriteria) {
        this.extraCriteria = extraCriteria;
    }

    protected String constructSqlString(List<PropertyFilter> in){
        //查询参数
        if(in != null && in.size() > 0){
            StringBuilder sb = new StringBuilder();
            for(PropertyFilter filter : in){
                sb.append("and" + filter.getSqlString());
            }
            log.debug("getWhereSqlString :" + sb.toString());
            return sb.toString();
        }

        return "";
    }
}
