package com.example.TaskManager.Repository;

import com.example.TaskManager.Model.User;
import com.example.TaskManager.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTests {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryIntegrationTests(UserRepository ur){
        this.userRepository = ur;
    }

    /**
     * Tests to make sure save and recall via id operations work when
     * saving and recalling entities from a database one at a time.
     */
    @Test
    public void UserRepository_TestSaveAndReturnUser_ReturnSavedUsers(){
        User user = TestDataUtil.createTestUserOne();
        User user2 = TestDataUtil.createTestUserTwo();
        userRepository.save(user);
        userRepository.save(user2);
        Optional<User> result = userRepository.findById(user.getId());
        Optional<User> result2 = userRepository.findById(user2.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
        assertThat(result2).isPresent();
        assertThat(result2.get()).isEqualTo(user2);


    }




}
