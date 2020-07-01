package com.bjyw.bjckyh.bean.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Inspect {
    @Id(autoincrement = true)//设置自增长
    private Long id;
    private boolean today;
    private String name;
    private String time;
    private int siteId;
    private String orderIndex;
    private String userId;
    private String status;
    private String is_unusual;
    private String useStatus;
    private String environmentStatus;
    private String conId;
    @Generated(hash = 1009980090)
    public Inspect(Long id, boolean today, String name, String time, int siteId,
            String orderIndex, String userId, String status, String is_unusual,
            String useStatus, String environmentStatus, String conId) {
        this.id = id;
        this.today = today;
        this.name = name;
        this.time = time;
        this.siteId = siteId;
        this.orderIndex = orderIndex;
        this.userId = userId;
        this.status = status;
        this.is_unusual = is_unusual;
        this.useStatus = useStatus;
        this.environmentStatus = environmentStatus;
        this.conId = conId;
    }
    @Generated(hash = 1505544833)
    public Inspect() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderIndex() {
        return this.orderIndex;
    }
    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getIs_unusual() {
        return this.is_unusual;
    }
    public void setIs_unusual(String is_unusual) {
        this.is_unusual = is_unusual;
    }
    public String getUseStatus() {
        return this.useStatus;
    }
    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }
    public String getEnvironmentStatus() {
        return this.environmentStatus;
    }
    public void setEnvironmentStatus(String environmentStatus) {
        this.environmentStatus = environmentStatus;
    }
    public String getConId() {
        return this.conId;
    }
    public void setConId(String conId) {
        this.conId = conId;
    }
    public boolean getToday() {
        return this.today;
    }
    public void setToday(boolean today) {
        this.today = today;
    }
    public int getSiteId() {
        return this.siteId;
    }
    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
