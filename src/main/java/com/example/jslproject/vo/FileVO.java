package com.example.jslproject.vo;

import com.example.jslproject.dto.FileDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@ToString
public class FileVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realFileName;

    @Column(unique = true)
    private String realFileSavePath;

    private String serverFileName;

    @UpdateTimestamp
    private Timestamp updateDate;

    @CreationTimestamp
    private Timestamp createDate;

    public static FileVO createFile(FileDto fileDto ){
        FileVO fileVO = new FileVO();
        fileVO.setRealFileName(fileDto.getRealFileName());
        fileVO.setServerFileName(fileDto.getServerFileName());
        fileVO.setRealFileSavePath(fileDto.getRealFileSavePath());
        return fileVO;
    }

    @Builder
    public FileVO(Long id, String realFileName, String realFileSavePath, String serverFileName, Timestamp updateDate, Timestamp createDate) {
        this.id = id;
        this.realFileName = realFileName;
        this.realFileSavePath = realFileSavePath;
        this.serverFileName = serverFileName;
        this.updateDate = updateDate;
        this.createDate = createDate;
    }
}
