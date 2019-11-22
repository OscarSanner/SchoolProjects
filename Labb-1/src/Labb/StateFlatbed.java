package Labb;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Flatbed which only has two stated(up or down). This type of flatbed is specifically
 * designed to carry cars.
 * @param <T> Can take all cars, or a specific type of cars.
 */
public class StateFlatbed<T extends Car> extends Flatbed{

    /**
     * As all state flatbeds can carry cars, it will also have a list (Deque) in which
     * the cars are indexed.
     */
    private Deque<T> loadedCars;

    /**
     * Constructor for the state flatbed.
     * In this particular type of flatbed the angle "1" represents the flatbed being
     * fully raised. The angle "0" will thus represent the flatbed being fully raised.
     * The list (Deque) is also initialized here.
     */
    public StateFlatbed() {
        super(0, 1, 1);
        loadedCars = new ArrayDeque<>();
    }

    /**
     * Getter for the list of loaded cars.
     * @return returns the list of loaded cars.
     */
    public Deque<T> getLoadedCars() {
        return loadedCars;
    }

    /**
     * Simply adds a car to the list of loaded cars. Rest is handled by
     * the vehicle carrying the flatbed. This method is thus ever used by
     * means of delegation.
     * @param carToBeLoaded the car to be loaded.
     */
    public void loadCar(T carToBeLoaded) {
        loadedCars.push(carToBeLoaded);
    }

    /**
     * This method will unload the first car in the flatbed. It will also
     * tell the car that it's no longer loaded on the flatbed, enabling the car
     * to drive once again, and moves the car away from it's previous carrier.
     */
    public void unloadFirstCar() {
        MotorizedVehicle motorizedVehicleBeingUnloaded = loadedCars.peek();
        loadedCars.pop();
        motorizedVehicleBeingUnloaded.rollOutFromCarrier();
    }

    /**
     * This method will unload the last car in the flatbed. It will also
     * tell the car that it's no longer loaded on the flatbed, enabling the car
     * to drive once again, and moves the car away from it's previous carrier.
     */
    public void unloadLastCar() {
        MotorizedVehicle tempForMotorizedVehicleBeingUnloaded = loadedCars.getLast();
        loadedCars.removeLast();
        tempForMotorizedVehicleBeingUnloaded.rollOutFromCarrier();
    }
}
