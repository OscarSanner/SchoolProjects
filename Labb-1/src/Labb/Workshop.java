package Labb;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Workshop <T extends Car> implements ICanLoadCars <T> {

    private double x;
    private double y;
    private List<T> carsInTheShop;
    private int maxCarCapacity;

    public Workshop(double x, double y, int maxCarCapacity) {
        carsInTheShop = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.maxCarCapacity = maxCarCapacity;
    }

    public List<T> getCarsInTheShop() {
        return new ArrayList<>(carsInTheShop);
    }

    public void loadCar(T carToBeLoaded) {
        if (loadCheck(carToBeLoaded)) {
            carsInTheShop.add(carToBeLoaded);
            carToBeLoaded.setLoaded(this);
        }
    }

    public void unloadCar(Car carToBePickedUp) {
        if (carsInTheShop.contains(carToBePickedUp)) {
            carsInTheShop.remove(carToBePickedUp);
            carToBePickedUp.rollOutFromCarrier();
        }
    }

    public boolean loadCheck(Car carToBeLoaded) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(pow((carToBeLoaded.getX() - this.x), 2) + pow((carToBeLoaded.getY() - this.y), 2));
        return distanceBetweenLoaderAndToBeLoaded < 2 && carToBeLoaded.getCarrier() == null && carsInTheShop.size() < maxCarCapacity;              //Avståndsformeln, 2 satt för 2 meters avstånd(?)
    }

    @Override
    public boolean confirmLoad (Vehicle vehicleRequestedToBeLoaded){
        return carsInTheShop.contains(vehicleRequestedToBeLoaded);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
