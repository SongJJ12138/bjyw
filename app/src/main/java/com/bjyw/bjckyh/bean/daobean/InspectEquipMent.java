package com.bjyw.bjckyh.bean.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class InspectEquipMent {
    @Id
    Long id;
    int position;
    String equipName;
    String orderIndex;
    String equipmentIndex;
    String remark;
    String picture;
    String context;
    String is_unusual;
    String is_exist;
    String comments;
    @Generated(hash = 755802430)
    public InspectEquipMent(Long id, int position, String equipName,
            String orderIndex, String equipmentIndex, String remark, String picture,
            String context, String is_unusual, String is_exist, String comments) {
        this.id = id;
        this.position = position;
        this.equipName = equipName;
        this.orderIndex = orderIndex;
        this.equipmentIndex = equipmentIndex;
        this.remark = remark;
        this.picture = picture;
        this.context = context;
        this.is_unusual = is_unusual;
        this.is_exist = is_exist;
        this.comments = comments;
    }
    @Generated(hash = 1383367214)
    public InspectEquipMent() {
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
    public String getEquipmentIndex() {
        return this.equipmentIndex;
    }
    public void setEquipmentIndex(String equipmentIndex) {
        this.equipmentIndex = equipmentIndex;
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
    public String getIs_exist() {
        return this.is_exist;
    }
    public void setIs_exist(String is_exist) {
        this.is_exist = is_exist;
    }
    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getEquipName() {
        return this.equipName;
    }
    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}
