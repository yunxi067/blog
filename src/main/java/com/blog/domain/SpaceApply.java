package com.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class SpaceApply implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer applyId;
    private Integer uid;
    private Long applySize;
    private Integer status;
    private String reason;
    private String rejectReason;
    private Date createTime;
    private Date updateTime;
    private Integer approvedBy;

    public SpaceApply() {}

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getApplySize() {
        return applySize;
    }

    public void setApplySize(Long applySize) {
        this.applySize = applySize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public String toString() {
        return "SpaceApply{" +
                "applyId=" + applyId +
                ", uid=" + uid +
                ", applySize=" + applySize +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
