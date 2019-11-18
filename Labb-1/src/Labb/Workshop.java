package Labb;

import java.util.Deque;

public class Workshop <T extends Vehicle> implements CanLoadCars <T>, HasXHasY {

    private CarLoad<T> carLoad;
    private double X;
    private double Y;

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

    @Override
    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }
}
