package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StartHandlerTest {
    @Test
    public void streamTest() {
        try {
            String testString = "This is a test!";
            InputStream stream = new ByteArrayInputStream(testString.getBytes());
            Assertions.assertEquals(testString, StartHandler.stream_to_string(stream));
        } catch (IOException e) {
            System.out.print("[*] " + e);
        }
    }
}
