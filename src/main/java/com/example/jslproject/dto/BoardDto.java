package com.example.jslproject.dto;

import com.example.jslproject.vo.FileVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.File;

@Data
public class BoardDto {



    private String boardWriteName;

    @NotBlank(message = "contentを入力してください")
    private String boardContents;

    @NotBlank(message = "subjectを入力してください")
    private String boardSubject;

    private FileVO fileVO;

}
