package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Dai
 * @Date: 2024/04/28 14:54
 * @Description: Event
 * @Version: 1.0
 */
public class Event {


    //该事件的主题
    private String topic;
    //触发该事件的用户
    private int userId;
    //触发该事件的实体类型
    private int entityType;
    //触发该事件的实体id
    private int entityId;
    //触发该事件的实体id对应的用户
    private int entityUserId;
    //该事件额外的数据，便于扩展功能
    private Map<String, Object> data = new HashMap<>();

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key,Object value) {
        this.data.put(key,value);
        return this;
    }

}
