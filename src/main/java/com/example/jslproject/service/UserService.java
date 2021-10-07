package com.example.jslproject.service;

import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User saveUser(User user){
        check_User_Email_Provider(user);
        return userRepository.save(user);
    }



    private User check_User_Email_Provider(User user){

        if(!user.getProvider().equals("local") && !user.getProvider().equals("naver") && !user.getProvider().equals("kakao")){
            throw new IllegalStateException("Provider Check");
        }
        User findEmailProvider = userRepository.findByEmailAndProvider(user.getEmail(),
                "local");
        User findUsernameProvider = userRepository.findByUsernameAndProvider(user.getUsername(),"local");
        if(findEmailProvider != null || findUsernameProvider != null){
            throw new IllegalStateException("check user");
        }
        return  user;

    }
}
