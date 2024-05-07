import models.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.AreaValidator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AreaCheckTest {
    @Test
    void checkAreaOnRectangle(){
        assertTrue(AreaValidator.checkArea(new Point(-1.0, -2.0, 3.0)));
        assertTrue(AreaValidator.checkArea(new Point(-2.0, -4.0, 5.0)));
    }

    @Test
    void checkAreaOnTriangle(){
        assertTrue(AreaValidator.checkArea(new Point(1.0, 1.0, 2.0)));
        assertTrue(AreaValidator.checkArea(new Point(1.0, 4.0, 5.0)));
    }

    @Test
    void checkAreaOnCircle(){
        assertTrue(AreaValidator.checkArea(new Point(-1.0, 1.0, 2.0)));
        assertTrue(AreaValidator.checkArea(new Point(-3.0, 0.5, 4.0)));
    }

    @Test
    void checkAreaOnEmpty(){
        assertTrue(AreaValidator.checkArea(new Point(0.0, 0.0, 2.0)));
    }
}
