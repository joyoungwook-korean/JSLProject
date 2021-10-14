package com.example.jslproject.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@ToString
@NoArgsConstructor
@Data
public class PhotoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realPhotoName;

    private String realPhotoPath;

    private String serverFileName;



    @CreationTimestamp
    private Timestamp photoCreateTimestamp;


    @UpdateTimestamp
    private Timestamp photoUpdateTimestamp;
}
