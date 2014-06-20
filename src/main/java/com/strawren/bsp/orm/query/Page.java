/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-9-17 下午3:41:36
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-9-17        Initailized
 * zhouxushun 2012-5-3        考虑电视分页的特殊性，MIN_PAGESIZE 大小由原来的5改成4
 * 
 */

package com.strawren.bsp.orm.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 注意所有序号从1开始.
 * 
 * @param <T> Page中记录的类型.
 */
public class Page<T> implements Serializable{

private static final long serialVersionUID = -8126702682606137927L;
    public static final int MIN_PAGESIZE =          4;
    public static final int DEFAULT_PAGESIZE =      20;
    public static final int MAX_PAGESIZE =          1000;
    public static final int DEFAULT_PAGENUMSHOWN =          10;
    // 公共变量
    public static final String ASC =                "asc";
    public static final String DESC =               "desc";
    
    //分页参数
    protected int pageNum = 1;
    protected int numPerPage = DEFAULT_PAGESIZE;
    protected String orderField = null;
    protected String orderDirection = ASC;
    protected boolean autoCount = true;
    protected boolean autoPage = true;
    protected int pageNumShown = DEFAULT_PAGENUMSHOWN;
    //返回结果
    protected List<T> result = null;
    protected long totalCount = -1;

    //扩展记录
    protected Object extraObj = null;
    
    // 构造函数
    public Page() {
        super();
    }

    public Page(final int pageSize) {
        setNumPerPage(pageSize);
    }

    public Page(final int pageSize, final boolean autoCount) {
        setNumPerPage(pageSize);
        this.autoCount = autoCount;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;

        if (pageNum < 1) {
            this.pageNum = 1;
        }
    }

    /**
     * 获得每页的记录数量,默认为20.
     */
    public int getNumPerPage() {
        return numPerPage;
    }

    /**
     * 设置每页的记录数量,超出MIN_PAGESIZE与MAX_PAGESIZE范围时会自动调整.
     */
    public void setNumPerPage(final int numPerPage) {
        this.numPerPage = numPerPage;

        if (numPerPage < MIN_PAGESIZE) {
            this.numPerPage = MIN_PAGESIZE;
        }
        if (numPerPage > MAX_PAGESIZE) {
            this.numPerPage = MAX_PAGESIZE;
        }
    }

    /**
    * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
    */
    public int getFirst() {
        return ((pageNum - 1) * numPerPage);
    }

    /**
     * 获得排序字段,无默认值.多个排序字段时用','分隔,仅在Criterion查询时有效.
     */
    public String getOrderField() {
        return orderField;
    }

    /**
     * 设置排序字段.多个排序字段时用','分隔.仅在Criterion查询时有效.
     */
    public void setOrderField(final String orderField) {
        this.orderField = orderField;
    }

    /**
     * 是否已设置排序字段,仅在Criterion查询时有效.
     */
    public boolean isOrderBySetted() {
        return StringUtils.isNotBlank(orderField);
    }

    /**
     * 获得排序方向,默认为asc,仅在Criterion查询时有效.
     * 
     * @param order 可选值为desc或asc,多个排序字段时用','分隔.
     */
    public String getOrderDirection() {
        return orderDirection;
    }

    /**
     * 设置排序方式向,仅在Criterion查询时有效.
     * 
     * @param order 可选值为desc或asc,多个排序字段时用','分隔.
     */
    public void setOrderDirection(final String orderDirection) {
        //检查order字符串的合法值
        String[] orders = StringUtils.split(orderDirection, ',');
        for (String orderStr : orders) {
            if (!StringUtils.equalsIgnoreCase(DESC, orderStr) && !StringUtils.equalsIgnoreCase(ASC, orderStr))
                throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
        }

        this.orderDirection = orderDirection.toLowerCase();
    }

    /**
     * 取得分页参数的组合字符串.
     * 将多个分页参数组合成一个字符串方便在页面上的传递,格式为pageNo|orderBy|order.
     */
    public String getPageRequest() {
        return getPageNum() + "|" + StringUtils.defaultString(getOrderField()) + "|" + getOrderDirection();
    }

    /**
     * 设置分页参数的组合字符串.
     * 将多个分页参数组合成一个字符串方便在页面上的传递,格式为pageNo|orderBy|order.
     */
    public void setPageRequest(final String pageRequest) {

        if (StringUtils.isBlank(pageRequest))
            return;

        String[] params = StringUtils.splitPreserveAllTokens(pageRequest, '|');

        if (StringUtils.isNumeric(params[0])) {
            setPageNum(Integer.valueOf(params[0]));
        }

        if (StringUtils.isNotBlank(params[1])) {
            setOrderField(params[1]);
        }

        if (StringUtils.isNotBlank(params[2])) {
            setOrderDirection(params[2]);
        }
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数,默认为false,仅在Criterion查询时有效.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数,仅在Criterion查询时有效.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    // 查询结果函数

    /**
     * 取得页内的记录列表.
     */
    public List<T> getResult() {
        if (result == null)
            return Collections.emptyList();
        return result;
    }

    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 取得总记录数,默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数,默认值为-1.
     */
    public long getTotalPages() {
        if (totalCount < 0)
            return -1;

        long count = totalCount / numPerPage;
        if (totalCount % numPerPage > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNum + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号,序号从1开始.
     */
    public int getNextPage() {
        if (isHasNext())
            return pageNum + 1;
        else
            return pageNum;
    }

    /**
     * 是否还有上一页. 
     */
    public boolean isHasPre() {
        return (pageNum - 1 >= 1);
    }

    /**
     * 取得上页的页号,序号从1开始.
     */
    public int getPrePage() {
        if (isHasPre())
            return pageNum - 1;
        else
            return pageNum;
    }

    /**
     * 取得反转的排序方向.
     * 如果有以','分隔的多个排序方向,逐一进行反转.
     */
    public String getInverseOrder() {
        String[] orders = StringUtils.split(orderDirection, ',');

        for (int i = 0; i < orders.length; i++) {
            if (DESC.equals(orders[i])) {
                orders[i] = ASC;
            } else {
                orders[i] = DESC;
            }
        }
        return StringUtils.join(orders);
    }

    public boolean isAutoPage() {
        return autoPage;
    }

    public void setAutoPage(boolean autoPage) {
        this.autoPage = autoPage;
    }

    public Object getExtraObj() {
        return extraObj;
    }

    public void setExtraObj(Object extraObj) {
        this.extraObj = extraObj;
    }

    
    public int getPageNumShown() {
        return pageNumShown;
    }

    
    public void setPageNumShown(int pageNumShown) {
        this.pageNumShown = pageNumShown;
    }
    
    
}
