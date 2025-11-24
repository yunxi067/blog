package com.blog.service;

import com.blog.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    File getFileById(Integer fid);
    
    boolean uploadFile(File file, MultipartFile multipartFile) throws IOException;
    
    boolean deleteFile(Integer fid);
    
    boolean updateFile(File file);
    
    boolean renameFile(Integer fid, String newName);
    
    boolean changeCategory(Integer fid, String category);
    
    boolean freezeFile(Integer fid);
    
    boolean unfreezeFile(Integer fid);
    
    boolean incrementDownloadCount(Integer fid);
    
    List<File> getFilesByUid(Integer uid);
    
    List<File> getFilesByUidAndCategory(Integer uid, String category);
    
    List<File> getUnapprovedFiles();
    
    boolean approveFile(Integer fid);
    
    boolean rejectFile(Integer fid);
    
    Long getTotalSizeByUid(Integer uid);
}
