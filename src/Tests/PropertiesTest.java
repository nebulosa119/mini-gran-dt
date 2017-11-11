package Tests;

import Models.Player;
import org.junit.Test;
import org.apache.commons.lang3.SerializationUtils;
import java.io.Serializable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PropertiesTest {

    @Test
    public void createPropertiesTest() {
        Player.Properties props1 = new Player.Properties(1,2,3,4,5,6,7);
        Player.Properties props2 = new Player.Properties(1,2,3,4,5,6,7);
        Player.Properties props3 = new Player.Properties(1,1,1,1,1,1,1);

        assertTrue(props1.equals(props2));
        assertFalse(props1.equals(props3));
        assertEquals(-8,props1.getPoints());
    }

    @Test
    public void calculationsTest() {
        Player.Properties props = new Player.Properties(1,2,3,4,5,6,7);
        int price = props.calculatePrice();
        int ranking = props.calculateRanking();

        assertEquals(255,price);
        assertEquals(115,ranking);
    }

    @Test
    public void serializationTest() {
        Player.Properties props = new Player.Properties(1,2,3,4,5,6,7);

        Serializable copy = SerializationUtils.clone((Serializable) props);
        assertEquals(props, copy);
    }

}
