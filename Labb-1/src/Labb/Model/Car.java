package Labb.Model;

import java.awt.*;

/**
 * Abstract Class for Cars, subclass to MotorizedVehicle.
 * Used to disinguished Cars from other MotorizedVehicles.
 */
abstract class Car extends MotorizedVehicle {

    /**
     * Constructor for Cars.
     * @param nrDoors     the number of doors on the car.
     * @param color       the color of the car.
     * @param enginePower the driving power of the car, max speed.
     * @param modelName   the motorizedVehicles model.
     */
    public Car(int nrDoors, Color color, double enginePower, String modelName, double x, double y) {
        super(nrDoors, color, enginePower, modelName, x, y);
    }


}
