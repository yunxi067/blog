package com.blog.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {
    private static final String UPLOAD_BASE_PATH = "/opt/blog_system/upload";
    
    public static String getUploadBasePath() {
        return UPLOAD_BASE_PATH;
    }

    public static String generateFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        return UUID.randomUUID().toString() + (extension != null ? "." + extension : "");
    }

    public static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return null;
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    public static String getUserUploadPath(Integer uid) {
        return UPLOAD_BASE_PATH + File.separator + uid;
    }

    public static String getCategoryPath(Integer uid, String category) {
        return getUserUploadPath(uid) + File.separator + (category != null ? category : "default");
    }

    public static void ensureDirectoryExists(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
    }

    public static long getFileSize(String filePath) throws IOException {
        return Files.size(Paths.get(filePath));
    }

    public static void deleteFile(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    public static boolean isImageFile(String filename) {
        String ext = getFileExtension(filename);
        return ext != null && (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif") || ext.equals("bmp"));
    }

    public static boolean isAudioFile(String filename) {
        String ext = getFileExtension(filename);
        return ext != null && (ext.equals("mp3") || ext.equals("wav") || ext.equals("flac") || ext.equals("aac"));
    }

    public static boolean isVideoFile(String filename) {
        String ext = getFileExtension(filename);
        return ext != null && (ext.equals("mp4") || ext.equals("avi") || ext.equals("mov") || ext.equals("mkv") || ext.equals("wmv"));
    }
}
