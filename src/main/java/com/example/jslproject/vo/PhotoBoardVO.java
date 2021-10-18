package com.example.jslproject.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class PhotoBoardVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "userfullname")
    private User user;

    private String contents;

    @OneToMany
    private List<PhotoVO> photoVOList;

    @CreationTimestamp
    private Timestamp createTimestamp;

    @UpdateTimestamp
    private Timestamp updateTimestamp;


}
