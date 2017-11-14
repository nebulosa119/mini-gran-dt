package Tests;

import Models.Exceptions.CompleteTeamException;
import Models.PhysicalTeam;
import Models.Player;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PhysicalTeamTest {

    @Test
    public void createTeamTest() {
        PhysicalTeam team1 = new PhysicalTeam("teamName1",5);
        PhysicalTeam team2 = new PhysicalTeam("teamName2",5);
        PhysicalTeam team3 = new PhysicalTeam("teamName1",5);

        assertFalse(team1.equals(team2));
        assertTrue(team1.equals(team3));
    }

    @Test
    public void CompleteTeamExceptionTest() {
        PhysicalTeam team = new PhysicalTeam("teamName",5);

        Player p1 = new Player("playerName1");
        Player p2 = new Player("playerName2");
        Player p3 = new Player("playerName3");
        Player p4 = new Player("playerName4");
        Player p5 = new Player("playerName5");
        Player p6 = new Player("playerName6");

        boolean thrown = false;

        try {
            team.add(p1);
            team.add(p2);
            team.add(p3);
            team.add(p4);
            team.add(p5);
        } catch (CompleteTeamException ex) {
            thrown = true;
        }

        assertEquals(false,thrown);

        try {
            team.add(p6);
        } catch (CompleteTeamException ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void serializationTest() {
        PhysicalTeam team = new PhysicalTeam("teamName",5);
        Player player = new Player("playerName");
        try {
            team.add(player);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }

        Serializable copy = SerializationUtils.clone((Serializable) team);

        assertEquals(team, copy);
        assertTrue(((PhysicalTeam)copy).getPlayers().contains(player));
    }
}