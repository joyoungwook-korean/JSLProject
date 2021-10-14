package com.example.jslproject.dto;

import com.example.jslproject.vo.PhotoVO;
import com.example.jslproject.vo.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class PhotoBoardDto {

    @NotBlank(message = "Titleを入力してください")
    @Length(max = 12, message = "12以下で入力してください")
    private String title;

    private String userId;

    @NotBlank(message = "contentsを入力してください")
    private String contents;

    private List<PhotoVO> photoVOList = new ArrayList<>();

    private List<Long> photoVOImgList = new ArrayList<>();

}
