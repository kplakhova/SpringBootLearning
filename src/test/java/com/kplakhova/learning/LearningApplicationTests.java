package com.kplakhova.learning;

import com.kplakhova.learning.clientproxy.UserResourceV1;
import com.kplakhova.learning.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LearningApplicationTests {

    @Autowired
    private UserResourceV1 userResourceV1;

	@Test
	public void itShouldFetchAllUsers() {
        List<User> users = userResourceV1.fetchUsers(null);
        assertThat(users).hasSize(1);

        User joe = new User(null, "Joe", "Jones",
                User.Gender.MALE, 22, "joe.jones@gmail.com");

        assertThat(users.get(0)).isEqualToIgnoringGivenFields(joe, "userUid");
        assertThat(users.get(0).getUserUid()).isInstanceOf(UUID.class);
        assertThat(users.get(0).getUserUid()).isNotNull();
    }

}
