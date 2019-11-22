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
        MotorizedVehicle motorizedVehicleBeingUnloaded = loadedCars.peek();
        loadedCars.pop();
        motorizedVehicleBeingUnloaded.rollOutFromCarrier();
    }

    public void unloadLastCar() {
        MotorizedVehicle tempForMotorizedVehicleBeingUnloaded = loadedCars.getLast();
        loadedCars.removeLast();
        tempForMotorizedVehicleBeingUnloaded.rollOutFromCarrier();
    }
}
