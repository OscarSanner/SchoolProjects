/*import Labb.Model.Car;
import Labb.Model.Volvo240;
import Labb.Model.Workshop;
import org.junit.Test;

public class TestWorkshop {

    @Test
    public void TestWorkshopLoad(){
        Workshop<Volvo240> wVolvo = new Workshop(0,0,3);
        Workshop<Car> wCar = new Workshop(0,0,3);
        Volvo240 v1 = new Volvo240(0,0);
        Volvo240 v2 = new Volvo240(0,0);
        Volvo240 v3 = new Volvo240(0,0);
        Volvo240 v4 = new Volvo240(0,0);

        wVolvo.loadCar(v1);
        boolean containsAvolvo = wVolvo.getCarsInTheShop().contains(v1);
        wVolvo.loadCar(v2);
        wVolvo.loadCar(v3);
        boolean containsThreeVolvos = wVolvo.getCarsInTheShop().contains(v1) && wVolvo.getCarsInTheShop().contains(v2) && wVolvo.getCarsInTheShop().contains(v3);
        wVolvo.loadCar(v4);
        boolean maxCap = wVolvo.getCarsInTheShop().size() == 3;

        Volvo240 v5 = new Volvo240(0,0);
        Workshop<Volvo240> wVolvoTEMP = new Workshop(3,3,3);
        wVolvoTEMP.loadCar(v5);
        boolean shouldBeEmpty = wVolvoTEMP.getCarsInTheShop().isEmpty();

        wCar.loadCar(v1);
        boolean shouldBeEmpty2 = wCar.getCarsInTheShop().isEmpty() && wVolvo.getCarsInTheShop().contains(v1);



        assert containsAvolvo && containsThreeVolvos && maxCap && shouldBeEmpty && shouldBeEmpty2;
    }

    @Test
    public void TestWorkshopUnload(){
        Workshop<Volvo240> wVolvo = new Workshop(0,0,3);
        Volvo240 v1 = new Volvo240(0,0);
        Volvo240 v2 = new Volvo240(0,0);
        Volvo240 v3 = new Volvo240(0,0);
        Volvo240 v4 = new Volvo240(0,0);
        wVolvo.loadCar(v1);
        wVolvo.loadCar(v2);
        wVolvo.loadCar(v3);

        wVolvo.unloadCar(v1);
        boolean sizeShouldBe2 = wVolvo.getCarsInTheShop().size() == 2;
        boolean carrierShouldBeNull = v1.getCarrier() == null;

        wVolvo.unloadCar(v2);
        wVolvo.unloadCar(v3);

        boolean shouldBeEmpty = wVolvo.getCarsInTheShop().size() == 0;

        assert  sizeShouldBe2 && carrierShouldBeNull && shouldBeEmpty;

    }





}


 */