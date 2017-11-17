package back.tests;

import back.model.*;
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
        User user1 = new Administrator("adminName1");
        User user2 = new Administrator("adminName2");
        User user1Bis = new Administrator("adminName1");

        assertEquals("adminName1", user1.getName());
        assertFalse(user1.equals(user2));
        assertTrue(user1.equals(user1Bis));
    }

    @Test
    public void createTournamentTest() {
        PhysicalTournament tour = new PhysicalTournament("tourName",5);
        Administrator admin = new Administrator("adminName");
        admin.addTournament(tour);

        assertTrue(admin.containsTournament(new PhysicalTournament("tourName",5)));
        assertEquals(tour,admin.getTournament("tourName"));
    }

    @Test
    public void addUserTest() {
        PhysicalTournament tour = new PhysicalTournament("tourName",5);
        DT DT1 = new DT("userName1");
        Administrator admin = new Administrator("adminName");
        admin.addTournament(tour);
        admin.addDT("tourName", DT1);

        List<DT> set = admin.getOrderedDTs(new PhysicalTournament("tourName",5));

        DT DT2 = new DT("userName2");

        assertFalse(set.isEmpty());
        assertTrue(set.contains(DT1));
        assertFalse(set.contains(DT2));
    }

    @Test
    public void serializationTest() {
        Serializable original = new Administrator("adminName");
        Serializable copy = SerializationUtils.clone(original);
        assertEquals(original, copy);
    }
}