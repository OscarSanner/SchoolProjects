/*import Labb.Model.Scania;
import org.junit.Test;

public class TestScania {
    Scania scania;

    @Test
    public void testFlatbedRestrictsMoveFunction(){
        scania = new Scania(0,0);
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
        scania = new Scania(0,0);
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


 */