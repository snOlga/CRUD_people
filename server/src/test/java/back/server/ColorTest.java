package back.server;

import org.springframework.boot.test.context.SpringBootTest;

import back.server.citizens.HEXColor;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class ColorTest {

    @Test
    void failTest() {
        HEXColor color = new HEXColor();
        try {
            color.setHexColor("black");
            failTest();
        } catch (Exception e) {
            //nothing
        }
    }

    @Test 
    void goodTest() {
        HEXColor color = new HEXColor();
        try {
            color.setHexColor("#ffffff");
        } catch (Exception e) {
            failTest();
        }
    }
}
