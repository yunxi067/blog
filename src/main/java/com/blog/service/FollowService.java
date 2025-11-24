package com.blog.service;

import com.blog.domain.Follow;

import java.util.List;

public interface FollowService {
    boolean follow(Integer uid, Integer followUid);
    
    boolean unfollow(Integer uid, Integer followUid);
    
    boolean isFollowing(Integer uid, Integer followUid);
    
    List<Follow> getFollowingList(Integer uid);
    
    List<Follow> getFollowersList(Integer uid);
    
    int getFollowerCount(Integer uid);
    
    int getFollowingCount(Integer uid);
}
