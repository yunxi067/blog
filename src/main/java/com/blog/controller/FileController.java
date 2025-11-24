package com.blog.controller;

import com.blog.domain.File;
import com.blog.domain.Space;
import com.blog.service.FileService;
import com.blog.service.SpaceService;
import com.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/upload")
    public String uploadPage(HttpSession session, Model model) {
        Integer uid = SessionUtil.getLoginUid(session);
        if (uid == null) {
            return "redirect:/user/login";
        }

        Space space = spaceService.getSpaceByUid(uid);
        model.addAttribute("space", space);

        return "file/upload";
    }

    @PostMapping("/doUpload")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file,
                                          @RequestParam(required = false) String category,
                                          @RequestParam(required = false) String description,
                                          HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer uid = SessionUtil.getLoginUid(session);

        if (uid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "file is empty");
            return result;
        }

        Space space = spaceService.getSpaceByUid(uid);
        if (space == null || space.getRemainSize() < file.getSize()) {
            result.put("success", false);
            result.put("message", "insufficient space");
            return result;
        }

        File fileRecord = new File();
        fileRecord.setUid(uid);
        fileRecord.setOriginalName(file.getOriginalFilename());
        fileRecord.setCategory(category != null ? category : "default");
        fileRecord.setDescription(description);

        try {
            boolean success = fileService.uploadFile(fileRecord, file);
            if (success) {
                result.put("success", true);
                result.put("message", "file uploaded successfully");
                result.put("file", fileRecord);
            } else {
                result.put("success", false);
                result.put("message", "upload failed");
            }
        } catch (IOException e) {
            result.put("success", false);
            result.put("message", "upload error: " + e.getMessage());
        }

        return result;
    }

    @GetMapping("/list")
    public String listFiles(HttpSession session, Model model) {
        Integer uid = SessionUtil.getLoginUid(session);
        if (uid == null) {
            return "redirect:/user/login";
        }

        List<File> files = fileService.getFilesByUid(uid);
        Space space = spaceService.getSpaceByUid(uid);

        model.addAttribute("files", files);
        model.addAttribute("space", space);

        return "file/list";
    }

    @GetMapping("/download/{fid}")
    public void downloadFile(@PathVariable Integer fid, HttpSession session, HttpServletResponse response) throws IOException {
        Integer uid = SessionUtil.getLoginUid(session);
        if (uid == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
            return;
        }

        File file = fileService.getFileById(fid);
        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        if (!file.getUid().equals(uid)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        if (file.getStatus() == 0) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "File is frozen");
            return;
        }

        Space space = spaceService.getSpaceByUid(uid);
        if (space.getStatus() == 0) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Space is frozen");
            return;
        }

        java.io.File physicalFile = new java.io.File(file.getFilePath());
        if (!physicalFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found on server");
            return;
        }

        fileService.incrementDownloadCount(fid);
        spaceService.incrementDownloadCount(uid);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getOriginalName(), "UTF-8"));
        response.setContentLength((int) physicalFile.length());

        try (FileInputStream fis = new FileInputStream(physicalFile);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
        }
    }

    @PostMapping("/delete/{fid}")
    @ResponseBody
    public Map<String, Object> deleteFile(@PathVariable Integer fid, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer uid = SessionUtil.getLoginUid(session);

        if (uid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        File file = fileService.getFileById(fid);
        if (file == null) {
            result.put("success", false);
            result.put("message", "file not found");
            return result;
        }

        if (!file.getUid().equals(uid)) {
            result.put("success", false);
            result.put("message", "access denied");
            return result;
        }

        boolean success = fileService.deleteFile(fid);
        result.put("success", success);
        result.put("message", success ? "file deleted" : "delete failed");

        return result;
    }

    @PostMapping("/rename/{fid}")
    @ResponseBody
    public Map<String, Object> renameFile(@PathVariable Integer fid, @RequestParam String newName, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer uid = SessionUtil.getLoginUid(session);

        if (uid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        File file = fileService.getFileById(fid);
        if (file == null) {
            result.put("success", false);
            result.put("message", "file not found");
            return result;
        }

        if (!file.getUid().equals(uid)) {
            result.put("success", false);
            result.put("message", "access denied");
            return result;
        }

        boolean success = fileService.renameFile(fid, newName);
        result.put("success", success);
        result.put("message", success ? "file renamed" : "rename failed");

        return result;
    }

    @PostMapping("/changeCategory/{fid}")
    @ResponseBody
    public Map<String, Object> changeCategory(@PathVariable Integer fid, @RequestParam String category, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer uid = SessionUtil.getLoginUid(session);

        if (uid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        File file = fileService.getFileById(fid);
        if (file == null) {
            result.put("success", false);
            result.put("message", "file not found");
            return result;
        }

        if (!file.getUid().equals(uid)) {
            result.put("success", false);
            result.put("message", "access denied");
            return result;
        }

        boolean success = fileService.changeCategory(fid, category);
        result.put("success", success);
        result.put("message", success ? "category changed" : "change failed");

        return result;
    }

    @PostMapping("/freeze/{fid}")
    @ResponseBody
    public Map<String, Object> freezeFile(@PathVariable Integer fid) {
        Map<String, Object> result = new HashMap<>();
        boolean success = fileService.freezeFile(fid);
        result.put("success", success);
        result.put("message", success ? "file frozen" : "freeze failed");
        return result;
    }

    @PostMapping("/unfreeze/{fid}")
    @ResponseBody
    public Map<String, Object> unfreezeFile(@PathVariable Integer fid) {
        Map<String, Object> result = new HashMap<>();
        boolean success = fileService.unfreezeFile(fid);
        result.put("success", success);
        result.put("message", success ? "file unfrozen" : "unfreeze failed");
        return result;
    }
}
