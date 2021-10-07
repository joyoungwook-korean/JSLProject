package com.example.jslproject.controller;

import com.example.jslproject.dto.UserJoinDto;
import com.example.jslproject.service.UserService;
import com.example.jslproject.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller("/user")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @GetMapping(value = "/joinform")
    public String joinform(Model model){
        model.addAttribute("userJoinDto",new UserJoinDto());
        return "/user/joinform";
    }

    @PostMapping(value = "/joinform")
    public String  joinform(@Valid UserJoinDto userJoinDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/user/joinform";
        }
        try{
            User user = User.createUser(userJoinDto,passwordEncoder);
            userService.saveUser(user);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/joinform";
        }

        return "redirect:/";
    }
}
