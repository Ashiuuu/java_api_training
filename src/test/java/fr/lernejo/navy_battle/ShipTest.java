package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShipTest {
    @Test
    public void shootTest() {
        Ship testShip = new Ship(0, 0, 4, Ship.Orientation.HORIZONTAL);
        Assertions.assertTrue(testShip.shootAtShip(new BoardPosition(0, 0)));
        Assertions.assertFalse(testShip.shootAtShip(new BoardPosition(0, 0)));
    }

    @Test
    public void sunkTest() {
        Ship testShip = new Ship(0,0,1,Ship.Orientation.HORIZONTAL);
        Assertions.assertFalse(testShip.isShipSunk());
        testShip.shootAtShip(new BoardPosition(0,0));
        Assertions.assertTrue(testShip.isShipSunk());
    }
}
