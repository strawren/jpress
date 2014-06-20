package com.strawren.jpress.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.jpress.model.CmsUser;
import com.strawren.jpress.service.CmsUserService;

public class JpressShiroRealm extends AuthorizingRealm {
	protected Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CmsUserService cmsUserService;
	
	/**
	 * 授权
	 */
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		log.debug("get auth info --》" + arg0);
		return null;
	}

	/**
	 * 认证
	 */
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		log.debug("begin to auth -->" + authcToken);
		
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        String userName = token.getUsername();
		String password = String.valueOf(token.getPassword());
		
		if(StringUtils.isNotBlank(userName)) {
		    List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "LOGIN_NAME", userName));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "LOGIN_PWD", password));
            List<CmsUser> usersList = cmsUserService.search(filters);
            
            if(usersList != null && usersList.size() > 0){
                return new SimpleAuthenticationInfo(userName, password, getName());
            }
		}
		
		return null;
	}
}

