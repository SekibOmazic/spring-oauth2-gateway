package com.comsysto.dcc.auth.controller;

import com.comsysto.dcc.auth.user.User;
import com.comsysto.dcc.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/list")
    public ModelAndView usersAll(ModelAndView model) {
        model.addObject("users", userRepository.findAll());
        model.setViewName("usersList");
        return model;
    }
    
    @GetMapping("/new")
    public ModelAndView users(ModelAndView model) {
        User user = new User();
        model.addObject("user", user);
        model.setViewName("users");
        return model;
    }
    
    @PostMapping("/save")
    public void usersSubmit(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        userRepository.save(user);
        ControllerHelper.constructRedirectURL("/user/list", request, response);
    }
    
}
