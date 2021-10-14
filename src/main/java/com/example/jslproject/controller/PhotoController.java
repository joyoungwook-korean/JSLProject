package com.example.jslproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @GetMapping("/index")
    public String index(){
        return "/photo/photo_04";
    }
}
