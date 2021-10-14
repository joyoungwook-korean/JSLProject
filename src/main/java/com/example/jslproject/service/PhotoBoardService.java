package com.example.jslproject.service;

import com.example.jslproject.dto.PhotoBoardDto;
import com.example.jslproject.repository.PhotoBoardVORepository;
import com.example.jslproject.repository.UserRepository;
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

    public PhotoBoardDto saved_Photo_Baord(List<MultipartFile> multipartFiles, PhotoBoardDto photoBoardDto) throws Exception {
        PhotoBoardVO photoBoardVO = new PhotoBoardVO();

        photoBoardVO.setContents(photoBoardDto.getContents());
        photoBoardVO.setTitle(photoBoardDto.getTitle());
        User user=userRepository.getById(Long.parseLong(photoBoardDto.getUserId()) );
        photoBoardVO.setUser(user);
        List<PhotoVO> aa = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles){
             aa.add( photoVOService.photoSave(multipartFile,user));
        }
        photoBoardVO.setPhotoVOList(aa);
        photoBoardDto.setPhotoVOList(aa);

        photoBoardVORepository.save(photoBoardVO);

        return photoBoardDto;

    }
}
