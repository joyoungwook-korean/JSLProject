package com.example.jslproject.vo;

import com.example.jslproject.constant.Role;
import com.example.jslproject.repository.BoardRepository;
import com.example.jslproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class BoardVOTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    private User createUser(){
        User user = new User();
        user.setUsername("1234");
        user.setRole(Role.ROLE_USER);
        user.setProvider("local");
        user.setUserFullName("1234asdzxc");
        user.setEmail("asdfw@na");
        return user;
    }

    @Test
    public void createService(){
        User user = createUser();
        userRepository.save(user);

        BoardVO boardVO = new BoardVO();
        boardVO.setUser(user);
        boardRepository.save(boardVO);

        em.flush();
        em.clear();

        BoardVO testBoardVO1 = boardRepository.getById(boardVO.getId());
        assertEquals(testBoardVO1.getUser().getProvider(),user.getProvider());
    }

}