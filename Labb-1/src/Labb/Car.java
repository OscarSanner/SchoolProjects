package Labb;

import java.awt.*;

public abstract class Car {
    private int nrDoors; // Number of doors on the car
    private double getEnginePower; // Engine power of the car
    private double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    private String modelName; // The car model name

    public Car(int nrDoors, Color color, double enginePower, String modelName) {
        this.nrDoors = nrDoors;
        this.color = color;
        this.getEnginePower = enginePower;
        this.modelName = modelName;
        stopEngine();
    }

    protected void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    protected void stopEngine() {
        currentSpeed = 0;
    }

    public int getNrDoors() {
        return nrDoors;
    }

    public double getEnginePower() {
        return getEnginePower;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public Color getColor() {
        return color;
    }

    protected void setColor(Color clr) {
        color = clr;
    }

    protected void startEngine() {
        currentSpeed = 0.1;
    }

    protected abstract double speedFactor();

    protected abstract void incrementSpeed(double amount);

    protected abstract void decrementSpeed(double amount);

}
