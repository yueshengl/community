package com.nowcoder.community.entity;

import java.util.Date;

/**
 * @Author: Dai
 * @Date: 2024/03/21 13:22
 * @Description: DiscussPost
 * @Version: 1.0
 */
public class DiscussPost {

    // 帖子id
    private int id;
    // 帖子作者id
    private int userId;
    // 帖子标题
    private String title;
    // 帖子内容
    private String content;
    // 帖子类型 0代表正常 1代表置顶
    private int type;
    // 帖子状态 0代表正常  1代表精华 2代表拉黑
    private int status;
    //创建时间
    private Date createTime;
    //帖子评论数量
    private int commentCount;
    //帖子分数（用来排名帖子）
    private double score;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DiscussPost{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", commentCount=" + commentCount +
                ", score=" + score +
                '}';
    }
}
