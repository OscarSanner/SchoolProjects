package Labb;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Class for CarTransporter, subclass to Truck and MotorizedVehicle.
 * Implements the interface ICanLoadCars which enables the cartransporter to load cars.
 */
public class CarTransporter <T extends Car> extends Truck implements ICanLoadCars <T> {

    /**
     * The type of flatbed the cartransporter has and what types of vehicles it can carry.
     */
    private StateFlatbed<T> flatbed = new StateFlatbed<>();

    /**
     * Constructor for cartransporter, initiates the properties for a cartransporter using the constructor in the superclass Truck and in turn MotorizedVehicle.
     */
    public CarTransporter() {
        super(2, Color.YELLOW, 300, "Car Transporter");
    }

    /**
     * Method for raising the flatbed.
     */
    public void raiseFlatbed() {
        flatbed.raiseFlatbed();
    }

    /**
     * Method for lowering the flatbed.
     * Can only be done if the cartransporter is not moving.
     */
    public void lowerFlatbed() {
        if (this.getCurrentSpeed() == 0) {
            flatbed.lowerFlatbed();
        }
    }

    /**
     * Overrides the move-method in MotorizedVehicle to add extra conditions for movement.
     * Moves only when the flatbed is lowered and also moves all the cars loaded on to the cartransporter.
     */
    @Override
    public void move() {
        if (flatbed.getCurrentAngle() == flatbed.getMaxAngle()) {
            super.move();
            for (Car c : flatbed.getLoadedCars()) {
                c.updateWithCarrier();
            }
        }
    }

    /**
     * Method for loading a car.
     * Initiates a "handshake"-chain where checks are done to made sure the car to be loaded actually can be loaded.
     * Checks are: flatbed is lowered, car isn't loaded on another entity, car is close enough to be loaded.
     * @param carToBeLoaded the car in question which is to be loaded on to the cartransporter.
     */
    @Override
    public void loadCar(T carToBeLoaded) {
        if (loadCheck(carToBeLoaded) && flatbed.getCurrentAngle() == flatbed.getMinAngle()) {
            flatbed.loadCar(carToBeLoaded);
            carToBeLoaded.setLoaded(this);
        }
    }

    /**
     * Method for confirming this cartransporter is the one asking to load the vehicle in question.
     * Called by the vehicle in the "handshake"-chain when this cartransporter tries to load the vehicle.
     * @param motorizedVehicleRequestedToBeLoaded the vehicle this cartransporter wants to load, confirming it's actually this cartransporter.
     * @return true if this cartransporter coincides with the cartransporter the vehicle is wanting to be loaded on, otherwise false.
     */
    @Override
    public boolean confirmLoad (MotorizedVehicle motorizedVehicleRequestedToBeLoaded){
        return flatbed.getLoadedCars().contains(motorizedVehicleRequestedToBeLoaded);
    }

    /**
     * Unloads cars from the cartransporter in a specific order if the flatbed is lowered.
     * The first car to be loaded is also the first car to be unloaded.
     */
    public void unloadCar() {
        if (flatbed.getCurrentAngle() == flatbed.getMinAngle() && !flatbed.getLoadedCars().isEmpty()) {
            flatbed.unloadFirstCar();
        }
    }

    /**
     * Method for checking that the car to be loaded is within distance and isn't currently loaded on another entity.
     * @param carToBeLoaded car trying to be loaded.
     * @return true if car isn't currently loaded and is close enough to the cartransporter.
     */
    @Override
    public boolean loadCheck(T carToBeLoaded) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(pow((carToBeLoaded.getX() - this.getX()), 2) + pow((carToBeLoaded.getY() - this.getY()), 2));
        return distanceBetweenLoaderAndToBeLoaded < 2 && carToBeLoaded.getCarrier() == null;                 //Avståndsformeln, 2 satt för 2 meters avstånd(?)
    }

    /**
     * Method for getting a list of the cars being carried.
     * @return A cloned list of the list of cars being carried.
     */
    public Deque<T> getLoadedCars(){
        return new ArrayDeque<T>( flatbed.getLoadedCars());
    }


    /**
     * Method for getting the current angle of the flatbed. (Raised or lowered)
     * @return returns 1 if the flatbed is raised or 0 if the flatbed if lowered.
     */
    @Override
    public int getCurrentAngle(){
        return flatbed.currentAngle;
    }

    /**
     * Method used by methods in MotorizedVehicle for increasing and decreasing speed.
     * @return calculates a factor based on the engine power.
     */
    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.003;
    }
}
