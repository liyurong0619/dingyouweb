package com.example.dweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    
    private static final String ADMIN_ACCOUNT = "528300";
    private static final String ADMIN_PASSWORD = "23582855";

    // 登入頁面
    
    @GetMapping("/login")
    public String loginPage() {
        return "admin-login"; 
    }
    // 登入處理

    @PostMapping("/login")
    public String loginProcess(@RequestParam String account,
                               @RequestParam String password,
                               HttpSession session) {

        //方便測試，印出收到的帳號密碼
        System.out.println("POST /admin/login 收到請求: " + account + " / " + password);

        if (ADMIN_ACCOUNT.equals(account) && ADMIN_PASSWORD.equals(password)) {
            // 登入成功，把標記放 session
            session.setAttribute("isAdminLoggedIn", true);
            System.out.println("登入成功，導向 dashboard");
            return "redirect:/admin/dashboard";
        } else {
            // 登入失敗，redirect 回登入頁加參數
            System.out.println("登入失敗");
            return "redirect:/admin/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清空 session
        System.out.println("已登出，導回登入頁");
        return "redirect:/admin/login";
    }
}
