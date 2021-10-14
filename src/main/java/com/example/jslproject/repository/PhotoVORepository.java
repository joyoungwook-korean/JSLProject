package com.example.jslproject.repository;

import com.example.jslproject.vo.PhotoVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoVORepository extends JpaRepository<PhotoVO, Long> {
}
