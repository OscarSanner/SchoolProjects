package Labb;

import java.awt.*;

/**
 * Abstract Class for Cars, used to disinguished Cars from other Vehicles.
 */
public abstract class Car extends Vehicle{

    /**
     * Constructor for Cars.
     * @param nrDoors     the number of doors on the car.
     * @param color       the color of the car.
     * @param enginePower the driving power of the car, max speed.
     * @param modelName   the cars model.
     */
    public Car(int nrDoors, Color color, double enginePower, String modelName) {
        super(nrDoors, color, enginePower, modelName);
    }


}
