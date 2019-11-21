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
        scania.raiseFlatbed();

        scania.gas(1);
        scania.move();
        double firstX = scania.getX();
        double firstY = scania.getY();
        scania.lowerFlatbed();
        scania.gas(1);
        scania.move();
        assert (scania.getX() != 0 || scania.getY() != 0) && firstX == 0 && firstY == 0;
    }

    @Test
    public void testFlatbedBoundaries(){
        scania = new Scania();
        int firstAngleDown = scania.getCurrentAngle();
        scania.lowerFlatbed();
        int secondAngleDown = scania.getCurrentAngle();

        while (scania.getCurrentAngle() != 70){
            scania.raiseFlatbed();
        }
        scania.raiseFlatbed();
        int angleUp = scania.getCurrentAngle();

        assert firstAngleDown == secondAngleDown && angleUp == 70;
    }

}