/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-9-16 下午12:43:02
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-9-16        Initailized
 */

package com.strawren.bsp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;


/**
 * TODO Add class descriptions
 *
 */
public class PropertyFilterUtils {
    /**
     * 根据对象ID集合,整理合并集合.
     * 
     * 默认对象主键的名称名为"id".
     * 
     * @see #mergeByCheckedIds(Collection, Collection, Class, String)
     */
    public static <T, ID> void mergeByCheckedIds(final Collection<T> srcObjects, 
                                                 final Collection<ID> checkedIds,final Class<T> clazz) {
        mergeByCheckedIds(srcObjects, checkedIds, clazz, "id");
    }

    /**
     * 根据对象ID集合,整理合并集合.
     * 
     * 页面发送变更后的子对象id列表时,删除原来的子对象集合再根据页面id列表创建一个全新的集合这种看似最简单的做法是不行的.
     * 因此采用如此的整合算法：在源集合中删除id不在目标集合中的对象,根据目标集合中的id创建对象并添加到源集合中.
     * 因为新建对象只有ID被赋值, 因此本函数不适合于cascade-save-or-update自动持久化子对象的设置.
     * 
     * @param srcObjects 源集合,元素为对象.
     * @param checkedIds  目标集合,元素为ID.
     * @param clazz  集合中对象的类型
     * @param idName 对象主键的名称
     */
    public static <T, ID> void mergeByCheckedIds(final Collection<T> srcObjects, final Collection<ID> checkedIds,
                                                 final Class<T> clazz, final String idName) {

        //参数校验
        AssertUtils.notNull(srcObjects, "scrObjects can not be null");
        AssertUtils.hasText(idName, "idName can not be null");
        AssertUtils.notNull(clazz, "clazz can not be null");

        //目标集合为空, 删除源集合中所有对象后直接返回.
        if (checkedIds == null) {
            srcObjects.clear();
            return;
        }

        //遍历源集合,如果其id不在目标ID集合中的对象,进行删除.
        //同时,在目标集合中删除已在源集合中的id,使得目标集合中剩下的id均为源集合中没有的id.
        Iterator<T> srcIterator = srcObjects.iterator();
        try {

            while (srcIterator.hasNext()) {
                T element = srcIterator.next();
                Object id;
                id = PropertyUtils.getProperty(element, idName);

                if (!checkedIds.contains(id)) {
                    srcIterator.remove();
                } 
                else {
                    checkedIds.remove(id);
                }
            }

            //ID集合目前剩余的id均不在源集合中,创建对象,为id属性赋值并添加到源集合中.
            for (ID id : checkedIds) {
                T obj = clazz.newInstance();
                PropertyUtils.setProperty(obj, idName, id);
                srcObjects.add(obj);
            }
        } 
        catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
     * 默认Filter属性名前缀为filter_.
     * 
     * @see #buildPropertyFilters(HttpServletRequest, String)
     */
    public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request) {
        return buildPropertyFilters(request, "filter_");
    }

    //目前不支持and 和 or 的方式
    public static List<PropertyFilter> buildMutilPropertyFilters(final HttpServletRequest request) {
    	String columnsNames = request.getParameter("searchColumnNames");
    	if(StringUtils.isNotBlank(columnsNames)) {
    		String [] columnsNameArr = columnsNames.split(Constants.COMMA_SIGN_SPLIT_NAME);
    		String [] searchConditionArr = request.getParameter("searchConditions").split(Constants.COMMA_SIGN_SPLIT_NAME);
    		String [] searchValsArr = request.getParameter("searchVals").split(Constants.COMMA_SIGN_SPLIT_NAME);
    		
    		int len = columnsNameArr.length;
    		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(len);
    		for(int i= 0; i < len; i++) {
    			filterList.add(new PropertyFilter(searchConditionArr[i] + columnsNameArr[i], searchValsArr[i]));
    		}
    		
    		return filterList;
    	}
    	else {
    		return buildPropertyFilters(request, "filter_");
    	}
    }
    
