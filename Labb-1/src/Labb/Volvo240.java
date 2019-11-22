package Labb;

import java.awt.*;

/**
 * Class that represents a specific car called volvo240.
 */
public class Volvo240 extends Car {

    private final static double trimFactor = 1.25;

    /**
     * Constructor for initialising variables declared in MotorizedVehicle (super class).
     */
    public Volvo240() {
        super(4, Color.black, 100, "Volvo240");
    }

    /**
     * Method used by methods in MotorizedVehicle for increasing and decreasing speed.
     * @return calculates a factor based on the engine power and the trimfactor.
     */
    protected double speedFactor() {
        return getEnginePower() * 0.01 * trimFactor;
    }



}

