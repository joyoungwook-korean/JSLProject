package com.example.jslproject.repository;

import com.example.jslproject.vo.FileVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileVO,Long> {
    FileVO findByRealFileSavePath(String realFileSavePath);
}
