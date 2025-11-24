package com.blog.mapper;

import com.blog.domain.Space;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceMapper {
    Space selectByUid(Integer uid);
    
    Space selectBySid(Integer sid);
    
    int insert(Space space);
    
    int update(Space space);
    
    int updateSsizeUsed(@Param("uid") Integer uid, @Param("addSize") Long addSize);
    
    int updateDownloadCount(@Param("uid") Integer uid);
    
    int updateStatus(@Param("uid") Integer uid, @Param("status") Integer status);
    
    List<Space> selectAll();
    
    List<Space> selectByStatus(Integer status);
}
