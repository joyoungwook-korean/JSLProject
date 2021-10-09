package com.example.jslproject.service;

import com.example.jslproject.dto.BoardDto;
import com.example.jslproject.repository.BoardRepository;
import com.example.jslproject.vo.BoardVO;
import com.example.jslproject.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public BoardVO saveBoard(BoardDto boardDto, User user){
        BoardVO boardVO = new BoardVO();
        boardVO.setBoardSubject(boardDto.getBoardSubject());
        boardVO.setUser(user);
        boardVO.setBoardCount(0);
        boardVO.setBoardFilename(boardDto.getBoardFilename());
        boardVO.setBoardWriteName(user.getUsername());
        boardVO.setBoardContents(boardDto.getBoardContents());
        return boardRepository.save(boardVO);
    }
    public void countAdd(BoardVO boardVO){
        boardVO.setBoardCount(boardVO.getBoardCount()+1);

    }

    public BoardVO update(BoardVO boardVO,String subject, String contents){
        boardVO.setBoardContents(contents);
        boardVO.setBoardSubject(subject);
        return boardVO;
    }



}
