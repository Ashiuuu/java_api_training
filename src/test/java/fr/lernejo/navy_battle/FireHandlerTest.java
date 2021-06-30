package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FireHandlerTest {
    @Test
    public void extractTest() {
        Assertions.assertEquals(FireHandler.extract_cell("cell=coucou"), "coucou");
    }
}
