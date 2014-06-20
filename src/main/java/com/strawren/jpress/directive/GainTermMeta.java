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

import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.jpress.model.CmsTermMeta;
import com.strawren.jpress.service.CmsTermMetaService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 * 根据TmerId 和 Key获取termMeta
 * @author liukh
 *
 */
@Service("gainTermMetaDirective")
public class GainTermMeta implements TemplateDirectiveModel{
    private static final Log log = LogFactory.getLog(GainTerm.class);

    @Autowired
    CmsTermMetaService termMetaService;

    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("gainTermMetaDirective is begin...");

//        TemplateModel termId = (TemplateModel) params.get("termId");
//        TemplateModel key = (TemplateModel) params.get("key");
//        if(termId == null || key == null){
//            log.debug("termId or key is null ");
//            return;
//        }
//
//        log.debug("termId >>" + String.valueOf(termId));
//        log.debug("key >>" + String.valueOf(key));



        Iterator parIter = params.entrySet().iterator();
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            //拼接查询参数
//            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", CliviaConstants.DICT_GLOBAL_STATUS_VALIDATE));
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


        CmsTermMeta termMeta = null;
//        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", CliviaConstants.DICT_GLOBAL_STATUS_VALIDATE));
//        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_ID", String.valueOf(termId)));
        List<CmsTermMeta> termMetaList = termMetaService.search(filters);
        if(termMetaList != null && termMetaList.size() > 0){
            log.debug("termMetaList size >> " + termMetaList.size());
            termMeta = termMetaList.get(0);
        }else{
            log.debug("termMetaList is null ...");
        }

        env.setVariable("termMeta",DEFAULT_WRAPPER.wrap(termMeta));
        body.render(env.getOut());
        log.debug("gainTermMetaDirective end!!");

    }

}
