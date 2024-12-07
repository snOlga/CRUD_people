package back.server;

import org.springframework.boot.test.context.SpringBootTest;

import back.server.model.HEXColor;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class ColorTest {

    @Test
    void failTest() {
        HEXColor color = new HEXColor();
        try {
            color.setHexColor("black");
            fail();
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
            fail();
        }
    }
}
