package com.example.jslproject.repository;


import com.example.jslproject.vo.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardVO,Long> {
    BoardVO findByUser(String userfullname);
    BoardVO findByBoardSubject(String subject);
}
