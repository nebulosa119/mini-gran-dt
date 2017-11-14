package Tests;

import Models.PhysicalTeam;
import Models.Tournament;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TournamentTest {

    @Test
    public void createTeamTest() {
        Tournament tour1 = new Tournament("tourName1",5);
        Tournament tour2 = new Tournament("tourName2",5);

        assertFalse(tour1.equals(tour2));
        assertEquals(5,tour1.getMaxPlayers());
        assertEquals(5,tour2.getMaxPlayers());
    }

    @Test
    public void addTeamTest() {
        Tournament tour = new Tournament("tourName",5);
        PhysicalTeam team1 = new PhysicalTeam("teamName1",tour.getMaxPlayers());
        tour.addTeam(team1);

        PhysicalTeam team2 = new PhysicalTeam("teamName2",tour.getMaxPlayers());

        assertTrue(tour.hasTeam(team1));
        assertFalse(tour.hasTeam(team2));
    }

    @Test
    public void serializationTest() {
        Serializable original = new Tournament("tourName",5);
        Serializable copy = SerializationUtils.clone(original);

        assertEquals(original, copy);
        assertEquals(5,((Tournament)copy).getMaxPlayers());
    }
}
