package com.blog.controller;

import com.blog.domain.User;
import com.blog.domain.Space;
import com.blog.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/doLogin")
    public String login(@RequestParam String username, @RequestParam String password, 
                        HttpSession session, Model model) {
        User user = userService.getLoginUser(username, password);
        if (user == null) {
            model.addAttribute("message", "username or password incorrect");
            return "user/login";
        }

        if (user.getStatus() == 0) {
            model.addAttribute("message", "user account is frozen");
            return "user/login";
        }

        SessionUtil.setLoginUser(session, user);
        return "redirect:/user/space";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }

    @PostMapping("/doRegister")
    public String register(@RequestParam String username, @RequestParam String password,
                          @RequestParam String confirmPassword, @RequestParam String email,
                          Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "passwords do not match");
            return "user/register";
        }

        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            model.addAttribute("message", "username already exists");
            return "user/register";
        }

        User user = new User(username, password, email);
        boolean registerSuccess = userService.register(user);

        if (!registerSuccess) {
            model.addAttribute("message", "registration failed");
            return "user/register";
        }

        User registeredUser = userService.getUserByUsername(username);
        Space space = new Space(registeredUser.getUid(), 1024L * 1024L * 100L);
        spaceService.createSpace(space);

        model.addAttribute("message", "registration successful, please login");
        return "user/login";
    }

    @GetMapping("/space")
    public String userSpace(HttpSession session, Model model) {
        Integer uid = SessionUtil.getLoginUid(session);
        if (uid == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(uid);
        Space space = spaceService.getSpaceByUid(uid);

        model.addAttribute("user", user);
        model.addAttribute("space", space);

        return "user/space";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtil.removeLoginUser(session);
        return "redirect:/index";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Integer uid = SessionUtil.getLoginUid(session);
        if (uid == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(uid);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/updateProfile")
    @ResponseBody
    public Map<String, Object> updateProfile(@RequestBody User user, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer uid = SessionUtil.getLoginUid(session);

        if (uid == null) {
            result.put("success", false);
            result.put("message", "not logged in");
            return result;
        }

        user.setUid(uid);
        boolean success = userService.updateUser(user);

        if (success) {
            User updatedUser = userService.getUserById(uid);
            SessionUtil.setLoginUser(session, updatedUser);
            result.put("success", true);
            result.put("message", "profile updated successfully");
        } else {
            result.put("success", false);
            result.put("message", "profile update failed");
        }

        return result;
    }

    @GetMapping("/list")
    public String userList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    @PostMapping("/freeze/{uid}")
    @ResponseBody
    public Map<String, Object> freezeUser(@PathVariable Integer uid) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.freezeUser(uid);
        result.put("success", success);
        result.put("message", success ? "user frozen" : "freeze failed");
        return result;
    }

    @PostMapping("/unfreeze/{uid}")
    @ResponseBody
    public Map<String, Object> unfreezeUser(@PathVariable Integer uid) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.unfreezeUser(uid);
        result.put("success", success);
        result.put("message", success ? "user unfrozen" : "unfreeze failed");
        return result;
    }
}
