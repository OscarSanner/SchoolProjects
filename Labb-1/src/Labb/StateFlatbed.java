package Labb;

import java.util.ArrayDeque;
import java.util.Deque;
import static java.lang.Math.*;

public class StateFlatbed <T extends Vehicle> extends Flatbed implements CanLoadCars <T> {

    //public Deque<T> loadedCars = new ArrayDeque<>();

    private CarLoad<T> carLoad;

    public StateFlatbed() {
        super(0, 1);
        carLoad = new CarLoad<>();
    }

    public Deque<T> getLoadedCars() {
        return carLoad.loadedCars;
    }

    public void loadCar(T carToBeLoaded, Vehicle loadedOnTo) {
        if (loadCheck(carToBeLoaded, loadedOnTo) && getCurrentAngle() == getMinAngle()) {
            carLoad.loadCar(carToBeLoaded, loadedOnTo);
            // UPDATE COORDINATES OF CAR?
        }
    }

    public void unloadCar() {
        if (getCurrentAngle() == getMinAngle() && !carLoad.getLoadedCars().isEmpty()) {
            carLoad.unloadCar();
        }
    }


    public boolean loadCheck(T carToBeLoaded, Vehicle loadedOnTo) {
        return carLoad.loadCheck(carToBeLoaded, loadedOnTo);

    }
}
