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

import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.jpress.dto.TermTaxonomyDTO;
import com.strawren.jpress.service.CmsTermService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 * 获取分类方法
 * @author liukh
 *
 */
@Service("gainTermTaxonomyDirective")
public class GainTermTaxonomy implements TemplateDirectiveModel{
    private static final Log log = LogFactory.getLog(GainTermTaxonomy.class);

    @Autowired
    CmsTermService termService;

    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("gainTermTaxonomyDirective is begin...");

        Iterator parIter = params.entrySet().iterator();
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
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


        TermTaxonomyDTO termTaxonomy = null;
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order("\"GROUP\"", Page.ASC));
        List<TermTaxonomyDTO> termTaxonomyList = termService.searchTermTaxonomy(filters, orders);
        if(termTaxonomyList != null && termTaxonomyList.size() == 1){
            log.debug("termTaxonomyList size >> " + termTaxonomyList.size());
            termTaxonomy = termTaxonomyList.get(0);
            env.setVariable("termTaxonomy",DEFAULT_WRAPPER.wrap(termTaxonomy));
        }else if(termTaxonomyList != null && termTaxonomyList.size() > 1){
            log.debug("termTaxonomyList size >> " + termTaxonomyList.size());
            env.setVariable("termTaxonomyList",DEFAULT_WRAPPER.wrap(termTaxonomyList));
        }else{
            log.debug("termTaxonomyList is null ...");
        }

        log.debug("gainTermTaxonomyDirective end!!");
        body.render(env.getOut());
    }

}
