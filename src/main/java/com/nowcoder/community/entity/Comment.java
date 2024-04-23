package com.nowcoder.community.entity;

import java.util.Date;

/**
 * @Author: Dai
 * @Date: 2024/04/11 15:36
 * @Description: Comment
 * @Version: 1.0
 */
public class Comment {
    //评论Id
    private int id;
    //评论人Id
    private int userId;
    //评论类型 1代表对帖子进行评论 2代表对帖子的评论进行回复
    private int entityType;
    //评论目标Id（即帖子或回复的Id）
    private int entityId;
    //如果再对回复进行回复，则targetId为要回复的对象的userId
    private int targetId;
    //评论内容
    private String content;
    //状态 0代表正常 1代表被删除或禁用了
    private int status;
    //创建时间
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", targetId=" + targetId +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
