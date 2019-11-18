package Labb;

import java.util.Deque;

public class Workshop <T extends Vehicle> implements CanLoadCars <T> {

    private CarLoad<T> carLoad;

    public Workshop(){
        this.carLoad = new CarLoad<>();
    }


    public Deque<T> getLoadedCars() {
        return carLoad.loadedCars;
    }

    public void loadCar(T carToBeLoaded, Vehicle loadedOnTo) {
        carLoad.loadCar(carToBeLoaded, loadedOnTo);
        // UPDATE COORDINATES OF CAR?
    }

    public void loadCar(T carToBeLoaded) {
        carLoad.loadCar(carToBeLoaded);
        // UPDATE COORDINATES OF CAR?

    }

    public void unloadCar() {
        carLoad.unloadCar();

    }


    public boolean loadCheck(T carToBeLoaded, Vehicle loadedOnTo) {
        return carLoad.loadCheck(carToBeLoaded, loadedOnTo);
    }
}
