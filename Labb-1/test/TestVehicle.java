
import org.junit.After;
import org.junit.Before;
import Labb.*;
import org.junit.Test;
import java.awt.*;

public class TestVehicle {
    private Volvo240 volvo;
    private Saab95 saab;

    @Before
    public void createCar(){
        this.saab = new Saab95();
        this.volvo = new Volvo240();
    }

    @Test
    public void testTurboSaab(){
        Saab95 saabTurbo = new Saab95();
        Saab95 saabNatural  = new Saab95();
        saabTurbo.startEngine();
        saabNatural.startEngine();
        double safeCheck = saabTurbo.getCurrentSpeed();
        double safeCheck1 = saabNatural.getCurrentSpeed();
        saabTurbo.setTurboOn();
        saabTurbo.gas(1);
        saabNatural.gas(1);
        double speedcheckT = saabTurbo.getCurrentSpeed();
        double speedcheckN = saabNatural.getCurrentSpeed();
        saabTurbo.setTurboOff();
        saabNatural.setTurboOn();
        saabTurbo.gas(1);
        saabNatural.gas(1);
        double speedCheckNEqual = saabNatural.getCurrentSpeed();
        double speedCheckTEqual = saabTurbo.getCurrentSpeed();
        assert safeCheck == safeCheck1 && speedcheckT > speedcheckN && speedCheckNEqual == speedCheckTEqual;

    }

    @Test
    public void testGasVolvo(){
        double startspeed = volvo.getCurrentSpeed();
        volvo.startEngine();
        double engineStart = volvo.getCurrentSpeed();
        volvo.gas(-1);
        double speedUnchanged = volvo.getCurrentSpeed();
        volvo.gas(10);
        double speedUnchangedTwice = volvo.getCurrentSpeed();
        for (int i = 0; i < 100000; i++){
            volvo.gas(0.9);
        }
        double maxSpeed = volvo.getCurrentSpeed();
        assert (startspeed == 0 && engineStart > startspeed &&
                speedUnchanged == engineStart && speedUnchangedTwice == speedUnchanged &&
                maxSpeed == 100.00);
    }

    @Test
    public void testGasSaab(){
        double startspeed = saab.getCurrentSpeed();
        saab.startEngine();
        double engineStart = saab.getCurrentSpeed();
        saab.gas(-1);
        double speedUnchanged = saab.getCurrentSpeed();
        saab.gas(10);
        double speedUnchangedTwice = saab.getCurrentSpeed();
        for (int i = 0; i < 10000; i++){
            saab.gas(1);
        }
        double maxSpeed = saab.getCurrentSpeed();
        assert (startspeed == 0 && engineStart > startspeed &&
                speedUnchanged == engineStart && speedUnchangedTwice == speedUnchanged &&
                maxSpeed == 125.00);
    }

    @Test
    public void testBreakVolvo(){
        for (int i = 0; i < 10000; i++){
            volvo.gas(1);
        }

        double startspeed = volvo.getCurrentSpeed();
        volvo.brake(1);
        double brakeOnce = volvo.getCurrentSpeed();
        volvo.brake(10);
        double unchanged1 = volvo.getCurrentSpeed();
        volvo.brake(-1);
        double unchanged2 = volvo.getCurrentSpeed();
        for (int i = 0; i < 10000; i++){
            volvo.brake(1);
        }
        double stop = volvo.getCurrentSpeed();
        assert (startspeed > brakeOnce && brakeOnce == unchanged1 &&
                unchanged1 == unchanged2 && stop == 0);
    }

    @Test
    public void testBreakSaab(){
        for (int i = 0; i < 10000; i++){
            saab.gas(1);
        }

        double startspeed = saab.getCurrentSpeed();
        saab.brake(1);
        double brakeOnce = saab.getCurrentSpeed();
        saab.brake(10);
        double unchanged1 = saab.getCurrentSpeed();
        saab.brake(-1);
        double unchanged2 = saab.getCurrentSpeed();
        for (int i = 0; i < 10000; i++){
            saab.brake(1);
        }
        double stop = saab.getCurrentSpeed();
        assert (startspeed > brakeOnce && brakeOnce == unchanged1 &&
                unchanged1 == unchanged2 && stop < 0.1 && stop >= 0);
    }

