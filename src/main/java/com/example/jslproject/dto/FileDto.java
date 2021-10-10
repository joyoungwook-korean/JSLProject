package com.example.jslproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Data
public class FileDto {

    private String realFileName;

    private String realFileSavePath;

    private String serverFileName;


}
