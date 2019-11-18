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
    public void loadCar(T carToBeLoaded, Vehicle loadedOnTo) {
        loadedCars.push(carToBeLoaded);


        carToBeLoaded.setX(loadedOnTo.getX());
        carToBeLoaded.setY(loadedOnTo.getY());
    }

    public void loadCar(T carToBeLoaded) {
        loadedCars.push(carToBeLoaded);
    }

    @Override
    public void unloadCar() {
        loadedCars.peek().setY(loadedCars.peek().getY()+2);                         //TEMPORARY SETTER, UNSAFE!!! (Avlastas på varandra, problem?)
        loadedCars.pop();
    }

    @Override
    public boolean loadCheck(T vehicleToBeLoaded, Vehicle loadedOnTo) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(  pow( (vehicleToBeLoaded.getX() - loadedOnTo.getX()) , 2) + pow( (vehicleToBeLoaded.getY() - loadedOnTo.getY()) , 2) );
        return distanceBetweenLoaderAndToBeLoaded < 2;              //Avståndsformeln, 2 satt för 2 meters avstånd(?)
    }

}
