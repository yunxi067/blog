package com.blog.service;

import com.blog.domain.SpaceApply;

import java.util.List;

public interface SpaceApplyService {
    SpaceApply getApplyById(Integer applyId);
    
    boolean createApply(SpaceApply spaceApply);
    
    boolean updateApply(SpaceApply spaceApply);
    
    List<SpaceApply> getApplyByUid(Integer uid);
    
    List<SpaceApply> getPendingApplies();
    
    List<SpaceApply> getApplyByStatus(Integer status);
    
    boolean approveApply(Integer applyId, Integer approvedBy);
    
    boolean rejectApply(Integer applyId, String reason);
    
    boolean canApplyForExpansion(Integer uid);
}
