package com.blog.mapper;

import com.blog.domain.SpaceApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceApplyMapper {
    SpaceApply selectByApplyId(Integer applyId);
    
    int insert(SpaceApply spaceApply);
    
    int update(SpaceApply spaceApply);
    
    List<SpaceApply> selectByUid(Integer uid);
    
    List<SpaceApply> selectByStatus(Integer status);
    
    List<SpaceApply> selectAll();
    
    List<SpaceApply> selectByStatusAndUid(@Param("status") Integer status, @Param("uid") Integer uid);
}
