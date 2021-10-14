package com.example.jslproject.service;

import com.example.jslproject.dto.FileDto;
import com.example.jslproject.repository.PhotoVORepository;
import com.example.jslproject.util.MD5Generator;
import com.example.jslproject.vo.FileVO;
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
@Log
@Transactional
public class PhotoVOService {

    @Autowired
    PhotoVORepository photoVORepository;

    private PhotoVO uploadPhoto(MultipartFile multipartFile, User user) throws Exception{
        UUID uuid = UUID.randomUUID();
        PhotoVO photoVO = new PhotoVO();
        try {
            String realPhotoName = multipartFile.getOriginalFilename();
            String extension = realPhotoName.substring(realPhotoName.lastIndexOf("."));
            String savedPhotoName = uuid.toString()+extension;
            String savePath = System.getProperty("user.dir") + "\\filename" + "\\" + user.getProvider() + "\\" + user.getUsername() +"\\" +"Photo";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String filePath = savePath + "\\" + savedPhotoName;
            multipartFile.transferTo(new File(filePath));

            photoVO.setRealPhotoName(realPhotoName);
            photoVO.setServerFileName(savedPhotoName);
            photoVO.setRealPhotoPath(filePath);


        } catch (Exception e) {
            e.getStackTrace();
        }
        return photoVO;

    }

    public void deletePhoto(String filePath) throws Exception{
        File deleteFile = new File(filePath);

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일삭제완료");
        }else{
            log.info("파일이 존재x");
        }

    }

    public PhotoVO photoSave(MultipartFile multipartFile, User user) throws Exception {
        PhotoVO photoVO = uploadPhoto(multipartFile,user);
        photoVORepository.save(photoVO);
        return photoVO;
    }



}
