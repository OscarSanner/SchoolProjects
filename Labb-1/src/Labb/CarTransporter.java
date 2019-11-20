package Labb;

import java.awt.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CarTransporter extends Truck {

    private StateFlatbed<Car> flatbed = new StateFlatbed<>();

    public CarTransporter() {
        super(2, Color.YELLOW, 300, "Car Transporter");
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
    protected double speedFactor() {
        return getEnginePower() * 0.003;
    }

    public void loadCar(Car carToBeLoaded) {
        if (loadCheck(carToBeLoaded) && flatbed.getCurrentAngle() == flatbed.getMinAngle()) {
            flatbed.loadCar(carToBeLoaded);
            carToBeLoaded.setLoaded(this);
        }
    }

    public boolean confirmLoad (Vehicle vehicleRequestedToBeLoaded){
        return flatbed.getLoadedCars().contains(vehicleRequestedToBeLoaded);
    }

    public void unloadCar() {
        if (flatbed.getCurrentAngle() == flatbed.getMinAngle() && !flatbed.getLoadedCars().isEmpty()) {
            flatbed.unloadFirstCar();
        }
    }

    public boolean loadCheck(Car vehicleToBeLoaded) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(pow((vehicleToBeLoaded.getX() - this.getX()), 2) + pow((vehicleToBeLoaded.getY() - this.getY()), 2));
        return distanceBetweenLoaderAndToBeLoaded < 2;              //Avståndsformeln, 2 satt för 2 meters avstånd(?)
    }
}
