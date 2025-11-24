package com.blog.mapper;

import com.blog.domain.File;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMapper {
    File selectByFid(Integer fid);
    
    int insert(File file);
    
    int update(File file);
    
    int delete(Integer fid);
    
    int updateStatus(@Param("fid") Integer fid, @Param("status") Integer status);
    
    int incrementDownloadCount(Integer fid);
    
    List<File> selectByUid(Integer uid);
    
    List<File> selectByUidAndCategory(@Param("uid") Integer uid, @Param("category") String category);
    
    List<File> selectByStatus(Integer status);
    
    List<File> selectAll();
    
    Long selectTotalSizeByUid(Integer uid);
}
