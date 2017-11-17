package tests;

import model.PhysicalTeam;
import model.PhysicalTournament;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import java.io.Serializable;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PhysicalTournamentTest {

    @Test
    public void createTeamTest() {
        PhysicalTournament tour1 = new PhysicalTournament("tourName1",5);
        PhysicalTournament tour2 = new PhysicalTournament("tourName2",5);

        assertFalse(tour1.equals(tour2));
        assertEquals(5,tour1.getMaxPlayers());
        assertEquals(5,tour2.getMaxPlayers());
    }

    @Test
    public void addTeamTest() {
        PhysicalTournament tour = new PhysicalTournament("tourName",5);
        PhysicalTeam team1 = new PhysicalTeam("teamName1",tour.getMaxPlayers());
        tour.addTeam(team1);

        PhysicalTeam team2 = new PhysicalTeam("teamName2",tour.getMaxPlayers());

        assertTrue(tour.hasTeam(team1));
        assertFalse(tour.hasTeam(team2));
    }

    @Test
    public void serializationTest() {
        Serializable original = new PhysicalTournament("tourName",5);
        Serializable copy = SerializationUtils.clone(original);

        assertEquals(original, copy);
        assertEquals(5,((PhysicalTournament)copy).getMaxPlayers());
    }
}
