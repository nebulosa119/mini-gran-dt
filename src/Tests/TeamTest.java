package Tests;

import Models.Player;
import Models.Team;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;

public class TeamTest {

    @Test
    public void createTeamTest() {
        Team team1 = new Team("teamName1",5);
        Team team2 = new Team("teamName2",5);
        Team team3 = new Team("teamName1",5);

        assertEquals(false,team1.equals(team2));
        assertEquals(true,team1.equals(team3));
    }

    @Test
    public void CompleteTeamExceptionTest() {
        Team team = new Team("teamName",5);

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
        } catch (Team.CompleteTeamException ex) {
            thrown = true;
        }

        assertEquals(false,thrown);

        try {
            team.add(p6);
        } catch (Team.CompleteTeamException ex) {
            thrown = true;
        }

        assertEquals(true,thrown);
    }

    @Test
    public void serializationTest() {
        Team team = new Team("teamName",5);
        Player player = new Player("playerName");
        try {
            team.add(player);
        } catch (Team.CompleteTeamException ex) {
            ex.getMessage();
        }

        Serializable copy = SerializationUtils.clone((Serializable) team);

        assertEquals(team, copy);
        assertEquals(true,((Team)copy).getPlayers().contains(player));
    }
}