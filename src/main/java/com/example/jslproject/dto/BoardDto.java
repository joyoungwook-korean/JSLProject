package com.example.jslproject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardDto {



    private String boardWriteName;

    @NotBlank(message = "contentを入力してください")
    private String boardContents;

    @NotBlank(message = "subjectを入力してください")
    private String boardSubject;

    private String boardFilename;

}
