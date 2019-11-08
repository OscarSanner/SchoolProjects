package Labb;

import java.awt.*;

/**
 * Abstract class for all cars.
 * Contains methods for moving a car in 2d.
 * Contains common properties of a car.
 */
public abstract class Car implements Movable {
    private static Direction[] directionArrayRight = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
    private static Direction[] directionArrayLeft = {Direction.LEFT, Direction.DOWN, Direction.RIGHT, Direction.UP};

    private int nrDoors; // Number of doors on the car
    private double getEnginePower; // Engine power of the car
    private double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    private String modelName; // The car model name

    private double x;
    private double y;
    private Direction currentDirection;

    // Se till att era bilar implementerar interfacet Movable,
    // med någon lämplig intern representation av deras riktning och position.
    // Metoden move ska fungera så att beroende på riktning ökas (eller minskas)
    // bilens x- eller y-koordinat med dess currentSpeed.

    /**
     * Constructor for common properites of a car, all cars initiated standing still.
     * @param nrDoors the number of doors on the car.
     * @param color the color of the car.
     * @param enginePower the driving power of the car, max speed.
     * @param modelName the cars model.
     */
    public Car(int nrDoors, Color color, double enginePower, String modelName) {
        this.nrDoors = nrDoors;
        this.color = color;
        this.getEnginePower = enginePower;
        this.modelName = modelName;

        this.x = 0;
        this.y = 0;
        this.currentDirection = Direction.UP;

        stopEngine();
    }

    /**
     * Method for getting the current direction the car is facing in a 2d plane.
     * @return the direction the car is facing.
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Method for getting the current x-coordinate of the car.
     * @return the x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Method for getting the current y-coordinate of the car.
     * @return the y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the current speed of the car to the given value.
     * Used as a helper method for starting the car, acceleration and braking.
     * @param currentSpeed the given value.
     */
    private void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * Stops the engine of the car, setting its speed to 0.
     */
    public void stopEngine() {
        currentSpeed = 0;
    }

    /**
     * Method for getting the numbers of door on the car.
     * @return the number of doors on the car.
     */
    public int getNrDoors() {
        return nrDoors;
    }

    /**
     * Method for getting the driving power of the car, max speed.
     * @return the maximum speed.
     */
    public double getEnginePower() {
        return getEnginePower;
    }

    /**
     * Method for getting the current speed of the car.
     * @return the current speed.
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Method for getting the color of the car.
     * @return the color of the car.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Method for setting the color of the car to the given color.
     * @param clr the given color the car will get.
     */
    public void setColor(Color clr) {
        color = clr;
    }

    /**
     * Method for starting the car by giving it a starting speed.
     */
    public void startEngine() {
        currentSpeed = 0.1;
    }

    /**
     * Increases the speed of the car with the given amount from the gas-method.
     * The increase of speed is determined by adding a speedfactor (in conjuction with the amount) to the current speed.
     * The speedfactor is calculated through another method, specific for each type of car.
     * @param amount how much the cars speed will increase, given by gas-method.
     */
    private void incrementSpeed(double amount) {
        setCurrentSpeed(Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower()));
    }

    /**
     * Decreases the speed of the car with the given amount from the brake-method.
     * The decrease of speed is determined by subtracting a speedfactor (in conjuction with the amount) to the current speed.
     * The speedfactor is calculated through another method, specific for each type of car.
     * @param amount how much the cars speed will decrease, given by brake-method.
     */
    private void decrementSpeed(double amount) {
        setCurrentSpeed(Math.max(getCurrentSpeed() - speedFactor() * amount, 0));
    }

    public void gas(double amount) {
        if (!(amount < 0 || amount > 1)){
            incrementSpeed(amount);
        }
    }

    public void brake(double amount) {
        if (!(amount < 0 || amount > 1)){
            decrementSpeed(amount);
        }
    }
    
    @Override
    public void move() {
        switch (currentDirection) {
            case UP:
                y += currentSpeed;
                break;
            case DOWN:
                y -= currentSpeed;
                break;
            case LEFT:
                x -= currentSpeed;
                break;
            case RIGHT:
                x += currentSpeed;
                break;
        }
    }

    @Override
    public void turnLeft() {
        currentDirection = findNextDirection(directionArrayLeft);
    }

    @Override
    public void turnRight() {
        currentDirection = findNextDirection(directionArrayRight);
    }

    /**
     *
     * @param dirArr
     * @return
     */
    private Direction findNextDirection(Direction[] dirArr) {
        Direction nextDirection = null;
        for (int i = 0; i < dirArr.length; i++) {
            if (currentDirection == dirArr[i]) {
                nextDirection = dirArr[(i + 1) % dirArr.length];
                break;
            }
        }
        return nextDirection;
    }

    protected abstract double speedFactor();
}
