package com.example.jslproject.controller;

import com.example.jslproject.auth.CustomDetails;
import com.example.jslproject.dto.BoardDto;
import com.example.jslproject.repository.BoardRepository;
import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.service.BoardService;
import com.example.jslproject.vo.BoardVO;
import com.example.jslproject.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @GetMapping(value = "/list")
    public String list(Model model,@PageableDefault(size = 7) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<BoardVO> boardVO = boardRepository.findByBoardSubjectContainingOrBoardContentsContaining(searchText,searchText,pageable);
        int startPage = Math.max(0,boardVO.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boardVO.getTotalPages(), boardVO.getPageable().getPageNumber()+4);
        model.addAttribute("boardvo", boardVO);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/list";
    }


    @GetMapping("/write")
    public String write(@AuthenticationPrincipal CustomDetails customDetails, Model model) {
        User user = userRepository.findByUserFullName(customDetails.getUserFullname());
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("userAll", user);
        model.addAttribute("boardDto", new BoardDto());
        return "/board/write";
    }

    @PostMapping("/write")
    public String write(@Valid BoardDto boardDto, BindingResult bindingResult,
                        @RequestParam("userfullname") String userfullname,
            @RequestParam("username") String username
            , Model model) {
        if (bindingResult.hasErrors()) {
            return "/board/write";
        }
        User user = userRepository.findByUserFullName(userfullname);
        BoardVO aa = boardService.saveBoard(boardDto,user);


        return "redirect:/board/list";
    }

    @GetMapping("/modify")
    public String modify(@AuthenticationPrincipal CustomDetails customDetails
            ,Model model, @RequestParam("id") Long id){
        BoardVO boardVO = boardRepository.getById(id);
        if(customDetails.getUser().getUserFullName().equals(boardVO.getUser().getUserFullName())){
            model.addAttribute("truea","same");
        }else{
            boardService.countAdd(boardVO);
        }

        model.addAttribute("thisUserVO",customDetails);
        model.addAttribute("boardvo",boardVO);
        model.addAttribute("boardDto", new BoardDto());
        return "board/modify";
    }

    @PostMapping("/modify")
    public String modify(@RequestParam("subject") String subject,@RequestParam("contents") String contents,@RequestParam("id") Long id){
        BoardVO boardVO = boardRepository.getById(id);
        boardService.update(boardVO,subject,contents);
        return "redirect:/board/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("boardId") String id){
        Long id_x = Long.parseLong(id);
        BoardVO boardVO = boardRepository.findById(id_x).orElseThrow(IllegalArgumentException::new);
        boardRepository.delete(boardVO);
        return "redirect:/board/list";
    }
}

