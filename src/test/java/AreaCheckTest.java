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
    void one(){
        assertTrue(AreaValidator.checkArea(new Point(2.0, (double) 0, 2.0)));
        assertTrue(AreaValidator.checkArea(new Point(2.0, 1.0, 2.0)));
        assertTrue(AreaValidator.checkArea(new Point(1.0, 1.0, 2.0)));
        assertTrue(AreaValidator.checkArea(new Point((double) 0, 1.0, 2.0)));
    }
}
