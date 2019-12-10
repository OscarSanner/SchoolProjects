/*import Labb.Model.*;
import org.junit.Test;

public class TestCarsOnCarriers {

    @Test
    public void MoveTestOfCarOnWorkshop(){
        Workshop<Car> wCar = new Workshop<>(0,0, 3);
        Volvo240 v1 = new Volvo240(0,0);

        v1.startEngine();
        v1.gas(1);
        wCar.loadCar(v1);

        boolean shouldContainCar = wCar.getCarsInTheShop().contains(v1);

        v1.startEngine();

        for(int i = 0; i < 20; i++){
            v1.move();
        }

        boolean shouldHaveSameCoordinates = v1.getX() == wCar.getX() && wCar.getY() == v1.getY();

        assert shouldContainCar && shouldHaveSameCoordinates;
    }


    @Test
    public void MoveTestOfCarOnACarLoader(){
        CarTransporter ct = new CarTransporter(0,0);
        Volvo240 v1 = new Volvo240(0,0);


        ct.lowerFlatbed();
        ct.loadCar(v1);
        ct.raiseFlatbed();
        v1.startEngine();
        v1.gas(1);
        v1.move();
        boolean shouldNotHaveMoved = v1.getX() == ct.getX() && v1.getY() == ct.getY();

        ct.gas(1);
        ct.gas(1);
        ct.gas(1);
        ct.gas(1);
        ct.gas(1);
        ct.move();
        boolean shouldBothBeMoving =  v1.getX() == ct.getX() && v1.getY() == ct.getY();

        v1.startEngine();
        v1.gas(1);
        v1.gas(1);
        v1.turnLeft();
        v1.gas(1);
        v1.gas(1);
        v1.move();
        v1.move();
        v1.move();
        boolean shouldNotBeAbleToMove = v1.getX() == ct.getX() && v1.getY() == ct.getY();

        assert shouldBothBeMoving && shouldNotHaveMoved && shouldNotBeAbleToMove;

    }



    @Test
    public void MoveTestOfCarOnACarFerry(){
        CarFerry ferry = new CarFerry(0,0);
        Volvo240 v1 = new Volvo240(0,0);


        ferry.lowerFlatbed();
        ferry.loadCar(v1);
        ferry.raiseFlatbed();
        v1.startEngine();
        v1.gas(1);
        v1.move();
        boolean shouldNotHaveMoved = v1.getX() == ferry.getX() && v1.getY() == ferry.getY();

        ferry.gas(1);
        ferry.gas(1);
        ferry.gas(1);
        ferry.gas(1);
        ferry.gas(1);
        ferry.move();
        boolean shouldBothBeMoving =  v1.getX() == ferry.getX() && v1.getY() == ferry.getY();

        v1.startEngine();
        v1.gas(1);
        v1.gas(1);
        v1.turnLeft();
        v1.gas(1);
        v1.gas(1);
        v1.move();
        v1.move();
        v1.move();
        boolean shouldNotBeAbleToMove = v1.getX() == ferry.getX() && v1.getY() == ferry.getY();

        assert shouldBothBeMoving && shouldNotHaveMoved && shouldNotBeAbleToMove;

    }

    @Test
    public void TestStealingACarFromACarrier(){
        CarTransporter ct111111 = new CarTransporter(0,0);
        CarTransporter ct222222 = new CarTransporter(0,0);
        Volvo240 v1 = new Volvo240(0,0);

        ct111111.lowerFlatbed();
        ct222222.lowerFlatbed();
        ct111111.loadCar(v1);
        boolean shouldContainCar = ct111111.getLoadedCars().contains(v1);

        ct222222.loadCar(v1);
        boolean shouldSTILLContainCar = ct111111.getLoadedCars().contains(v1) && ct222222.getLoadedCars().isEmpty();

        assert shouldContainCar && shouldSTILLContainCar;
    }
}


 */