package com.blog.controller;

import com.blog.domain.Space;
import com.blog.domain.SpaceApply;
import com.blog.service.SpaceApplyService;
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
@RequestMapping("/apply")
public class SpaceApplyController {

    @Autowired
    private SpaceApplyService spaceApplyService;

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/request")
    public String applyPage(HttpSession session, Model model) {
        Integer uid = SessionUtil.getLoginUid(session);
        if (uid == null) {
            return "redirect:/user/login";
        }

        Space space = spaceService.getSpaceByUid(uid);
        boolean canApply = spaceApplyService.canApplyForExpansion(uid);

        model.addAttribute("space", space);
        model.addAttribute("canApply", canApply);

        return "apply/request";
    }

    @PostMapping("/doRequest")
    @ResponseBody
    public Map<String, Object> applyForExpansion(@RequestParam Long applySize,
                                                  @RequestParam(required = false) String reason,
                                                  HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer uid = SessionUtil.getLoginUid(session);

        if (uid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        if (!spaceApplyService.canApplyForExpansion(uid)) {
            result.put("success", false);
            result.put("message", "download count must be >= 50");
            return result;
        }

        SpaceApply spaceApply = new SpaceApply();
        spaceApply.setUid(uid);
        spaceApply.setApplySize(applySize);
        spaceApply.setReason(reason);

        boolean success = spaceApplyService.createApply(spaceApply);
        result.put("success", success);
        result.put("message", success ? "application submitted" : "submit failed");

        return result;
    }

    @GetMapping("/list")
    public String listApplies(Model model) {
        List<SpaceApply> applies = spaceApplyService.getPendingApplies();
        model.addAttribute("applies", applies);
        return "admin/apply-list";
    }

    @GetMapping("/myApplies")
    public String myApplies(HttpSession session, Model model) {
        Integer uid = SessionUtil.getLoginUid(session);
        if (uid == null) {
            return "redirect:/user/login";
        }

        List<SpaceApply> applies = spaceApplyService.getApplyByUid(uid);
        model.addAttribute("applies", applies);

        return "apply/my-applies";
    }

    @PostMapping("/approve/{applyId}")
    @ResponseBody
    public Map<String, Object> approveApply(@PathVariable Integer applyId, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer adminUid = SessionUtil.getLoginUid(session);

        if (adminUid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        boolean success = spaceApplyService.approveApply(applyId, adminUid);
        result.put("success", success);
        result.put("message", success ? "application approved" : "approve failed");

        return result;
    }

    @PostMapping("/reject/{applyId}")
    @ResponseBody
    public Map<String, Object> rejectApply(@PathVariable Integer applyId, @RequestParam String reason) {
        Map<String, Object> result = new HashMap<>();

        boolean success = spaceApplyService.rejectApply(applyId, reason);
        result.put("success", success);
        result.put("message", success ? "application rejected" : "reject failed");

        return result;
    }
}
