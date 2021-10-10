package com.example.jslproject.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author siosi
 */


@Data
@NoArgsConstructor
@ToString
@Entity
public class BoardVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardWriteName;

    private String boardContents;

    private String boardSubject;

    private int boardCount;

    @OneToOne
    @JoinColumn(name = "realFileSavePath")
    private FileVO boardFile;

    @ManyToOne
    @JoinColumn(name = "userfullname")
    private User user;

    @CreationTimestamp
    private Timestamp board_create_time;

    @UpdateTimestamp
    private Timestamp board_update_time;

}
