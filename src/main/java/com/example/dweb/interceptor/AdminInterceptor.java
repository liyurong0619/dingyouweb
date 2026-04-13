package com.example.dweb.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        Boolean admin = (Boolean) session.getAttribute("isAdminLoggedIn"); // <-- 統一名稱

        if (admin == null || !admin) {
            response.sendRedirect("/admin/login");
            return false;
        }

        return true;
    }
}
