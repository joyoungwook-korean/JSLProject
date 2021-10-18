package com.example.jslproject.controller;

import com.example.jslproject.dto.PhotoBoardDto;
import com.example.jslproject.dto.UserJoinDto;
import com.example.jslproject.repository.PhotoBoardVORepository;
import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.service.PhotoBoardService;
import com.example.jslproject.service.PhotoVOService;
import com.example.jslproject.vo.PhotoBoardVO;
import com.example.jslproject.vo.PhotoVO;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoBoardVORepository photoBoardVORepository;

    @Autowired
    PhotoBoardService photoBoardService;

    @Autowired
    PhotoVOService photoVOService;

    @GetMapping("/index")
    public String index(Model model) {

        List<PhotoBoardVO> photoBoardVOList = photoBoardVORepository.findAll();
        model.addAttribute("photoBoardList" , photoBoardVOList);

        return "/photo/photo_04";
    }

    @GetMapping("/view")
    public String view(@RequestParam("photoBoardIdx") String id,Model model) {
        PhotoBoardVO photoBoardVO = photoBoardVORepository.getById(Long.parseLong(id));
        model.addAttribute("photoBoardVO", photoBoardVO);
        return "/photo/photo_view";
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

       User user = userRepository.getById(Long.parseLong(photoBoardDto.getUserId()));
        List<PhotoVO> photoVOList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            return "/photo/photo_insertForm";
        }

        try{
            PhotoBoardVO photoBoardVO = photoBoardService.saved_Photo_Baord(photoBoardDto,user);

            for(MultipartFile multipartFile : multipartFiles){
                System.out.println(multipartFiles.size());
                if(!multipartFile.getOriginalFilename().equals("")){
                   photoVOList.add(photoVOService.photoSave(multipartFile, user,photoBoardVO));
                }
            }
            PhotoBoardVO photoBoardVO1 = photoBoardService.updatePhotoVO(photoVOList,photoBoardVO);
            System.out.println(photoBoardVO1.getPhotoVOList().get(0).getServerPhotoName());
        }catch (Exception e){
            e.printStackTrace();
        }
         return "redirect:/photo/index";

    }


}
