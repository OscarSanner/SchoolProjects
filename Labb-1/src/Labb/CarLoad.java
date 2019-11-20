package Labb;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CarLoad <T extends Vehicle> implements CanLoadCars <T> {

    public Deque<T> loadedCars = new ArrayDeque<>();






    @Override
    public Deque<T> getLoadedCars() {
        return loadedCars;
    }

    @Override
    public void loadCar(T carToBeLoaded) {
        loadedCars.push(carToBeLoaded);
    }

    @Override
    public void unloadFirstCar() {          //TEMPORARY SETTER, UNSAFE!!! (Avlastas på varandra, problem?)
        Vehicle tempForVehicleBeingUnloaded = loadedCars.peek();
        loadedCars.pop();
        tempForVehicleBeingUnloaded.rollOutFromCarrier();
    }

    @Override
    public void unloadLastCar() {             //TEMPORARY SETTER, UNSAFE!!! (Avlastas på varandra, problem?)
        Vehicle tempForVehicleBeingUnloaded = loadedCars.getLast();
        loadedCars.removeLast();
        tempForVehicleBeingUnloaded.rollOutFromCarrier();
    }
    public void unloadSpecificCar (Car carToBeUnloaded){

    }
}
