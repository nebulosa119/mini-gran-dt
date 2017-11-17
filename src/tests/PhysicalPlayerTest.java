package tests;

import model.PhysicalPlayer;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import java.io.Serializable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhysicalPlayerTest {

    @Test
    public void createPlayerTest() {
        PhysicalPlayer physicalPlayer1 = new PhysicalPlayer("playerName1",2000);
        PhysicalPlayer physicalPlayer2 = new PhysicalPlayer("playerName2");
        PhysicalPlayer physicalPlayer3 = new PhysicalPlayer("playerName1");

        assertEquals("playerName1", physicalPlayer1.getName());
        assertEquals(2000, physicalPlayer1.getPrice());
        assertFalse(physicalPlayer1.equals(physicalPlayer2));
        assertTrue(physicalPlayer1.equals(physicalPlayer3));
    }

    @Test
    public void serializationTest() {
        PhysicalPlayer.Properties props = new PhysicalPlayer.Properties(1,2,3,4,5,6,7);
        PhysicalPlayer physicalPlayer = new PhysicalPlayer("playerName",2000,props);

        Serializable copy = SerializationUtils.clone((Serializable) physicalPlayer);

        assertEquals(physicalPlayer, copy);
        assertEquals(2000,((PhysicalPlayer)copy).getPrice());
        assertEquals(props,((PhysicalPlayer)copy).getProperties());
    }

}
