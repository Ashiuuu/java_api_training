package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShipTest {
    @Test
    public void shootTest() {
        Ship testShip = new Ship(0, 0, 4, Ship.Orientation.HORIZONTAL);
        Assertions.assertEquals(testShip.shootAtShip(new BoardPosition(0,0)), true);
        Assertions.assertEquals(testShip.shootAtShip(new BoardPosition(0,0)), false);
    }

    @Test
    public void sunkTest() {
        Ship testShip = new Ship(0,0,1,Ship.Orientation.HORIZONTAL);
        Assertions.assertEquals(testShip.isShipSunk(), false);
        testShip.shootAtShip(new BoardPosition(0,0));
        Assertions.assertEquals(testShip.isShipSunk(), true);
    }
}
