package Labb;

import java.util.ArrayDeque;
import java.util.Deque;

public class StateFlatbed extends Flatbed {
    public Deque<Car> getLoadedCars() {
        return loadedCars;
    }

    public Deque<Car> loadedCars = new ArrayDeque<>();


    public StateFlatbed() {
        super(0, 1);
    }

    public void loadCar (Car carToLoad){
        carToLoad.setLoaded(true);
        carToLoad.stopEngine();
        loadedCars.push(carToLoad);
    }

    public void deloadCar (Car carToDeload){
        loadedCars.pop();
        carToDeload.setLoaded(false);
        carToDeload.rollOut();
    }

}
