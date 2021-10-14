package com.example.jslproject.controller;

import com.example.jslproject.dto.PhotoBoardDto;
import com.example.jslproject.dto.UserJoinDto;
import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.service.PhotoBoardService;
import com.example.jslproject.vo.User;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoBoardService photoBoardService;


    @GetMapping("/index")
    public String index() {
        return "/photo/photo_04";
    }

    @GetMapping("/view")
    public String view() {
        return "photo/photo_view";
    }

    @GetMapping("/insertForm")
    public String insertForm( Model model) {
        model.addAttribute("photoBoardDto", new PhotoBoardDto());
        return "/photo/photo_insertForm";
    }

    @PostMapping("/insertForm")
    public String insertForm(@Valid PhotoBoardDto photoBoardDto, BindingResult bindingResult
            , Model model,@RequestParam("photoImgFile") List<MultipartFile> multipartFiles,@RequestParam("us") String aaa) {
       photoBoardDto.setUserId(aaa);

        if (bindingResult.hasErrors()) {
            return "/photo/photo_insertForm";
        }

        try{
            photoBoardService.saved_Photo_Baord(multipartFiles,photoBoardDto);
        }catch (Exception e){
            e.printStackTrace();
        }
         return "redirect:/photo/index";

    }


}
