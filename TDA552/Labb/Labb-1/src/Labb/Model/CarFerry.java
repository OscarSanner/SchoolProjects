package Labb.Model;

import Labb.View.DrawPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Class for CarFerry, subclass to MotorizedVehicle.
 * Implements the interface ICanLoadCars which enables the ferry to load motorizedVehicles.
 */
class CarFerry extends MotorizedVehicle implements ICanLoadCars {

    /**
     * The type of flatbed the Ferry has and what types of vehicles it can carry.
     */
    private StateFlatbed<Car> flatbed = new StateFlatbed<>();

    /**
     * Constructor for Ferry, initiates the properties for a Ferry using the constructor in the superclass MotorizedVehicle.
     */
    public CarFerry(double x, double y) {
        super(200, Color.WHITE, 3000, "Car Transporter", x, y);
    }

    /**
     * Method for raising the flatbed.
     */
    public void raiseFlatbed() {
        flatbed.raiseFlatbed();
    }

    /**
     * Method for lowering the flatbed.
     * Can only be done when the ferry is not moving.
     */
    public void lowerFlatbed() {
        if (this.getCurrentSpeed() == 0) {
            flatbed.lowerFlatbed();

        }
    }

    /**
     * Overrides the move-method in MotorizedVehicle to add extra conditions for movement.
     * Moves only when the flatbed is lowered and also moves all the motorizedVehicles loaded on to the ferry.
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

    @Override
    public void gas(double amount){
        if (flatbed.getCurrentAngle() == flatbed.getMaxAngle()){
            super.gas(amount);
        }
    }

    /**
     * Method for loading a car.
     * Initiates a "handshake"-chain where checks are done to made sure the car to be loaded actually can be loaded.
     * Checks are: flatbed is lowered, car isn't loaded on another entity, car is close enough to be loaded.
     * @param carToBeLoaded the car in question which is to be loaded on to the ferry.
     */
    @Override
    public void loadCar(Car carToBeLoaded) {
        if (loadCheck(carToBeLoaded) && flatbed.getCurrentAngle() == flatbed.getMinAngle() && carToBeLoaded.getCarrier() == null) {
            flatbed.loadCar(carToBeLoaded);
            carToBeLoaded.setLoaded(this);
        }
    }

    /**
     * Method for confirming this ferry is the one asking to load the vehicle in question.
     * Called by the vehicle in the "handshake"-chain when this ferry tries to load the vehicle.
     * @param motorizedVehicleRequestedToBeLoaded the vehicle this ferry wants to load, confirming it's actually this ferry.
     * @return true if this ferry coincides with the ferry the vehicle is wanting to be loaded on, otherwise false.
     */
    @Override
    public boolean confirmLoad (MotorizedVehicle motorizedVehicleRequestedToBeLoaded){
        return flatbed.getLoadedCars().contains(motorizedVehicleRequestedToBeLoaded);
    }


    /**
     * Unloads motorizedVehicles from the ferry in a specific order if the flatbed is lowered.
     * The first car to be loaded is also the first car to be unloaded.
     */
    public void unloadCar() {
        if (flatbed.getCurrentAngle() == flatbed.getMinAngle() && !flatbed.getLoadedCars().isEmpty()) {
            flatbed.unloadLastCar();
        }
    }

    /**
     * Method for checking that the car to be loaded is within distance and isn't currently loaded on another entity.
     * @param carToBeLoaded car trying to be loaded.
     * @return true if car isn't currently loaded and is close enough to the ferry.
     */
    @Override
    public boolean loadCheck(Car carToBeLoaded) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(pow((carToBeLoaded.getX() - this.getX()), 2) + pow((carToBeLoaded.getY() - this.getY()), 2));
        return distanceBetweenLoaderAndToBeLoaded < 2 && carToBeLoaded.getCarrier() == null;              //Avståndsformeln, 2 satt för 2 meters avstånd(?)
    }

    /**
     * Method for getting a list of the motorizedVehicles being carried.
     * @return A cloned list of the list of motorizedVehicles being carried.
     */
    public Deque<Car> getLoadedCars(){
        return new ArrayDeque<Car>( flatbed.getLoadedCars());
    }

    /**
     * Method used by methods in MotorizedVehicle for increasing and decreasing speed.
     * @return calculates a factor based on the engine power.
     */
    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.0002;
    }

    /**
     * Method for getting the current angle of the flatbed. (Raised or lowered)
     * @return returns 1 if the flatbed is raised or 0 if the flatbed if lowered.
     */
    public int getCurrentAngle(){
        return flatbed.currentAngle;
    }

    @Override
    public BufferedImage getIcon() {
        try{return ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/CarFerry.jpg"));
        }catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
