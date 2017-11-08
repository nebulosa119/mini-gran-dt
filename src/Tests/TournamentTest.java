package Tests;

import Models.Team;
import Models.Tournament;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;

public class TournamentTest {

    @Test
    public void createTeamTest() {
        Tournament tour1 = new Tournament("tourName1",5);
        Tournament tour2 = new Tournament("tourName2",5);

        assertEquals(false,tour1.equals(tour2));
        assertEquals(true,tour1.equals(tour1));
        assertEquals(5,tour1.getMaxPlayers());
        assertEquals(5,tour2.getMaxPlayers());
    }

    @Test
    public void addTeamTest() {
        Tournament tour = new Tournament("tourName",5);
        Team team1 = new Team("teamName1",tour.getMaxPlayers());
        tour.addTeam(team1);

        Team team2 = new Team("teamName2",tour.getMaxPlayers());

        assertEquals(true,tour.hasTeam(team1));
        assertEquals(false,tour.hasTeam(team2));
    }

    @Test
    public void serializationTest() {
        Serializable original = new Tournament("tourName",5);
        Serializable copy = SerializationUtils.clone(original);
        assertEquals(original, copy);
    }
}
