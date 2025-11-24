package com.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class Follow implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer followId;
    private Integer uid;
    private Integer followUid;
    private Date createTime;

    public Follow() {}

    public Follow(Integer uid, Integer followUid) {
        this.uid = uid;
        this.followUid = followUid;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followId=" + followId +
                ", uid=" + uid +
                ", followUid=" + followUid +
                ", createTime=" + createTime +
                '}';
    }
}
