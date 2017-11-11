package Tests;

import Models.Player;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import java.io.Serializable;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void createPlayerTest() {
        Player player1 = new Player("playerName1",2000);
        Player player2 = new Player("playerName2");
        Player player3= new Player("playerName1");

        assertEquals("playerName1", player1.getName());
        assertEquals(2000, player1.getPrice());
        assertFalse(player1.equals(player2));
        assertTrue(player1.equals(player3));
    }

    @Test
    public void serializationTest() {
        Player.Properties props = new Player.Properties(1,2,3,4,5,6,7);
        Player player = new Player("playerName",2000,props);

        Serializable copy = SerializationUtils.clone((Serializable) player);

        assertEquals(player, copy);
        assertEquals(2000,((Player)copy).getPrice());
        assertEquals(props,((Player)copy).getProperties());
    }

}
