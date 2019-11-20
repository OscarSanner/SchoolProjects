package Labb;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Math.*;

public class StateFlatbed<T extends Vehicle> extends Flatbed implements CanLoadCars<T> {

    //public Deque<T> loadedCars = new ArrayDeque<>();

    private CarLoad<T> carLoad;

    public StateFlatbed() {
        super(0, 1);
        carLoad = new CarLoad<>();
    }

    public Deque<T> getLoadedCars() {
        return carLoad.loadedCars;
    }

    public void loadCar(T carToBeLoaded) {
        carLoad.loadCar(carToBeLoaded);
    }

    public void unloadFirstCar() {
        carLoad.unloadFirstCar();
    }
    public void unloadLastCar() {
        carLoad.unloadLastCar();
    }
}
