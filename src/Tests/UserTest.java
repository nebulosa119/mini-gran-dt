package Tests;

import Models.Player;
import Models.Team;
import Models.Tournament;
import Models.User;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void createUserTest() {
        User user1 = new User("userName1");
        User user2 = new User("userName2");

        assertEquals(false,user1.equals(user2));
        assertEquals(true, user1.equals(user1));
        assertEquals(0,user1.getPoints());
    }

    @Test
    public void serializationTest() {
        Serializable original = new User("userName");
        Serializable copy = SerializationUtils.clone(original);
        assertEquals(original, copy);
    }
}