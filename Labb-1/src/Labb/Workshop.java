package Labb;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Class that represents a workshop, used to maintain cars, either all cars, or specific cars.
 * @param <T> generic parameter used to define which typ of car the shop will handle.
 */
public class Workshop <T extends Car> implements ICanLoadCars <T> {

    /**
     * The x-coordinate of the workshop.
     */
    private double x;
    /**
     * The y-coordinate of the workshop.
     */
    private double y;

    /**
     * A list that is used to list track of the cars in the shop.
     */
    private List<T> carsInTheShop;

    /**
     * The maximum amount of cars in the shop.
     */
    private int maxCarCapacity;

    /**
     * Constructor for the workshop. It allows setting x, y, and capacity when creating an object of the class.
     * @param x the initial x-coordinate of the workshop.
     * @param y the initial y-coordinate of the workshop.
     * @param maxCarCapacity the max capacity of the workshop.
     */
    public Workshop(double x, double y, int maxCarCapacity) {
        carsInTheShop = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.maxCarCapacity = maxCarCapacity;
    }

    /**
     * A getter for a copy of the list holding the cars in the shop.
     * @return returns a copy(!) of the list with cars in the shop.
     */
    public List<T> getCarsInTheShop() {
        return new ArrayList<>(carsInTheShop);
    }

    /**
     * Takes the car into the workshop, and disables the driving functions of the car (with setLoaded).
     * It also sets the x- and y-coordinates of the car to those of the workshop. (with setLoaded)
     * @param carToBeLoaded the car potentially being loaded into the workshop.
     */
    public void loadCar(T carToBeLoaded) {
        if (loadCheck(carToBeLoaded)) {
            carsInTheShop.add(carToBeLoaded);
            carToBeLoaded.setLoaded(this);
        }
    }

    /**
     * removes the car from the workshop, as well as from "carsInTheShop". Uses the method "rollOutFromCarrier"
     * to achieve this.
     * @param carToBePickedUp the car which is potentially being picked up.
     */
    public void unloadCar(Car carToBePickedUp) {
        if (carsInTheShop.contains(carToBePickedUp)) {
            carsInTheShop.remove(carToBePickedUp);
            carToBePickedUp.rollOutFromCarrier();
        }
    }

    /**
     * Checks if a car is close enough to be taken into the shop, as well as making sure the shop isn't full.
     * Furthermore the method makes sure that the car is not currently loaded onto anything else.
     * @param carToBeLoaded the car potentially being loaded into the workshop.
     * @return returns true if the car passes the tests and can be taken into the workshop. If not, false.
     */
    public boolean loadCheck(Car carToBeLoaded) {
        double distanceBetweenLoaderAndToBeLoaded = sqrt(pow((carToBeLoaded.getX() - this.x), 2) + pow((carToBeLoaded.getY() - this.y), 2));
        return distanceBetweenLoaderAndToBeLoaded < 2 && carToBeLoaded.getCarrier() == null && carsInTheShop.size() < maxCarCapacity;              //Avståndsformeln, 2 satt för 2 meters avstånd(?)
    }


    /**
     * Method for confirming this cartransporter is the one asking to load the vehicle in question.
     * Called by the vehicle in the "handshake"-chain when this cartransporter tries to load the vehicle.
     * @param motorizedVehicleRequestedToBeLoaded the vehicle this cartransporter wants to load, confirming it's actually this cartransporter.
     * @return true if this cartransporter coincides with the cartransporter the vehicle is wanting to be loaded on, otherwise false.
     */
    @Override
    public boolean confirmLoad (MotorizedVehicle motorizedVehicleRequestedToBeLoaded){
        return carsInTheShop.contains(motorizedVehicleRequestedToBeLoaded);
    }

    /**
     * Method for getting x-cooridnate.
     * @return the x-coordinate.
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Method for getting y-cooridnate.
     * @return the y-coordinate.
     */
    @Override
    public double getY() {
        return y;
    }
}
