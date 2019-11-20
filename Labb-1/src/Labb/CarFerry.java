package Labb;

import java.awt.*;
import java.util.Deque;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CarFerry extends Vehicle implements ICanLoadCars {

    private StateFlatbed<Car> flatbed = new StateFlatbed<>();
    public CarFerry() {
        super(200, Color.WHITE, 3000, "Car Transporter");
    }

    public void raiseFlatbed() {
        flatbed.raiseFlatbed();
    }
    public void lowerFlatbed() {
        if (this.getCurrentSpeed() == 0) {
            flatbed.lowerFlatbed();
        }
    }

    @Override
    public void move() {
        if (flatbed.getCurrentAngle() == flatbed.getMaxAngle()) {
            super.move();
            for (Car c : flatbed.getLoadedCars()) {
                c.updateWithCarrier();
            }
        }
    }

    @Override
    public void loadCar(Car carToBeLoaded) {
        if (loadCheck(carToBeLoaded) && flatbed.getCurrentAngle() == flatbed.getMinAngle() && carToBeLoaded.getCarrier() == null) {
            flatbed.loadCar(carToBeLoaded);
            carToBeLoaded.setLoaded(this);
        }
    }

    @Override
    public boolean confirmLoad (Vehicle vehicleRequestedToBeLoaded){
        return flatbed.getLoadedCars().contains(vehicleRequestedToBeLoaded);
    }


    public void unloadCar() {
        if (flatbed.getCurrentAngle() == flatbed.getMinAngle() && !flatbed.getLoadedCars().isEmpty()) {
            flatbed.unloadLastCar();
        }
    }

    @Override
    public boolean loadCheck(Car carToBeLoaded) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(pow((carToBeLoaded.getX() - this.getX()), 2) + pow((carToBeLoaded.getY() - this.getY()), 2));
        return distanceBetweenLoaderAndToBeLoaded < 2 && carToBeLoaded.getCarrier() == null;              //Avståndsformeln, 2 satt för 2 meters avstånd(?)
    }

    public Deque<Car> getLoadedCars(){
        return flatbed.getLoadedCars();
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.0002;
    }
}
