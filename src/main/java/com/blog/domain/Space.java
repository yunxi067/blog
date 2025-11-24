package com.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class Space implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer sid;
    private Integer uid;
    private Long ssizeTotal;
    private Long ssizeUsed;
    private Long downloadCount;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public Space() {}

    public Space(Integer uid, Long ssizeTotal) {
        this.uid = uid;
        this.ssizeTotal = ssizeTotal;
        this.ssizeUsed = 0L;
        this.downloadCount = 0L;
        this.status = 1;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getSsizeTotal() {
        return ssizeTotal;
    }

    public void setSsizeTotal(Long ssizeTotal) {
        this.ssizeTotal = ssizeTotal;
    }

    public Long getSsizeUsed() {
        return ssizeUsed;
    }

    public void setSsizeUsed(Long ssizeUsed) {
        this.ssizeUsed = ssizeUsed;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRemainSize() {
        return this.ssizeTotal - this.ssizeUsed;
    }

    @Override
    public String toString() {
        return "Space{" +
                "sid=" + sid +
                ", uid=" + uid +
                ", ssizeTotal=" + ssizeTotal +
                ", ssizeUsed=" + ssizeUsed +
                ", downloadCount=" + downloadCount +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
