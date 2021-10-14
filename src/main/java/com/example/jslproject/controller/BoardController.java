package com.example.jslproject.controller;

import com.example.jslproject.auth.CustomDetails;
import com.example.jslproject.dto.BoardDto;
import com.example.jslproject.dto.FileDto;
import com.example.jslproject.repository.BoardRepository;
import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.service.BoardService;
import com.example.jslproject.service.FileService;
import com.example.jslproject.util.MD5Generator;
import com.example.jslproject.vo.BoardVO;
import com.example.jslproject.vo.FileVO;
import com.example.jslproject.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Autowired
    FileService fileService;

    @GetMapping(value = "/list")
    public String list(Model model, @PageableDefault(size = 7) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText,
                       @RequestParam(required = false, defaultValue = "action") String drop_check) {
        Page<BoardVO> boardVO = boardService.check_get_dropBox(drop_check, searchText, pageable);
        int startPage = Math.max(0, boardVO.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boardVO.getTotalPages(), boardVO.getPageable().getPageNumber() + 4);
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
                        @RequestParam("file") MultipartFile multipartFile
            , Model model) {

        if (bindingResult.hasErrors()) {
            return "/board/write";
        }

        User user = userRepository.findByUserFullName(userfullname);
        if (!multipartFile.isEmpty()) {
            boardDto.setFileVO(fileService.saveFile(multipartFile, user.getEmail(), user.getProvider()));
        }

        boardService.saveBoard(boardDto, user);


        return "redirect:/board/list";
    }

    @GetMapping("/modify")
    public String modify(@AuthenticationPrincipal CustomDetails customDetails
            , Model model, @RequestParam("id") Long id
    ) {
        BoardVO boardVO = boardRepository.getById(id);
        if (customDetails.getUser().getUserFullName().equals(boardVO.getUser().getUserFullName())) {
            model.addAttribute("truea", "same");
        } else {
            boardService.countAdd(boardVO);
        }

        model.addAttribute("thisUserVO", customDetails);
        model.addAttribute("boardvo", boardVO);
        model.addAttribute("boardDto", new BoardDto());
        return "board/modify";
    }

    @PostMapping("/modify")
    public String modify(@RequestParam("subject") String subject, @RequestParam("file") MultipartFile multipartFile
            , @RequestParam("contents") String contents, @RequestParam("id") Long id) {
        BoardVO boardVO = boardRepository.getById(id);
        FileVO fileVO = null;

        System.out.println(multipartFile.getOriginalFilename());
        if (!multipartFile.isEmpty()) {
            if (boardVO.getBoardFile() != null && boardVO.getBoardFile().getRealFileName().equals(multipartFile.getOriginalFilename())) {
                fileService.delete_Board_FileVO(boardVO);
            }
            fileVO = fileService.update_File(boardVO, multipartFile);
        }
        boardService.update(boardVO, subject, contents, fileVO);

        return "redirect:/board/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("boardId") String id) {
        Long id_x = Long.parseLong(id);
        BoardVO boardVO = boardRepository.findById(id_x).orElseThrow(IllegalArgumentException::new);

        fileService.delete_Board_FileVO(boardVO);
        boardRepository.delete(boardVO);
        return "redirect:/board/list";
    }

    @GetMapping("/download/{field}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("field") Long field) throws IOException {
        FileVO fileVO = fileService.getFile(field);
        Path path = Paths.get(fileVO.getRealFileSavePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-steam"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileVO.getRealFileName() + "\"")
                .body(resource);
    }
}

