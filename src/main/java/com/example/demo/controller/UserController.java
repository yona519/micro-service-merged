package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.ProjectInvolved;
import com.example.demo.model.UserInfoOutput;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public String index(@PathVariable String id, Model model, HttpSession httpSession) {
        UserInfoOutput user = userService.getUserById(id);
        List<ProjectInvolved> projectsOfUser = projectService.getAllProjectsOfUser(id);
        
        model.addAttribute("principal", httpSession.getAttribute("principal"));
        model.addAttribute("userInfo", user);
        model.addAttribute("projectsOfUser", projectsOfUser);
        return "userInfo";
    }

}
