package com.bjyw.bjckyh.bean.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class InspectCommmit {
    @Id(autoincrement = true)//设置自增长
    private Long id;
    private String userId;
    private String siteId;
    private String orderIndex;
    private String data;
    @Generated(hash = 196264380)
    public InspectCommmit(Long id, String userId, String siteId, String orderIndex,
            String data) {
        this.id = id;
        this.userId = userId;
        this.siteId = siteId;
        this.orderIndex = orderIndex;
        this.data = data;
    }
    @Generated(hash = 1535815522)
    public InspectCommmit() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getSiteId() {
        return this.siteId;
    }
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    public String getOrderIndex() {
        return this.orderIndex;
    }
    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
