package com.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer notificationId;
    private Integer uid;
    private Integer followUid;
    private String content;
    private Integer status;
    private Date createTime;

    public Notification() {}

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFollowUid() {
        return followUid;
    }

    public void setFollowUid(Integer followUid) {
        this.followUid = followUid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        return "Notification{" +
                "notificationId=" + notificationId +
                ", uid=" + uid +
                ", followUid=" + followUid +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
