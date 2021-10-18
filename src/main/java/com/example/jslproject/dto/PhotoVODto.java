package com.example.jslproject.dto;

import com.example.jslproject.vo.PhotoBoardVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PhotoVODto {
    private String realPhotoName;

    private String realPhotoSavePath;

    private String serverPhotoName;

    private PhotoBoardVO photoBoardVO;
}
