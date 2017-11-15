package Tests;

import Models.DT;
import Models.Exceptions.CompleteTeamException;
import Models.Exceptions.InsufficientFundsException;
import Models.PhysicalTeam;
import Models.PhysicalTournament;
import Models.PhysicalPlayer;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DTTest {

    @Test
    public void createUserTest() {
        DT DTAccount1 = new DT("userName1");
        DT DTAccount2 = new DT("userName2");

        assertFalse(DTAccount1.equals(DTAccount2));
        assertEquals(0, DTAccount1.getPoints(new PhysicalTournament("tourName")));
    }

    @Test
    public void expensesTest() {
        DT DTAccount = new DT("userName");
        PhysicalTournament tour = new PhysicalTournament("tourName");
        PhysicalTeam team = new PhysicalTeam("teamName",5);
        PhysicalPlayer physicalPlayer = new PhysicalPlayer("playerName");

        try {
            team.add(physicalPlayer);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }
        tour.addTeam(team);
        DTAccount.signUp(tour);

        boolean thrown = false;
        try {
            DTAccount.buy(tour, physicalPlayer);
        } catch (InsufficientFundsException ex) {
            thrown = true;
        }

        assertFalse(thrown);
        // DT has a initial amount of funds of 10000
        assertEquals(10000- physicalPlayer.getPrice(), DTAccount.getExpenses().getAvailableFunds(tour));
        assertTrue(DTAccount.getDTTeamsManager().getUserTeamPlayers(tour).contains(physicalPlayer));

        DTAccount.sell(tour, physicalPlayer);

        assertEquals(10000, DTAccount.getExpenses().getAvailableFunds(tour));
    }

    @Test
    public void refreshPointsTest() {
        DT DTAccount = new DT("userName");
        PhysicalTournament tour = new PhysicalTournament("tourName");
        PhysicalTeam team = new PhysicalTeam("teamName",5);
        PhysicalPlayer physicalPlayer = new PhysicalPlayer("playerName");

        try {
            team.add(physicalPlayer);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }
        tour.addTeam(team);
        DTAccount.signUp(tour);
        DTAccount.buy(tour, physicalPlayer);

        assertEquals(0, DTAccount.getPoints(tour));

        PhysicalPlayer.Properties props = new PhysicalPlayer.Properties(1,2,3,4,3,2,1);
        physicalPlayer.refresh(props);

        Map<String, PhysicalPlayer.Properties> map = new HashMap<>();
        map.put("playerName", physicalPlayer.getProperties());

        DTAccount.refreshPoints(tour,map);

        assertEquals(4, DTAccount.getPoints(tour));
    }

    @Test
    public void serializationTest() {
        DT DTAccount = new DT("userName");
        PhysicalTournament tour = new PhysicalTournament("tourName");
        PhysicalTeam team = new PhysicalTeam("teamName",5);
        PhysicalPlayer physicalPlayer = new PhysicalPlayer("playerName");

        try {
            team.add(physicalPlayer);
        } catch (CompleteTeamException ex) {
            ex.getMessage();
        }
        tour.addTeam(team);
        DTAccount.signUp(tour);
        DTAccount.buy(tour, physicalPlayer);

        Serializable copy = SerializationUtils.clone((Serializable) DTAccount);
        assertEquals(DTAccount, copy);
        assertTrue(((DT)copy).getDTTeamsManager().getUserTeamPlayers(tour).contains(physicalPlayer));
    }
}