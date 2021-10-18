package com.example.jslproject.repository;

import com.example.jslproject.vo.PhotoBoardVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoBoardVORepository extends JpaRepository<PhotoBoardVO,Long> {

}
