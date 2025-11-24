package com.blog.controller;

import com.blog.domain.Space;
import com.blog.service.SpaceService;
import com.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/space")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/info")
    @ResponseBody
    public Map<String, Object> getSpaceInfo(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer uid = SessionUtil.getLoginUid(session);

        if (uid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        Space space = spaceService.getSpaceByUid(uid);
        if (space == null) {
            result.put("success", false);
            result.put("message", "space not found");
            return result;
        }

        result.put("success", true);
        result.put("space", space);

        return result;
    }

    @GetMapping("/list")
    public String listSpaces(Model model) {
        List<Space> spaces = spaceService.getAllSpaces();
        model.addAttribute("spaces", spaces);
        return "admin/space-list";
    }

    @PostMapping("/freeze/{uid}")
    @ResponseBody
    public Map<String, Object> freezeSpace(@PathVariable Integer uid) {
        Map<String, Object> result = new HashMap<>();
        boolean success = spaceService.freezeSpace(uid);
        result.put("success", success);
        result.put("message", success ? "space frozen" : "freeze failed");
        return result;
    }

    @PostMapping("/unfreeze/{uid}")
    @ResponseBody
    public Map<String, Object> unfreezeSpace(@PathVariable Integer uid) {
        Map<String, Object> result = new HashMap<>();
        boolean success = spaceService.unfreezeSpace(uid);
        result.put("success", success);
        result.put("message", success ? "space unfrozen" : "unfreeze failed");
        return result;
    }
}
