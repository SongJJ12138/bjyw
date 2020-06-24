package com.bjyw.bjckyh.bean.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class InspectConsumable {
    @Id
    Long id;
    String orderIndex;
    String equipId;
    String consumableId;
    String handId;
    String count;
    @Generated(hash = 1339080262)
    public InspectConsumable(Long id, String orderIndex, String equipId,
            String consumableId, String handId, String count) {
        this.id = id;
        this.orderIndex = orderIndex;
        this.equipId = equipId;
        this.consumableId = consumableId;
        this.handId = handId;
        this.count = count;
    }
    @Generated(hash = 145122889)
    public InspectConsumable() {
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
    public String getEquipId() {
        return this.equipId;
    }
    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }
    public String getConsumableId() {
        return this.consumableId;
    }
    public void setConsumableId(String consumableId) {
        this.consumableId = consumableId;
    }
    public String getHandId() {
        return this.handId;
    }
    public void setHandId(String handId) {
        this.handId = handId;
    }
    public String getCount() {
        return this.count;
    }
    public void setCount(String count) {
        this.count = count;
    }
}
