package Labb;

import java.util.ArrayDeque;
import java.util.Deque;

public class StateFlatbed<T extends Car> extends Flatbed{

    private Deque<T> loadedCars;

    public StateFlatbed() {
        super(0, 1, 1);
        loadedCars = new ArrayDeque<>();
    }

    public Deque<T> getLoadedCars() {
        return loadedCars;
    }

    public void loadCar(T carToBeLoaded) {
        loadedCars.push(carToBeLoaded);
    }

    public void unloadFirstCar() {
        Vehicle vehicleBeingUnloaded = loadedCars.peek();
        loadedCars.pop();
        vehicleBeingUnloaded.rollOutFromCarrier();
    }

    public void unloadLastCar() {
        Vehicle tempForVehicleBeingUnloaded = loadedCars.getLast();
        loadedCars.removeLast();
        tempForVehicleBeingUnloaded.rollOutFromCarrier();
    }
}
