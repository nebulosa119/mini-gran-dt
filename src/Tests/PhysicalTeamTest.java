package Tests;

import Models.Exceptions.CompleteTeamException;
import Models.PhysicalTeam;
import Models.PhysicalPlayer;
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

        PhysicalPlayer p1 = new PhysicalPlayer("playerName1");
        PhysicalPlayer p2 = new PhysicalPlayer("playerName2");
        PhysicalPlayer p3 = new PhysicalPlayer("playerName3");
        PhysicalPlayer p4 = new PhysicalPlayer("playerName4");
        PhysicalPlayer p5 = new PhysicalPlayer("playerName5");
        PhysicalPlayer p6 = new PhysicalPlayer("playerName6");

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
        PhysicalPlayer physicalPlayer = new PhysicalPlayer("playerName");
        try {
            team.add(physicalPlayer);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }

        Serializable copy = SerializationUtils.clone((Serializable) team);

        assertEquals(team, copy);
        assertTrue(((PhysicalTeam)copy).getPhysicalPlayers().contains(physicalPlayer));
    }
}