package com.strawren.jpress.dto;


public class MenuNavDTO {

   /**
    * 菜单id
    */
   private Long menuId;

    /**
     * 菜单文字
     */
    private String url;

    /**
     * 菜单图片guid
     */
    private String guid;

    /**
     * 菜单图片（灰色）
     * @return
     */
    private String guidGray;


    public Long getMenuId() {
        return menuId;
    }



    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getGuid() {
        return guid;
    }


    public void setGuid(String guid) {
        this.guid = guid;
    }



    public String getGuidGray() {
        return guidGray;
    }



    public void setGuidGray(String guidGray) {
        this.guidGray = guidGray;
    }



}
