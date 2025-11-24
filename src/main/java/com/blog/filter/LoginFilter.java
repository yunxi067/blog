package com.blog.filter;

import com.blog.util.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
            "/",
            "/index",
            "/user/login",
            "/user/register",
            "/user/doLogin",
            "/user/doRegister",
            "/static/",
            "/public.jsp"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        boolean isPublicPath = false;
        for (String publicPath : PUBLIC_PATHS) {
            if (path.equals(publicPath) || path.startsWith(publicPath)) {
                isPublicPath = true;
                break;
            }
        }

        if (isPublicPath) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!SessionUtil.isLogin(request.getSession())) {
            response.sendRedirect(contextPath + "/user/login");
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
