package com.blog.service.impl;

import com.blog.domain.Space;
import com.blog.mapper.SpaceMapper;
import com.blog.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

    private static final Long DEFAULT_SPACE_SIZE = 1024L * 1024L * 100L;

    @Override
    public Space getSpaceByUid(Integer uid) {
        return spaceMapper.selectByUid(uid);
    }

    @Override
    public Space getSpaceBySid(Integer sid) {
        return spaceMapper.selectBySid(sid);
    }

    @Override
    public boolean createSpace(Space space) {
        if (space == null || space.getUid() == null) {
            return false;
        }
        
        if (space.getSsizeTotal() == null) {
            space.setSsizeTotal(DEFAULT_SPACE_SIZE);
        }
        if (space.getSsizeUsed() == null) {
            space.setSsizeUsed(0L);
        }
        if (space.getDownloadCount() == null) {
            space.setDownloadCount(0L);
        }
        if (space.getStatus() == null) {
            space.setStatus(1);
        }
        
        space.setCreateTime(new Date());
        
        int result = spaceMapper.insert(space);
        return result > 0;
    }

    @Override
    public boolean updateSpace(Space space) {
        if (space == null || space.getUid() == null) {
            return false;
        }
        space.setUpdateTime(new Date());
        int result = spaceMapper.update(space);
        return result > 0;
    }

    @Override
    public boolean updateUsedSpace(Integer uid, Long addSize) {
        if (uid == null || addSize == null) {
            return false;
        }
        int result = spaceMapper.updateSsizeUsed(uid, addSize);
        return result > 0;
    }

    @Override
    public boolean incrementDownloadCount(Integer uid) {
        if (uid == null) {
            return false;
        }
        int result = spaceMapper.updateDownloadCount(uid);
        return result > 0;
    }

    @Override
    public boolean freezeSpace(Integer uid) {
        int result = spaceMapper.updateStatus(uid, 0);
        return result > 0;
    }

    @Override
    public boolean unfreezeSpace(Integer uid) {
        int result = spaceMapper.updateStatus(uid, 1);
        return result > 0;
    }

    @Override
    public List<Space> getAllSpaces() {
        return spaceMapper.selectAll();
    }

    @Override
    public List<Space> getSpacesByStatus(Integer status) {
        return spaceMapper.selectByStatus(status);
    }

    @Override
    public Long getRemainSize(Integer uid) {
        Space space = spaceMapper.selectByUid(uid);
        if (space == null) {
            return 0L;
        }
        return space.getRemainSize();
    }

    @Override
    public boolean isSpaceFrozen(Integer uid) {
        Space space = spaceMapper.selectByUid(uid);
        return space != null && space.getStatus() == 0;
    }

    @Override
    public Long getSpaceDownloadCount(Integer uid) {
        Space space = spaceMapper.selectByUid(uid);
        if (space == null) {
            return 0L;
        }
        return space.getDownloadCount();
    }
}
