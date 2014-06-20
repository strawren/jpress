/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-9-17 下午5:15:39
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-9-17        Initailized
 */

package com.strawren.bsp.orm.query;

import org.apache.commons.lang.StringUtils;


/**
 * SQL 排序对象
 *
 */
public class Order {
    //排序字段
    private String orderBy;
    //排序方式
    private String order;
    
    public Order(){
        
    }
    
    public Order(String orderBy, String order){
        this.orderBy = orderBy;
        setOrder(order);
    }

    public String getOrderBy() {
        return orderBy;
    }
    
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        if(StringUtils.isBlank(order)){
            this.order = Page.DESC;
            return;
        }
        if(Page.ASC.equalsIgnoreCase(order)){
            this.order = Page.ASC;
            return;
        }
        this.order = Page.DESC;
    }
}
