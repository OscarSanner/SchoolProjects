package Labb;

import java.awt.*;

/**
 *  Class that represents a specific car called Saab95.
 */
public class Saab95 extends Car {

    private boolean turboOn;

    /**
     * Constructor for class, initiates hardcoded properties of the car.
     */
    public Saab95() {
        super(2, Color.red, 125, "Saab95");
        turboOn = false;
    }

    /**
     * Method for turning turbo on.
     */
    public void setTurboOn() {
        turboOn = true;
    }
    /**
     * Method for turning turbo off.
     */
    public void setTurboOff() {
        turboOn = false;
    }

    /**
     * Used in methods where the car accelerates or brakes.
     * @return calculates a factor based on turbo turned on/off.
     */
    protected double speedFactor() {
        double turbo = 1;
        if (turboOn) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }
}
