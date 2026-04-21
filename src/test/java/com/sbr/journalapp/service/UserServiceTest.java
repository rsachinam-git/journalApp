package com.sbr.journalapp.service;

import com.sbr.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Disabled
    public void testFindByUsername(){
//        assertEquals(4, 2+2);
        assertNotNull(userRepository.findByUsername("sanjay"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "sanjay","ram"
    })
    public void testFindByUsernameParametrized(String username){
        assertNotNull(userRepository.findByUsername(username));
    }
}