    /**
     * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
     * PropertyFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
     * 
     * eg.
     * filter_EQS_name
     * filter_LIKES_name_OR_email
     */
    public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request, final String filterPrefix) {
        List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix);

        //分析参数Map,构造PropertyFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            Object valueObj = entry.getValue();
            String value = null;
            if(valueObj instanceof String){
                value = (String)valueObj;
            }
            //如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                PropertyFilter filter = new PropertyFilter(filterName, value);
                filterList.add(filter);
            }
        }
        return filterList;
    }
    
    public static String propertyFilter2SqlString(PropertyFilter filter){
        String[] propertyNames = filter.getPropertyNames();
        
        StringBuilder sb = new StringBuilder();
        if(filter.isMultiProperty()){
            sb.append(" (");
        }
        
        boolean isFirst = true;
        for(String propertyName : propertyNames){
            if(StringUtils.isNotBlank(propertyName)){
                if(isFirst){
                    isFirst = false;
                }
                else{
                    sb.append("OR");
                }
                sb.append(propertyFilter2SqlString(propertyName, filter));
            }
        }
        
        if(filter.isMultiProperty()){
            sb.append(") ");
        }
        return sb.toString();
    }
    
    protected static String propertyFilter2SqlString(String propertyName, PropertyFilter filter){
        PropertyType propertyType = filter.getPropertyType();
        Object propertyValue = filter.getPropertyValue();
        MatchType matchType = filter.getMatchType();
        
        StringBuilder sb = new StringBuilder(" " + propertyName);
        sb.append(" " + matchType.express() + " ");
        if(matchType.outputValue()){
            sb.append(propertyType.expressStart());
            sb.append(matchType.expressStart());
            if(propertyValue != null && propertyValue instanceof Date){
                Date date = (Date)propertyValue;
                sb.append(DateTimeUtils.dateToStringFormat(date, Constants.DEFUALT_LONG_TIME_FORMAT));
            }
            else{
                sb.append(propertyValue);
            }
            sb.append(matchType.expressEnd());
            sb.append(propertyType.expressEnd() + " ");
        }
        
        return sb.toString();
    }
    
    public static void main(String [] args){
        PropertyType propertyType = PropertyType.D;
        MatchType matchType = MatchType.LLIKE;
        
        System.out.println(propertyType.name());
        System.out.println(matchType.name());
        
        String query1 = "LIKES_NAME_OR_LOGIN_NAME";
        PropertyFilter f1 = new PropertyFilter(query1, "admin");
        System.out.println(propertyFilter2SqlString(f1));
        
        String query2 = "LLIKES_NAME_OR_LOGIN_NAME";
        PropertyFilter f2 = new PropertyFilter(query2, "admin");
        System.out.println(propertyFilter2SqlString(f2));
        
        String query3 = "RLIKES_NAME_OR_LOGIN_NAME";
        PropertyFilter f3 = new PropertyFilter(query3, "admin");
        System.out.println(propertyFilter2SqlString(f3));
        
        String query4 = "EQS_NAME";
        PropertyFilter f4 = new PropertyFilter(query4, "admin");
        System.out.println(propertyFilter2SqlString(f4));
        
        String query5 = "GEI_NAME";
        PropertyFilter f5 = new PropertyFilter(query5, "1");
        System.out.println(propertyFilter2SqlString(f5));
        
        String query6 = "GTI_NAME";
        PropertyFilter f6 = new PropertyFilter(query6, "1");
        System.out.println(propertyFilter2SqlString(f6));
        
        String query7 = "EQD_NAME";
        PropertyFilter f7 = new PropertyFilter(query7, "2011-02-03");
        System.out.println(propertyFilter2SqlString(f7));
        
        String query8 = "EQN_NAME";
        PropertyFilter f8 = new PropertyFilter(query8, "1");
        System.out.println(propertyFilter2SqlString(f8));
        
        String query9 = "EQS_NAME";
        PropertyFilter f9 = new PropertyFilter(query9, "admin");
        System.out.println(propertyFilter2SqlString(f9));
        
        String query10 = "GTD_CREATE_OR_LASTUPD";
        PropertyFilter f10 = new PropertyFilter(query10, "2011-02-03 12:12:01");
        System.out.println(propertyFilter2SqlString(f10));
        
        String query11 = "INA_STATUS";
        PropertyFilter f11 = new PropertyFilter(query11, "'A','B','C','D'");
        System.out.println(propertyFilter2SqlString(f11));
        
        String query12 = "NULLS_STATUS";
        PropertyFilter f12 = new PropertyFilter(query12,null);
        System.out.println(propertyFilter2SqlString(f12));
    }
}
