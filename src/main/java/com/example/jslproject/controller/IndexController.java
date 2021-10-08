package com.example.jslproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping( "/loginform")
    public String loginform(Model model){
        return "/loginform";
    }

    @PostMapping( "/loginform")
    public String loginform(){
        return "/loginform";
    }
}
