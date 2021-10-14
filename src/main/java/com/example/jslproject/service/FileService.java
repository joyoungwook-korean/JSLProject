package com.example.jslproject.service;

import com.example.jslproject.dto.BoardDto;
import com.example.jslproject.dto.FileDto;
import com.example.jslproject.repository.FileRepository;
import com.example.jslproject.util.MD5Generator;
import com.example.jslproject.vo.BoardVO;
import com.example.jslproject.vo.FileVO;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {


    private final FileRepository fileRepository;

    private FileDto md5_FileDto(MultipartFile multipartFile, String email, String user_provider) {
        FileDto fileDto = null;
        try {
            String realFileName = multipartFile.getOriginalFilename();
            String filename = new MD5Generator(realFileName).toString();
            String savePath = System.getProperty("user.dir") + "\\filename" + "\\" + user_provider + "\\" + email;
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

        } catch (Exception e) {
            e.getStackTrace();
        }
        return fileDto;
    }


    public FileVO saveFile(MultipartFile multipartFile, String email, String user_provider) {
        FileDto fileDto = md5_FileDto(multipartFile, email, user_provider);

        return fileRepository.save(FileVO.createFile(fileDto));
    }

    public FileVO getFile(Long id) {
        FileVO fileVO = fileRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return fileVO;
    }

    public FileVO update_File(BoardVO boardVO, MultipartFile multipartFile) {


        FileVO fileVO = boardVO.getBoardFile();

        FileDto fileDto = md5_FileDto(multipartFile, boardVO.getUser().getEmail(), boardVO.getUser().getProvider());

        if (fileVO != null && !fileVO.getRealFileSavePath().equals(fileDto.getRealFileSavePath())) {
            fileVO.setServerFileName(fileDto.getServerFileName());
            fileVO.setRealFileName(fileDto.getRealFileName());
            fileVO.setRealFileSavePath(fileDto.getRealFileSavePath());
        } else if (fileVO !=null&& fileVO.getRealFileSavePath().equals(fileDto.getRealFileSavePath())) {
            return fileVO;
        } else {
            fileVO = fileRepository.save(FileVO.createFile(fileDto));
        }

        return fileVO;

    }

    public BoardVO delete_Board_FileVO(BoardVO boardVO) {
        FileVO fileVO = boardVO.getBoardFile();
        if (fileVO != null) {
            File file = new File(boardVO.getBoardFile().getRealFileSavePath());
            file.delete();
            boardVO.setBoardFile(null);
            fileRepository.delete(fileVO);
        }
        return boardVO;
    }

    public boolean checked_File_name(String realFileName){
        FileVO filevo_check = fileRepository.findByRealFileSavePath(realFileName);

        if(filevo_check != null){
            return false;
        }
        return true;


    }


}
