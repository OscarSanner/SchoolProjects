import org.junit.After;
import org.junit.Before;
import Labb.*;
import org.junit.Test;
import java.awt.*;

public class TestScania {
    Scania scania;

    @Test
    public void testFlatbedRestrictsMoveFunction(){
        scania = new Scania();
        scania.lowerFlatbed();

        scania.gas(1);
        scania.move();
        double firstX = scania.getX();
        double firstY = scania.getY();
        scania.raiseFlatbed();
        scania.gas(1);
        scania.move();
        assert scania.getX() != 0 && scania.getY() != 0 && firstX == 0 && firstY == 0;
    }

    @Test
    public void testFlatbedBoundaries(){
        scania = new Scania();
        int firstAngleUp = scania.getCurrentAngle();
        scania.raiseFlatbed();
        int secondAngleUp = scania.getCurrentAngle();

        while (scania.getCurrentAngle() != 0){
            scania.lowerFlatbed();
        }
        scania.lowerFlatbed();
        int angleDown = scania.getCurrentAngle();

        assert firstAngleUp == secondAngleUp && angleDown == 0;
    }

}
