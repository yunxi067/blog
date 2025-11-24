package com.blog.filter;

import com.blog.domain.User;
import com.blog.util.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorityFilter implements Filter {

    private static final String[] ADMIN_PATHS = {
            "/admin/",
            "/space/freeze",
            "/space/unfreeze",
            "/user/freeze",
            "/user/unfreeze",
            "/apply/list",
            "/apply/approve",
            "/apply/reject"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        boolean isAdminPath = false;
        for (String adminPath : ADMIN_PATHS) {
            if (path.equals(adminPath) || path.startsWith(adminPath)) {
                isAdminPath = true;
                break;
            }
        }

        if (!isAdminPath) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        User loginUser = SessionUtil.getLoginUser(request.getSession());
        if (loginUser == null || loginUser.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
