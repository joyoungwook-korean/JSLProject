package com.example.jslproject.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserJoinDto {

    @NotBlank(message = "Emailを入力してください")
    @Email(message = "emailの形式で入力してください")
    private String email;

    @NotBlank(message = "nameを入力してください")
    private String username;

    @NotBlank(message = "passwordを入力してください")
    @Length(min = 6, max = 16, message = "passwordは 6文字以上　16文字以下で入力をお願いします。")
    private String password;
}
