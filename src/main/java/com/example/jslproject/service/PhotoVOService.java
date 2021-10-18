package com.example.jslproject.service;

import com.example.jslproject.dto.FileDto;
import com.example.jslproject.dto.PhotoVODto;
import com.example.jslproject.repository.PhotoVORepository;
import com.example.jslproject.util.MD5Generator;
import com.example.jslproject.vo.FileVO;
import com.example.jslproject.vo.PhotoBoardVO;
import com.example.jslproject.vo.PhotoVO;
import com.example.jslproject.vo.User;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Transactional
public class PhotoVOService {

    @Autowired
    PhotoVORepository photoVORepository;

    private PhotoVODto uploadPhoto(MultipartFile multipartFile, User user) throws Exception{
        UUID uuid = UUID.randomUUID();
        PhotoVODto photoVODto = null;
        try {

            System.out.println("inside"+multipartFile.getOriginalFilename());
            String realPhotoName = multipartFile.getOriginalFilename();
            String extension = realPhotoName.substring(realPhotoName.lastIndexOf("."));
            String savedPhotoName = uuid.toString()+extension;
            String savePath = System.getProperty("user.dir") + "\\filename" + "\\" + user.getProvider() + "\\" + user.getEmail()+"\\Photo";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String filePath = savePath + "\\" + savedPhotoName;

            photoVODto = new PhotoVODto();
            photoVODto.setRealPhotoName(realPhotoName);
            photoVODto.setRealPhotoSavePath(filePath);
            photoVODto.setServerPhotoName(savedPhotoName);
            multipartFile.transferTo(new File(filePath));


        } catch (Exception e) {
            e.getStackTrace();
        }
        return photoVODto;

    }

    public PhotoVO photoSave(MultipartFile multipartFile, User user, PhotoBoardVO photoBoardVO) throws Exception {
        PhotoVO photoVO = null;
        photoVO = new PhotoVO().createPhotoVO(uploadPhoto(multipartFile,user));
        photoVO.setPhotoBoardVO(photoBoardVO);
        if(photoVO!=null){
            photoVORepository.save(photoVO);
            PhotoVO photoVO1 = photoVORepository.getById(photoVO.getId());
            System.out.println(photoVO1.getId());
            return photoVO1;
        }else {
            return null;
        }

    }

}
