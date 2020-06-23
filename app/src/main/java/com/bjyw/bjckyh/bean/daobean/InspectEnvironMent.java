package com.bjyw.bjckyh.bean.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class InspectEnvironMent {
    @Id
    public Long id;
    String onrderIndex;
    String environmentIndex;
    String remark;
    String picture;
    String context;
    String is_unusual;
    @Generated(hash = 1146601518)
    public InspectEnvironMent(Long id, String onrderIndex, String environmentIndex,
            String remark, String picture, String context, String is_unusual) {
        this.id = id;
        this.onrderIndex = onrderIndex;
        this.environmentIndex = environmentIndex;
        this.remark = remark;
        this.picture = picture;
        this.context = context;
        this.is_unusual = is_unusual;
    }
    @Generated(hash = 181602696)
    public InspectEnvironMent() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEnvironmentIndex() {
        return this.environmentIndex;
    }
    public void setEnvironmentIndex(String environmentIndex) {
        this.environmentIndex = environmentIndex;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getPicture() {
        return this.picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getContext() {
        return this.context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getIs_unusual() {
        return this.is_unusual;
    }
    public void setIs_unusual(String is_unusual) {
        this.is_unusual = is_unusual;
    }
    public String getOnrderIndex() {
        return this.onrderIndex;
    }
    public void setOnrderIndex(String onrderIndex) {
        this.onrderIndex = onrderIndex;
    }
}
