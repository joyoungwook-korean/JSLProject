package com.example.jslproject.vo;

import com.example.jslproject.dto.FileDto;
import com.example.jslproject.dto.PhotoVODto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@EqualsAndHashCode(callSuper = false, exclude = {"id"})
@Entity
@NoArgsConstructor
@Data
public class PhotoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realPhotoName;

    private String realPhotoPath;

    private String serverPhotoName;

    @ManyToOne
    private PhotoBoardVO photoBoardVO;

    @CreationTimestamp
    private Timestamp photoCreateTimestamp;


    @UpdateTimestamp
    private Timestamp photoUpdateTimestamp;



    public PhotoVO createPhotoVO(PhotoVODto photoVODto){
        PhotoVO photoVO = new PhotoVO();
        photoVO.setRealPhotoPath(photoVODto.getRealPhotoSavePath());
        photoVO.setRealPhotoName(photoVODto.getRealPhotoName());
        photoVO.setServerPhotoName(photoVODto.getServerPhotoName());
        photoVO.setPhotoBoardVO(photoVODto.getPhotoBoardVO());
        return photoVO;
    }

    @Builder
    public PhotoVO(Long id, String realPhotoName, String realPhotoPath, String serverPhotoName, Timestamp photoCreateTimestamp, Timestamp photoUpdateTimestamp) {
        this.id = id;
        this.realPhotoName = realPhotoName;
        this.realPhotoPath = realPhotoPath;
        this.serverPhotoName = serverPhotoName;
        this.photoCreateTimestamp = photoCreateTimestamp;
        this.photoUpdateTimestamp = photoUpdateTimestamp;
    }
}
