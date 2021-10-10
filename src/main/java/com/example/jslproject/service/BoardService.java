package com.example.jslproject.service;

import com.example.jslproject.dto.BoardDto;
import com.example.jslproject.repository.BoardRepository;
import com.example.jslproject.vo.BoardVO;
import com.example.jslproject.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        boardVO.setBoardFile(boardDto.getFileVO());
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

    public void boardVO_Delete(BoardVO boardVO){
        boardRepository.delete(boardVO);
    }

    public Page<BoardVO> check_get_dropBox(String drop_check,String searchText, Pageable pageable){
        Page<BoardVO> boardVO=null;
        if(drop_check.equals("action") || drop_check.equals("subject")){
            boardVO = boardRepository.findByBoardSubjectContainingOrBoardContentsContaining(searchText,searchText,pageable);
        }else if(drop_check.equals("writer")){
            boardVO = boardRepository.findByBoardWriteNameContaining(searchText,pageable);
        }else if(drop_check.equals("provider")){
            boardVO = boardRepository.findByUserProvider(searchText,pageable);
        }
        return  boardVO;
    }





}
