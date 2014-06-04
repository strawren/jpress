package com.orientpay.clivia.portal.web.auths;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * HTTP请求工具类
 *
 */
public class HttpUtils {
	protected static final Log log = LogFactory.getLog(HttpUtils.class);

	public static String doJsonPost(String url, String json) {
		try {
			log.debug("do post to " + url);

			HttpClient hc = new DefaultHttpClient();



			hc.getParams().setParameter(
			        HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");



			HttpPost post = new HttpPost(url);

			StringEntity s = new StringEntity(json,"UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);

			HttpResponse response = hc.execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				log.debug("post ok !!!");
				return EntityUtils.toString(entity);
			}
			else {
				log.info("post error : " + response.getStatusLine().getStatusCode());
			}
		}
		catch(Exception e) {
			log.warn(e.getMessage(), e);
		}

		return "";
	}
}
