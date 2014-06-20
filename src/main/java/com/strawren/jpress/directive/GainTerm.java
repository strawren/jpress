package com.strawren.jpress.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.service.CmsTermService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 * 根据TermId获取term
 * 当查询记录为一条时返回model,
 * 当查询记录为大于一条时，返回List集合
 * @author liukh
 *
 */
@Service("gainTermDirective")
public class GainTerm implements TemplateDirectiveModel{
    private static final Log log = LogFactory.getLog(GainTerm.class);

    @Autowired
    CmsTermService termService;

    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("gainTermDirective is begin...");

        Iterator parIter = params.entrySet().iterator();
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
      //拼接查询参数
        while(parIter.hasNext()){
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            String preFix = "filter_";
            while(parIter.hasNext()){
                Map.Entry ent = (Map.Entry) parIter.next();
                String parName = (String) ent.getKey();
                TemplateModel parValue = (TemplateModel) ent.getValue();
                log.debug("parName -> " + parName + "@@@  parValue -> " + parValue);
                if(parName.startsWith(preFix)){
                    String param = parName.substring(preFix.length());
                    filters.add(new PropertyFilter(param,parValue.toString()));
                }
            }
        }

        CmsTerm term = null;
        List<CmsTerm> termList = termService.search(filters);
        if(termList != null && termList.size() == 1){
            log.debug("termList size >> " + termList.size());
            term = termList.get(0);
            env.setVariable("term",DEFAULT_WRAPPER.wrap(term));
        }else if(termList != null && termList.size() > 1){
            env.setVariable("termList",DEFAULT_WRAPPER.wrap(termList));
        }else{
            log.debug("termList is null ...");
        }


        body.render(env.getOut());
        log.debug("gainTermDirective end!!");
    }
}
