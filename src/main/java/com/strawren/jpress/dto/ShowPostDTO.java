package com.strawren.jpress.dto;

import com.strawren.jpress.model.CmsPost;


public class ShowPostDTO extends CmsPost {

    private static final long serialVersionUID = 5322173598332325084L;

    private String termName;
    private String articleNumber;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }



}
