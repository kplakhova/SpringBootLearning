package com.kplakhova.learning.it;

import com.kplakhova.learning.clientproxy.UserResourceV1;
import com.kplakhova.learning.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserIT {

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

    @Test
    public void shouldUpdateUser() {
        //Given
        UUID userUid = UUID.randomUUID();
        User user = new User(userUid, "Joe", "Jones",
                User.Gender.MALE, 22, "joe.jones@gmail.com");

        //When
        userResourceV1.insertNewUser(user);

        User updatedUser = new User(userUid, "Alex", "Jones",
                User.Gender.MALE, 55, "alex.jones@gmail.com");

        userResourceV1.updateUser(updatedUser);

        //Then
        user = userResourceV1.fetchUser(userUid);
        assertThat(user).isEqualToComparingFieldByField(updatedUser);

    }

    @Test
    public void shouldFetchUsersByGender() {
        //Given
        UUID userUid = UUID.randomUUID();
        User user = new User(userUid, "Joe", "Jones",
                User.Gender.MALE, 22, "joe.jones@gmail.com");

        //When
        userResourceV1.insertNewUser(user);

        List<User> females = userResourceV1.fetchUsers(User.Gender.FEMALE.name());

        assertThat(females).extracting("userUid").doesNotContain(user.getUserUid());
        assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
        assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
        assertThat(females).extracting("gender").doesNotContain(user.getGender());
        assertThat(females).extracting("age").doesNotContain(user.getAge());
        assertThat(females).extracting("email").doesNotContain(user.getEmail());

        List<User> males = userResourceV1.fetchUsers(User.Gender.MALE.name());

        assertThat(males).extracting("userUid").contains(user.getUserUid());
        assertThat(males).extracting("firstName").contains(user.getFirstName());
        assertThat(males).extracting("lastName").contains(user.getLastName());
        assertThat(males).extracting("gender").contains(user.getGender());
        assertThat(males).extracting("age").contains(user.getAge());
        assertThat(males).extracting("email").contains(user.getEmail());
    }
}
