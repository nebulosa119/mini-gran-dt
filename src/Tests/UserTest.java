package Tests;

import Models.Player;
import Models.Team;
import Models.Tournament;
import Models.User;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import java.io.Serializable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserTest {

    @Test
    public void createUserTest() {
        User user1 = new User("userName1");
        User user2 = new User("userName2");

        assertFalse(user1.equals(user2));
        assertEquals(0,user1.getPoints());
    }


/*
    @Test
    public void serializationTest() {
        User user = new User("userName");
        Team team = new Team("teamName",5);
        user.addTeam("tourName",team);

        Serializable copy = SerializationUtils.clone((Serializable) user);
        assertEquals(user, copy);
        assertEquals(true,((User)copy).getTeam("tourName").equals(team));
    }*/
}