package com.example.jslproject.service;

import com.example.jslproject.dto.BoardDto;
import com.example.jslproject.dto.FileDto;
import com.example.jslproject.repository.FileRepository;
import com.example.jslproject.util.MD5Generator;
import com.example.jslproject.vo.FileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {


    private final FileRepository fileRepository;

    public FileVO saveFile(MultipartFile multipartFile, String email, String user_provider){
        FileDto fileDto = null;
        try {
            String realFileName = multipartFile.getOriginalFilename();
            String filename = new MD5Generator(realFileName).toString();
            String savePath = System.getProperty("user.dir") + "\\filename"+"\\"+user_provider+"\\"+email;
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            multipartFile.transferTo(new File(filePath));

            fileDto = new FileDto();
            fileDto.setRealFileName(realFileName);
            fileDto.setRealFileSavePath(filePath);
            fileDto.setServerFileName(filename);
        }catch (Exception e){
            e.printStackTrace();
        }

        return fileRepository.save(FileVO.createFile(fileDto));
    }

    public FileVO getFile(Long id){
        FileVO fileVO = fileRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return fileVO;
    }


}
