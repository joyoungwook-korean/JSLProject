package com.example.jslproject.config;

import com.example.jslproject.oauth2.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().
                antMatchers("/user/**").authenticated().
                antMatchers("/admin/**").access("hasRole('ADMIN')").
                anyRequest().permitAll().
                and()
                .formLogin().loginPage("/index/loginform").
                loginProcessingUrl("/login")
                .defaultSuccessUrl("/").
                usernameParameter("username")
                .passwordParameter("password")
        .and()
        .oauth2Login()
        .loginPage("/loginpage")
        .userInfoEndpoint()
        .userService(customOAuth2UserService);

    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();
    }


}
