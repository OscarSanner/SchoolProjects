import org.junit.Before;
import org.junit.Test;
import Labb.*;
import java.awt.*;

public class TestCar {
    private Volvo240 volvo;
    private Saab95 saab;

    @Before
    public void createCar(){
        this.saab = new Saab95();
        this.volvo = new Volvo240();
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
                maxSpeed <= 100.00);
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
        double maxSpeed = volvo.getCurrentSpeed();
        assert (startspeed == 0 && engineStart > startspeed &&
                speedUnchanged == engineStart && speedUnchangedTwice == speedUnchanged &&
                maxSpeed <= 125.00);
    }

    @Test
    public void testBreakVolvo(){
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
                unchanged1 == unchanged2 && stop < 0.1 && stop >= 0);
    }

    @Test
    public void testBreakSaab(){
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

}
