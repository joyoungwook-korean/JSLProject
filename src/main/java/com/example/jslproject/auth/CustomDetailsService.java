package com.example.jslproject.auth;

import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.vo.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        System.out.println("---");
        User user = userRepository.findByUsernameAndProvider(username,"local");

        if(user!=null){
            return new CustomDetails(user);
        }
        return null;
    }
}
