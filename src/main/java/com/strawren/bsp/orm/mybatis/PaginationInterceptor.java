/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-26 下午03:13:16
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-26        Initailized
 */

package com.strawren.bsp.orm.mybatis;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.strawren.bsp.orm.dialect.Dialect;


/**
 * mybatis分页拦截器
 *
 */
@Intercepts({@Signature(type=Executor.class,method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor implements Interceptor{
    public static final Log log = LogFactory.getLog(PaginationInterceptor.class);

    private final static int MAPPED_STATEMENT_INDEX = 0;  
    private final static int PARAMETER_INDEX = 1;  
    private final static int ROWBOUNDS_INDEX = 2;  
    private final static int MAX_VALUE_CONDITION = RowBounds.NO_ROW_LIMIT;
    private Dialect dialect;
      
    public Object intercept(Invocation invocation) throws Throwable { 
        
        final Object[] queryArgs = invocation.getArgs();
        if(queryArgs[ROWBOUNDS_INDEX] != null){
            final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX]; 
            if(rowBounds.getLimit() < MAX_VALUE_CONDITION){
                processIntercept(invocation.getArgs()); 
            }
        }
        queryArgs[ROWBOUNDS_INDEX] = org.apache.ibatis.session.RowBounds.DEFAULT; 
        return invocation.proceed();  
    }  
  
    private void processIntercept(final Object[] queryArgs) {  
        MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];  
        Object parameter = queryArgs[PARAMETER_INDEX]; 
        final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX]; 
        
        if (rowBounds != null) {  
            int offset = rowBounds.getOffset();  
            int limit = rowBounds.getLimit(); 
            
            if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {  
                log.debug("Pagination query prepare ...");  
                
                BoundSql boundSql = ms.getBoundSql(parameter);  
                String sql = boundSql.getSql().trim(); 
                
                if (dialect.supportsLimitOffset()) {  
                    sql = dialect.getLimitString(sql, offset, limit);  
                    offset = RowBounds.NO_ROW_OFFSET;  
                } 
                else {  
                    sql = dialect.getLimitString(sql, 0, limit);  
                } 
                
                BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());  
                log.debug("Mybatis Page Search:"+ sql.replaceAll("\n", ""));
                MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));  
                queryArgs[MAPPED_STATEMENT_INDEX] = newMs; 
            }
        }

        queryArgs[ROWBOUNDS_INDEX]= RowBounds.DEFAULT; 
    }  
      
    private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {  
        Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());  
        builder.resource(ms.getResource());  
        builder.fetchSize(ms.getFetchSize());  
        builder.statementType(ms.getStatementType());  
        builder.keyGenerator(ms.getKeyGenerator());  
        if(ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
        	builder.keyProperty(ms.getKeyProperties()[0]);  
        }
        builder.timeout(ms.getTimeout());  
        builder.parameterMap(ms.getParameterMap());  
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache()); 
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.statementType(ms.getStatementType());
        MappedStatement newMs = builder.build();  
        return newMs;  
    }  
  
    public Object plugin(Object target) {  
        return Plugin.wrap(target, this);  
    }  
      
    public static class BoundSqlSqlSource implements SqlSource {  
        BoundSql boundSql;  
        public BoundSqlSqlSource(BoundSql boundSql) {  
            this.boundSql = boundSql;  
        }  
        public BoundSql getBoundSql(Object parameterObject) {  
            return boundSql;  
        }  
    }

    public void setProperties(Properties props) {
        String dialectClass = props.getProperty("dialect");   
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } 
        catch (Exception e) {
            throw new IllegalArgumentException(dialectClass +" : can not init!");
        }
    }  
}  