package Tests;

import Models.*;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdministratorTest {

    @Test
    public void createAccountTest() {
        Account account1 = new Administrator("adminName1");
        Account account2 = new Administrator("adminName2");
        Account account1bis= new Administrator("adminName1");

        assertEquals("adminName1",account1.getName());
        assertFalse(account1.equals(account2));
        assertTrue(account1.equals(account1bis));
    }

    @Test
    public void createTournamentTest() {
        Tournament tour = new Tournament("tourName");
        Administrator admin = new Administrator("adminName");
        admin.addTournament(tour);

        assertTrue(admin.containsTournament(new Tournament("tourName")));
        assertEquals(tour,admin.getTournament("tourName"));
    }

    @Test
    public void addUserTest() {
        Tournament tour = new Tournament("tourName");
        UserDT userDT1 = new UserDT("userName1");
        Administrator admin = new Administrator("adminName");
        admin.addTournament(tour);
        admin.addUser("tourName", userDT1);

        List<UserDT> set = admin.getUsers(new Tournament("tourName"));

        UserDT userDT2 = new UserDT("userName2");

        assertFalse(set.isEmpty());
        assertTrue(set.contains(userDT1));
        assertFalse(set.contains(userDT2));
    }

    @Test
    public void serializationTest() {
        Serializable original = new Administrator("adminName");
        Serializable copy = SerializationUtils.clone(original);
        assertEquals(original, copy);
    }
}