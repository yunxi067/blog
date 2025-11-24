package com.blog.service.impl;

import com.blog.domain.File;
import com.blog.mapper.FileMapper;
import com.blog.mapper.SpaceMapper;
import com.blog.service.FileService;
import com.blog.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private SpaceMapper spaceMapper;

    @Override
    public File getFileById(Integer fid) {
        return fileMapper.selectByFid(fid);
    }

    @Override
    public boolean uploadFile(File file, MultipartFile multipartFile) throws IOException {
        if (file == null || file.getUid() == null || multipartFile == null || multipartFile.isEmpty()) {
            return false;
        }

        String fileName = FileUtil.generateFileName(multipartFile.getOriginalFilename());
        String category = file.getCategory() != null ? file.getCategory() : "default";
        String categoryPath = FileUtil.getCategoryPath(file.getUid(), category);

        FileUtil.ensureDirectoryExists(categoryPath);

        String filePath = categoryPath + java.io.File.separator + fileName;
        multipartFile.transferTo(new java.io.File(filePath));

        long fileSize = Files.size(Paths.get(filePath));

        file.setFileName(fileName);
        file.setFileSize(fileSize);
        file.setFilePath(filePath);
        file.setFileType(FileUtil.getFileExtension(multipartFile.getOriginalFilename()));
        file.setDownloadCount(0);
        file.setStatus(1);
        file.setCreateTime(new Date());

        int result = fileMapper.insert(file);
        if (result > 0) {
            spaceMapper.updateSsizeUsed(file.getUid(), fileSize);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFile(Integer fid) {
        File file = fileMapper.selectByFid(fid);
        if (file == null) {
            return false;
        }

        try {
            FileUtil.deleteFile(file.getFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int result = fileMapper.delete(fid);
        if (result > 0 && file.getFileSize() != null) {
            spaceMapper.updateSsizeUsed(file.getUid(), -file.getFileSize());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateFile(File file) {
        if (file == null || file.getFid() == null) {
            return false;
        }
        file.setUpdateTime(new Date());
        int result = fileMapper.update(file);
        return result > 0;
    }

    @Override
    public boolean renameFile(Integer fid, String newName) {
        File file = fileMapper.selectByFid(fid);
        if (file == null) {
            return false;
        }
        file.setOriginalName(newName);
        file.setUpdateTime(new Date());
        int result = fileMapper.update(file);
        return result > 0;
    }

    @Override
    public boolean changeCategory(Integer fid, String category) {
        File file = fileMapper.selectByFid(fid);
        if (file == null) {
            return false;
        }
        file.setCategory(category);
        file.setUpdateTime(new Date());
        int result = fileMapper.update(file);
        return result > 0;
    }

    @Override
    public boolean freezeFile(Integer fid) {
        int result = fileMapper.updateStatus(fid, 0);
        return result > 0;
    }

    @Override
    public boolean unfreezeFile(Integer fid) {
        int result = fileMapper.updateStatus(fid, 1);
        return result > 0;
    }

    @Override
    public boolean incrementDownloadCount(Integer fid) {
        int result = fileMapper.incrementDownloadCount(fid);
        return result > 0;
    }

    @Override
    public List<File> getFilesByUid(Integer uid) {
        return fileMapper.selectByUid(uid);
    }

    @Override
    public List<File> getFilesByUidAndCategory(Integer uid, String category) {
        return fileMapper.selectByUidAndCategory(uid, category);
    }

    @Override
    public List<File> getUnapprovedFiles() {
        return fileMapper.selectByStatus(0);
    }

    @Override
    public boolean approveFile(Integer fid) {
        int result = fileMapper.updateStatus(fid, 1);
        return result > 0;
    }

    @Override
    public boolean rejectFile(Integer fid) {
        return deleteFile(fid);
    }

    @Override
    public Long getTotalSizeByUid(Integer uid) {
        Long totalSize = fileMapper.selectTotalSizeByUid(uid);
        return totalSize != null ? totalSize : 0L;
    }
}
