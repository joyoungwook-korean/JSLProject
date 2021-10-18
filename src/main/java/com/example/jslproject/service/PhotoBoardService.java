package com.example.jslproject.service;

import com.example.jslproject.dto.BoardDto;
import com.example.jslproject.dto.PhotoBoardDto;
import com.example.jslproject.dto.PhotoVODto;
import com.example.jslproject.repository.PhotoBoardVORepository;
import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.vo.BoardVO;
import com.example.jslproject.vo.PhotoBoardVO;
import com.example.jslproject.vo.PhotoVO;
import com.example.jslproject.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PhotoBoardService {

    @Autowired
    PhotoBoardVORepository photoBoardVORepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoVOService photoVOService;

    public PhotoBoardVO saved_Photo_Baord( PhotoBoardDto photoBoardDto, User user) throws Exception {
        PhotoBoardVO photoBoardVO = new PhotoBoardVO();

        photoBoardVO.setContents(photoBoardDto.getContents());
        photoBoardVO.setTitle(photoBoardDto.getTitle());
        photoBoardVO.setPhotoVOList(photoBoardDto.getPhotoVOList());
        photoBoardVO.setUser(user);

        photoBoardVORepository.save(photoBoardVO);

        return photoBoardVO;

    }

    public PhotoBoardVO updatePhotoVO(List<PhotoVO> photoVOList, PhotoBoardVO photoBoardVO){
        photoBoardVO.setPhotoVOList(photoVOList);
        return photoBoardVO;
    }
}
