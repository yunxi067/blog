package com.blog.util;

import com.blog.domain.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static final String USER_SESSION_KEY = "login_user";
    private static final String USER_ID_SESSION_KEY = "login_uid";

    public static void setLoginUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
        session.setAttribute(USER_ID_SESSION_KEY, user.getUid());
    }

    public static User getLoginUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static Integer getLoginUid(HttpSession session) {
        Object obj = session.getAttribute(USER_ID_SESSION_KEY);
        if (obj == null) {
            return null;
        }
        return (Integer) obj;
    }

    public static void removeLoginUser(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        session.removeAttribute(USER_ID_SESSION_KEY);
        session.invalidate();
    }

    public static boolean isLogin(HttpSession session) {
        return getLoginUid(session) != null;
    }

    public static HttpSession getCurrentSession() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        return attrs.getRequest().getSession();
    }

    public static User getCurrentLoginUser() {
        HttpSession session = getCurrentSession();
        if (session == null) {
            return null;
        }
        return getLoginUser(session);
    }

    public static Integer getCurrentLoginUid() {
        HttpSession session = getCurrentSession();
        if (session == null) {
            return null;
        }
        return getLoginUid(session);
    }
}
