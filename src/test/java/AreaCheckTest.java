import models.Point;
import org.junit.Test;
import utils.AreaValidator;

import static org.junit.Assert.assertTrue;

public class AreaCheckTest {
    @Test
    public void checkAreaOnRectangle(){
        assertTrue(AreaValidator.checkArea(new Point(-1.0, -2.0, 3.0)));
        assertTrue(AreaValidator.checkArea(new Point(-2.0, -4.0, 5.0)));
    }

    @Test
    public void checkAreaOnTriangle(){
        assertTrue(AreaValidator.checkArea(new Point(1.0, 1.0, 2.0)));
        assertTrue(AreaValidator.checkArea(new Point(1.0, 4.0, 5.0)));
    }

    @Test
    public void checkAreaOnCircle(){
        assertTrue(AreaValidator.checkArea(new Point(-1.0, 1.0, 2.0)));
        assertTrue(AreaValidator.checkArea(new Point(-3.0, 0.5, 4.0)));
    }

    @Test
    public void checkAreaOnEmpty(){

        assertTrue(AreaValidator.checkArea(new Point(0.0, 0.0, 2.0)));
    }
}
