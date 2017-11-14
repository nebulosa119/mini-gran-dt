package Tests;

import Models.Exceptions.CompleteTeamException;
import Models.Exceptions.InsufficientFundsException;
import Models.PhysicalTeam;
import Models.Player;
import Models.Tournament;
import Models.UserDT;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDTTest {

    @Test
    public void createUserTest() {
        UserDT userDT1 = new UserDT("userName1");
        UserDT userDT2 = new UserDT("userName2");

        assertFalse(userDT1.equals(userDT2));
        assertEquals(0, userDT1.getPoints(new Tournament("tourName")));
    }

    @Test
    public void expensesTest() {
        UserDT userDT = new UserDT("userName");
        Tournament tour = new Tournament("tourName");
        PhysicalTeam team = new PhysicalTeam("teamName",5);
        Player player = new Player("playerName");

        try {
            team.add(player);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }
        tour.addTeam(team);
        userDT.signUp(tour);

        boolean thrown = false;
        try {
            userDT.buy(tour, player);
        } catch (InsufficientFundsException ex) {
            thrown = true;
        }

        assertFalse(thrown);
        // UserDT has a initial amount of funds of 10000
        assertEquals(10000-player.getPrice(), userDT.getExpenses().getAvailableFunds(tour));
        assertTrue(userDT.getUserTeams().getUserTeamPlayers(tour).contains(player));

        userDT.sell(tour,player);

        assertEquals(10000,userDT.getExpenses().getAvailableFunds(tour));
    }

    @Test
    public void refreshPointsTest() {
        UserDT userDT = new UserDT("userName");
        Tournament tour = new Tournament("tourName");
        PhysicalTeam team = new PhysicalTeam("teamName",5);
        Player player = new Player("playerName");

        try {
            team.add(player);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }
        tour.addTeam(team);
        userDT.signUp(tour);
        userDT.buy(tour, player);

        assertEquals(0,userDT.getPoints(tour));

        Player.Properties props = new Player.Properties(1,2,3,4,3,2,1);
        player.refresh(props);

        Map<String, Player.Properties> map = new HashMap<>();
        map.put("playerName",player.getProperties());

        userDT.refreshPoints(tour,map);

        assertEquals(4,userDT.getPoints(tour));
    }

    @Test
    public void serializationTest() {
        UserDT userDT = new UserDT("userName");
        Tournament tour = new Tournament("tourName");
        PhysicalTeam team = new PhysicalTeam("teamName",5);
        Player player = new Player("playerName");

        try {
            team.add(player);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }
        tour.addTeam(team);
        userDT.signUp(tour);
        userDT.buy(tour, player);

        Serializable copy = SerializationUtils.clone((Serializable) userDT);
        assertEquals(userDT, copy);
        assertTrue(((UserDT)copy).getUserTeams().getUserTeamPlayers(tour).contains(player));
    }
}