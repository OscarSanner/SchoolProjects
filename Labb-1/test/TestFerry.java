import Labb.CarFerry;
import Labb.CarTransporter;
import Labb.Saab95;
import Labb.Volvo240;
import org.junit.Test;

public class TestFerry {
    CarFerry ct;
    Volvo240 v1 = new Volvo240();
    Volvo240 v2 = new Volvo240();
    Saab95 s1 = new Saab95();


    @Test
    public void testMovementUnloaded() {
        ct = new CarFerry();
        ct.gas(1);
        double firstTestX = ct.getX(); // should be != 0;
        ct.stopEngine();
        ct.lowerFlatbed();
        ct.gas(1);
        ct.move();
        double secondTestX = ct.getX(); // should be equal till firstX
        assert secondTestX == firstTestX;
    }

    @Test
    public void testFlatbed() {
        ct = new CarFerry();
        int initFlatbedState = ct.getCurrentAngle();
        ct.raiseFlatbed();
        int testBoundUp = ct.getCurrentAngle(); // should be equal to initFlatbedState;
        ct.lowerFlatbed();
        int flatbedDown = ct.getCurrentAngle();
        ct.move();
        double noMoveX = ct.getX(); // should be zero
        double noMoveY = ct.getY(); // should be zero
        ct.lowerFlatbed();
        int testBoundDown = ct.getCurrentAngle(); // should be equal to flatbedDown
        ct.raiseFlatbed();
        ct.gas(1);
        ct.move();
        double movedX = ct.getX();  // should be != zero
        double movedY = ct.getY();  // should be != zero
        ct.lowerFlatbed();
        boolean cantLowerFlatBedWhileMoving = (ct.getCurrentAngle() == 1);

        assert (initFlatbedState == testBoundUp) && (noMoveX == 0) && (noMoveY == 0) && (testBoundDown == flatbedDown) && ((movedX + movedY) != 0) && cantLowerFlatBedWhileMoving;
    }

    @Test
    public void testMovementWithCars() {
        ct = new CarFerry();
        ct.lowerFlatbed();
        ct.loadCar(v2);
        ct.loadCar(s1);
        ct.loadCar(v1);

        ct.raiseFlatbed();
        ct.gas(1);
        ct.move();

        boolean moveWithCars = (ct.getX() != 0 || ct.getY() != 0);
        ct.unloadCar();
        boolean cantUnloadWhileMoving = ct.getLoadedCars().size() == 3;
        ct.stopEngine();
        boolean cantUnloadWhileFlatBedIsUp = ct.getLoadedCars().size() == 3;
        ct.lowerFlatbed();
        ct.unloadCar();
        boolean carsRollSomeDistanceAfterBeingUnloaded = ct.getX() + ct.getY() != v2.getX() + v2.getY();
        boolean canUnloadCars = ct.getLoadedCars().size() == 2;
        ct.raiseFlatbed();
        ct.gas(1);
        double testX = ct.getX();
        double testY = ct.getX();
        ct.move();
        boolean canMoveAfterUnloading = (ct.getX() != testX || ct.getY() != testY);

        Saab95 s2 = new Saab95();
        ct.move();
        ct.move();
        ct.move();
        ct.move();
        ct.move();
        ct.move();

        ct.loadCar(s2);
        boolean cantLoadCarOutOfReach = ct.getLoadedCars().size() == 2;

        assert moveWithCars && cantUnloadWhileMoving && cantUnloadWhileFlatBedIsUp && canUnloadCars && canMoveAfterUnloading && carsRollSomeDistanceAfterBeingUnloaded;
    }

}

