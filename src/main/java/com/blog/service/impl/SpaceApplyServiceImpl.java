package com.blog.service.impl;

import com.blog.domain.Space;
import com.blog.domain.SpaceApply;
import com.blog.mapper.SpaceApplyMapper;
import com.blog.mapper.SpaceMapper;
import com.blog.service.SpaceApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpaceApplyServiceImpl implements SpaceApplyService {

    @Autowired
    private SpaceApplyMapper spaceApplyMapper;

    @Autowired
    private SpaceMapper spaceMapper;

    private static final Long DOWNLOAD_COUNT_THRESHOLD = 50L;

    @Override
    public SpaceApply getApplyById(Integer applyId) {
        return spaceApplyMapper.selectByApplyId(applyId);
    }

    @Override
    public boolean createApply(SpaceApply spaceApply) {
        if (spaceApply == null || spaceApply.getUid() == null) {
            return false;
        }

        spaceApply.setStatus(0);
        spaceApply.setCreateTime(new Date());

        int result = spaceApplyMapper.insert(spaceApply);
        return result > 0;
    }

    @Override
    public boolean updateApply(SpaceApply spaceApply) {
        if (spaceApply == null || spaceApply.getApplyId() == null) {
            return false;
        }

        spaceApply.setUpdateTime(new Date());
        int result = spaceApplyMapper.update(spaceApply);
        return result > 0;
    }

    @Override
    public List<SpaceApply> getApplyByUid(Integer uid) {
        return spaceApplyMapper.selectByUid(uid);
    }

    @Override
    public List<SpaceApply> getPendingApplies() {
        return spaceApplyMapper.selectByStatus(0);
    }

    @Override
    public List<SpaceApply> getApplyByStatus(Integer status) {
        return spaceApplyMapper.selectByStatus(status);
    }

    @Override
    public boolean approveApply(Integer applyId, Integer approvedBy) {
        SpaceApply spaceApply = spaceApplyMapper.selectByApplyId(applyId);
        if (spaceApply == null) {
            return false;
        }

        Space space = spaceMapper.selectByUid(spaceApply.getUid());
        if (space == null) {
            return false;
        }

        space.setSsizeTotal(space.getSsizeTotal() + spaceApply.getApplySize());
        space.setUpdateTime(new Date());
        spaceMapper.update(space);

        spaceApply.setStatus(1);
        spaceApply.setApprovedBy(approvedBy);
        spaceApply.setUpdateTime(new Date());
        int result = spaceApplyMapper.update(spaceApply);

        return result > 0;
    }

    @Override
    public boolean rejectApply(Integer applyId, String reason) {
        SpaceApply spaceApply = spaceApplyMapper.selectByApplyId(applyId);
        if (spaceApply == null) {
            return false;
        }

        spaceApply.setStatus(2);
        spaceApply.setRejectReason(reason);
        spaceApply.setUpdateTime(new Date());
        int result = spaceApplyMapper.update(spaceApply);

        return result > 0;
    }

    @Override
    public boolean canApplyForExpansion(Integer uid) {
        Space space = spaceMapper.selectByUid(uid);
        if (space == null) {
            return false;
        }

        return space.getDownloadCount() >= DOWNLOAD_COUNT_THRESHOLD;
    }
}