    @Test
    public void testEnginePowerSaab(){
        assert saab.getEnginePower() == 125;
    }

    @Test
    public void testEnginePowerVolvo(){
        assert volvo.getEnginePower() == 100;
    }

    @Test
    public void testColorSaab(){
        Color firstColor = saab.getColor();
        saab.setColor(Color.GREEN);
        Color secondColor = saab.getColor();
        assert firstColor == Color.RED && secondColor == Color.GREEN;
    }

    @Test
    public void testColorVolvo(){
        Color firstColor = volvo.getColor();
        volvo.setColor(Color.GREEN);
        Color secondColor = volvo.getColor();
        assert firstColor == Color.BLACK && secondColor == Color.GREEN;
    }

    @Test
    public void testDoorsVolvo(){
        assert volvo.getNrDoors() == 4;
    }

    @Test
    public void testDoorsSaab(){
        assert saab.getNrDoors() == 2;
    }

    private Volvo240 v1;
    private Saab95 s1;

    @Test
    public void testTurnLeftVolvo(){
        v1 = new Volvo240();
        v1.turnLeft();
        assert v1.getCurrentDirection() == Direction.LEFT;
    }

    @Test
    public void testRotateLeftVolvo(){
        v1 = new Volvo240();
        Direction d = v1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            v1.turnLeft();
        }
        assert v1.getCurrentDirection() == d;
    }

    @Test
    public void testTurnRightVolvo(){
        v1 = new Volvo240();
        v1.turnRight();
        assert v1.getCurrentDirection() == Direction.RIGHT;
    }

    @Test
    public void testRotateRightVolvo(){
        v1 = new Volvo240();
        Direction d = v1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            v1.turnRight();
        }
        assert v1.getCurrentDirection() == d;
    }

    @Test
    public void testMoveUp(){
        v1 = new Volvo240();
        double i = v1.getY();
        v1.startEngine();
        v1.move();
        assert v1.getY() > i;
    }

    @Test
    public void testMoveLeft(){
        v1 = new Volvo240();
        double i = v1.getX();
        v1.startEngine();
        v1.turnLeft();
        v1.move();
        assert v1.getX() < i;
    }

    @Test
    public void testMoveDown(){
        v1 = new Volvo240();
        double i = v1.getY();
        v1.startEngine();
        v1.turnLeft();
        v1.turnLeft();
        v1.move();
        assert v1.getY() < i;
    }

    @Test
    public void testMoveRight(){
        v1 = new Volvo240();
        double i = v1.getX();
        v1.startEngine();
        v1.turnRight();
        v1.move();
        assert v1.getX() > i;
    }

    @Test
    public void testTurnLeftSaab(){
        s1 = new Saab95();
        s1.turnLeft();
        assert s1.getCurrentDirection() == Direction.LEFT;
    }

    @Test
    public void testRotateLeftSaab(){
        s1 = new Saab95();
        Direction d = s1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            s1.turnLeft();
        }
        assert s1.getCurrentDirection() == d;
    }

    @Test
    public void testTurnRightSaab(){
        s1 = new Saab95();
        s1.turnRight();
        assert s1.getCurrentDirection() == Direction.RIGHT;
    }

    @Test
    public void testRotateRightSaab(){
        s1 = new Saab95();
        Direction d = s1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            s1.turnRight();
        }
        assert s1.getCurrentDirection() == d;
    }

    @Test
    public void testMoveUpSaab(){
        s1 = new Saab95();
        double yBefore = s1.getY();
        s1.startEngine();
        s1.move();
        assert s1.getY() > yBefore;
    }

    @Test
    public void testMoveLeftSaab(){
        s1 = new Saab95();
        double i = s1.getX();
        s1.startEngine();
        s1.turnLeft();
        s1.move();
        assert s1.getX() < i;
    }

    @Test
    public void testMoveDownSaab(){
        s1 = new Saab95();
        double i = s1.getY();
        s1.startEngine();
        s1.turnLeft();
        s1.turnLeft();
        s1.move();
        assert s1.getY() < i;
    }

    @Test
    public void testMoveRightSaab(){
        s1 = new Saab95();
        double i = s1.getX();
        s1.startEngine();
        s1.turnRight();
        s1.move();
        assert s1.getX() > i;
    }

    @After
    public void runMain(){
        Main.main(null);
    }
}
