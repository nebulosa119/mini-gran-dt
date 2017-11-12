package Tests;

import Models.UserDT;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserDTTest {

    @Test
    public void createUserTest() {
        UserDT userDT1 = new UserDT("userName1");
        UserDT userDT2 = new UserDT("userName2");

        assertFalse(userDT1.equals(userDT2));
        assertEquals(0, userDT1.getPoints());
    }


/*
    @Test
    public void serializationTest() {
        UserDT user = new UserDT("userName");
        Team team = new Team("teamName",5);
        user.addTeam("tourName",team);

        Serializable copy = SerializationUtils.clone((Serializable) user);
        assertEquals(user, copy);
        assertEquals(true,((UserDT)copy).getTeam("tourName").equals(team));
    }*/
}