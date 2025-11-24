package com.blog.mapper;

import com.blog.domain.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowMapper {
    Follow selectByFollowId(Integer followId);
    
    int insert(Follow follow);
    
    int delete(Integer followId);
    
    int deleteByUidAndFollowUid(@Param("uid") Integer uid, @Param("followUid") Integer followUid);
    
    List<Follow> selectByUid(Integer uid);
    
    List<Follow> selectFollowersByUid(Integer uid);
    
    Follow selectByUidAndFollowUid(@Param("uid") Integer uid, @Param("followUid") Integer followUid);
    
    int countFollowersByUid(Integer uid);
    
    int countFollowingByUid(Integer uid);
}
