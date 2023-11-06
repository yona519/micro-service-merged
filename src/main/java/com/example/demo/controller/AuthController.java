package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.dao.UserDao;

import com.example.demo.model.AuthResponse;
import com.example.demo.model.UserInfoOutput;
import com.example.demo.model.UserOutput;
import com.example.demo.service.UserService;
import com.example.demo.service.serviceimpl.AuthServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class AuthController {
    
    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @GetMapping("/userlogin")
    public String userloginPage() {
        return "loginUser";
    }

    //USER LOGIN CONTROLLER
    @PostMapping("/loginUser")
    public String userlogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession httpSession) {
        UserOutput user = userDao.getByUsername(username);
        AuthResponse response = authService.authUser(username, password);

        if (response.isSuccess() && user != null) {
            // Successful login
            UserInfoOutput principal = userService.getUserById(user.getEmp_id());
            //stores the user in httpSession.
            httpSession.setAttribute("principal", principal);
            model.addAttribute("success", "Login Successfully");
            //stores the session in the model.
            model.addAttribute("principal", httpSession.getAttribute("principal"));
            model.addAttribute("user", userService.getUserById(user.getEmp_id()));
            return "redirect:/user/" + principal.getEmp_id();

        } else {
            // Failed login
            model.addAttribute("error", "Invalid username or password");
            return "loginUser";
        }
    }

    // Logout remove httpsession
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}