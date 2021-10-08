package com.example.jslproject.vo;

import com.example.jslproject.constant.Role;
import com.example.jslproject.dto.UserJoinDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userFullName;


    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String provider;

    private String provider_id;


    private String password;


    @CreationTimestamp
    private Timestamp createDate;

    public static User createUser(UserJoinDto insertDto, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setUsername(insertDto.getUsername());
        user.setEmail(insertDto.getEmail());
        user.setPassword(passwordEncoder.encode(insertDto.getPassword()));
        String provider = "local";
        user.setProvider(provider);
        String provider_Id = "1234";
        user.setProvider_id(provider_Id);
        user.setUserFullName(insertDto.getUsername()+provider_Id+provider);
        user.setRole(Role.ROLE_ADMIN);
        return user;
    }

    @Builder
    public User(String username,  String password, String email, Role role,String provider, String provider_id, String userFullName,Timestamp createDate){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.userFullName=userFullName;
        this.provider = provider;
        this.provider_id = provider_id;
        this.createDate = createDate;
    }

}
