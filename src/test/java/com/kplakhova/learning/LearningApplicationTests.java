package com.kplakhova.learning;

import com.kplakhova.learning.clientproxy.UserResourceV1;
import com.kplakhova.learning.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LearningApplicationTests {

    @Autowired
    private UserResourceV1 userResourceV1;

    @Test
    public void shouldInsertUser() {
        //Given
        UUID userUid = UUID.randomUUID();
        User user = new User(userUid, "Joe", "Jones",
                User.Gender.MALE, 22, "joe.jones@gmail.com");

        //When
        userResourceV1.insertNewUser(user);

        //Then
        User joe = userResourceV1.fetchUser(userUid);
        assertThat(joe).isEqualToComparingFieldByField(user);
    }

    @Test
    public void shouldDeleteUser() {
        //Given
        UUID userUid = UUID.randomUUID();
        User user = new User(userUid, "Joe", "Jones",
                User.Gender.MALE, 22, "joe.jones@gmail.com");

        //When
        userResourceV1.insertNewUser(user);

        //Then
        User joe = userResourceV1.fetchUser(userUid);
        assertThat(joe).isEqualToComparingFieldByField(user);

        //When
        userResourceV1.deleteUser(userUid);

        //Then
        assertThatThrownBy(() -> userResourceV1.fetchUser(userUid))
                .isInstanceOf(NotFoundException.class);
    }
}
