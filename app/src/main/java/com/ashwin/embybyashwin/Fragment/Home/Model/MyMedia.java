package com.ashwin.embybyashwin.Fragment.Home.Model;

import mediabrowser.model.dto.BaseItemDto;

public class MyMedia {
    String id;
    String name;
    String thumbanilUrl;
    BaseItemDto _item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbanilUrl() {
        return thumbanilUrl;
    }

    public void setThumbanilUrl(String thumbanilUrl) {
        this.thumbanilUrl = thumbanilUrl;
    }

    public BaseItemDto getItemDetials() {
        return _item;
    }

    public void setItemDetials(BaseItemDto mediaName) {
        this._item = mediaName;
    }


}
