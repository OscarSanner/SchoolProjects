package Labb.Model;

import java.awt.*;


/**
 * Abstract class for representing a truck. All trucks have flatbeds, and all
 * trucks are motorized vehicles.
 */
abstract class Truck extends MotorizedVehicle {


    /**
     * Constructor for the truck.
     * @param nrDoors The number of doors.
     * @param color The color of the truck.
     * @param enginePower The engine power of the truck.
     * @param modelName The model name of the truck.
     */
    public Truck(int nrDoors, Color color, double enginePower, String modelName, double x, double y) {
        super(nrDoors, color, enginePower, modelName, x, y);
    }

    /**
     * Getter for the current angle of the flatbed. Abstract as the type of flatbeds
     * differ depending on the truck.
     * @return the current angle of the flatbed.
     */
    public abstract int getCurrentAngle();


}
