package com.blog.service.impl;

import com.blog.domain.Follow;
import com.blog.mapper.FollowMapper;
import com.blog.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public boolean follow(Integer uid, Integer followUid) {
        if (uid == null || followUid == null || uid.equals(followUid)) {
            return false;
        }

        Follow existingFollow = followMapper.selectByUidAndFollowUid(uid, followUid);
        if (existingFollow != null) {
            return false;
        }

        Follow follow = new Follow(uid, followUid);
        follow.setCreateTime(new Date());

        int result = followMapper.insert(follow);
        return result > 0;
    }

    @Override
    public boolean unfollow(Integer uid, Integer followUid) {
        if (uid == null || followUid == null) {
            return false;
        }

        int result = followMapper.deleteByUidAndFollowUid(uid, followUid);
        return result > 0;
    }

    @Override
    public boolean isFollowing(Integer uid, Integer followUid) {
        if (uid == null || followUid == null) {
            return false;
        }

        Follow follow = followMapper.selectByUidAndFollowUid(uid, followUid);
        return follow != null;
    }

    @Override
    public List<Follow> getFollowingList(Integer uid) {
        if (uid == null) {
            return null;
        }
        return followMapper.selectByUid(uid);
    }

    @Override
    public List<Follow> getFollowersList(Integer uid) {
        if (uid == null) {
            return null;
        }
        return followMapper.selectFollowersByUid(uid);
    }

    @Override
    public int getFollowerCount(Integer uid) {
        if (uid == null) {
            return 0;
        }
        return followMapper.countFollowersByUid(uid);
    }

    @Override
    public int getFollowingCount(Integer uid) {
        if (uid == null) {
            return 0;
        }
        return followMapper.countFollowingByUid(uid);
    }
}
