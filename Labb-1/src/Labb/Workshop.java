package Labb;

import java.util.Deque;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Workshop <T extends Vehicle> implements CanLoadCars <T> {

    private double x;
    private double y;
    private CarLoad<T> carLoad;

    public Workshop(double x, double y){
        this.carLoad = new CarLoad<>();
        this.x = x;
        this.y = y;
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
        carToBeLoaded.updateWithCarrier();
        // UPDATE COORDINATES OF CAR?

    }

    public void unloadCar() {
        carLoad.unloadCar();

    }


    public boolean loadCheck(Car vehicleToBeLoaded) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(pow((vehicleToBeLoaded.getX() - this.x), 2) + pow((vehicleToBeLoaded.getY() - this.y), 2));
        return distanceBetweenLoaderAndToBeLoaded < 2;              //Avståndsformeln, 2 satt för 2 meters avstånd(?)
}
