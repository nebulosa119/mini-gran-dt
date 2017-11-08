/**package Tests;

import Models.Player;
import Models.Team;
import Models.User;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void createUserTest() {
        User user1 = new User("userName1");
        User user2 = new User("userName2");

        assertEquals(false,user1.equals(user2));
        assertEquals(true, user1.equals(user1));
        assertEquals(0,user1.getPoints());
    }

    @Test
    public void addTeamTest() {
        String tourName = "tourName";
        Team team = new Team("teamName",5);

        User user = new User("userName");
        user.addTeam(tourName,team);

        assertEquals(team,user.getTeam(tourName));
    }

    @Test
    public void canBuyTest() {
        // User tiene private final static int INITIAL_AMOUNT = 1000
        Player player1 = new Player("playerName1",80);
        Player player2 = new Player("playerName2",1010);
        User user = new User("userName");

        assertEquals(true,user.canBuy(player1));
        assertEquals(false,user.canBuy(player2));
    }

    @Test
    public void buyTest() {
        Player player1 = new Player("playerName1",1000);
        Player player2 = new Player("playerName2",0);
        Player player3 = new Player("playerName3",1);
        User user = new User("userName");

        user.addTeam("tourName",new Team("teamName",5));
        user.buy("tourName",player1);

        assertEquals(true,user.canBuy(player2));
        assertEquals(false,user.canBuy(player3));
    }

    @Test
    public void serializationTest() {
        Serializable original = new User("userName");
        Serializable copy = SerializationUtils.clone(original);
        assertEquals(original, copy);
    }
}
**/