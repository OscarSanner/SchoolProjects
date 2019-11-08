package Labb;

import java.awt.*;

public class Saab95 extends Car {

    private boolean turboOn;

    public Saab95() {
        super(2, Color.red, 125, "Saab95");
        turboOn = false;
    }

    protected void setTurboOn() {
        turboOn = true;
    }

    protected void setTurboOff() {
        turboOn = false;
    }

    protected double speedFactor() {
        double turbo = 1;
        if (turboOn) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }

    protected void incrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() + speedFactor() * amount);
    }

    protected void decrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() - speedFactor() * amount);
    }

    // TODO fix this method according to lab pm
    public void gas(double amount) {
        incrementSpeed(amount);
    }

    // TODO fix this method according to lab pm
    public void brake(double amount) {
        decrementSpeed(amount);
    }
}
