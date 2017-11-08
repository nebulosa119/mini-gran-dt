package Tests;

import Models.*;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class AdministratorTest {

    @Test
    public void createAccountTest() {
        Account account1 = new Administrator("adminName1");
        Account account2 = new Administrator("adminName2");
        Account account1bis= new Administrator("adminName1");

        assertEquals("adminName1",account1.getName());
        assertEquals(false,account1.equals(account2));
        assertEquals(true,account1.equals(account1bis));
    }

    @Test
    public void createTournamentTest() {
        Tournament tour = new Tournament("tourName");
        Administrator admin = new Administrator("adminName");
        admin.addTournament(tour);

        assertEquals(true,admin.hasTournament("tourName"));
        assertEquals(tour,admin.getTournament("tourName"));
    }

    @Test
    public void addUserTest() {
        Tournament tour = new Tournament("tourName");
        User user1 = new User("userName1");
        Administrator admin = new Administrator("adminName");
        admin.addTournament(tour);
        admin.addUser("tourName",user1);

        List<User> set = admin.getUsers("tourName");

        User user2 = new User("userName2");

        assertEquals(false,set.isEmpty());
        assertEquals(true,set.contains(user1));
        assertEquals(false,set.contains(user2));
    }

    @Test
    public void serializationTest() {
        Serializable original = new Administrator("adminName");
        Serializable copy = SerializationUtils.clone(original);
        assertEquals(original, copy);
    }
}