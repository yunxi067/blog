package com.blog.service;

import com.blog.domain.Space;

import java.util.List;

public interface SpaceService {
    Space getSpaceByUid(Integer uid);
    
    Space getSpaceBySid(Integer sid);
    
    boolean createSpace(Space space);
    
    boolean updateSpace(Space space);
    
    boolean updateUsedSpace(Integer uid, Long addSize);
    
    boolean incrementDownloadCount(Integer uid);
    
    boolean freezeSpace(Integer uid);
    
    boolean unfreezeSpace(Integer uid);
    
    List<Space> getAllSpaces();
    
    List<Space> getSpacesByStatus(Integer status);
    
    Long getRemainSize(Integer uid);
    
    boolean isSpaceFrozen(Integer uid);
    
    Long getSpaceDownloadCount(Integer uid);
}
