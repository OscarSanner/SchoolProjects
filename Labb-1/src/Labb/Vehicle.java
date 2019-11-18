package Labb;

import java.awt.*;

/**
 * Abstract class for all cars.
 * Contains methods for moving a car in 2d.
 * Contains common properties of a car.
 */
public abstract class Vehicle implements Movable, HasXHasY {


    /**
     * Array for right turns.
     * An array containing all the possible directions,
     * in which the element after the current direction will be the next direction when turning right.
     */
    private static Direction[] directionArrayRight = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

    /**
     * Array for left turns.
     * An array containing all the possible directions,
     * in which the element after the current direction will be the next direction when turning left.
     */
    private static Direction[] directionArrayLeft = {Direction.LEFT, Direction.DOWN, Direction.RIGHT, Direction.UP};

    /**
     * The number of doors on the car.
     */
    private int nrDoors;

    /**
     * The engine power of the car.
     */
    private double getEnginePower;

    /**
     * The current speed of the car.
     */
    private double currentSpeed;

    /**
     * Color of the car.
     */
    private Color color;

    /**
     * The model name of the car.
     */
    private String modelName;

    /**
     * The current x coordinate of the car.
     */
    private double x;

    /**
     * The current y coordinate of the car.
     */
    private double y;


    /**
     * The current direction the car is facing. (UP, DOWN, LEFT, RIGHT)
     */
    private Direction currentDirection;


    /**
     * Constructor for common properites of a car, all cars initiated standing still.
     * @param nrDoors the number of doors on the car.
     * @param color the color of the car.
     * @param enginePower the driving power of the car, max speed.
     * @param modelName the cars model.
     */
    public Vehicle(int nrDoors, Color color, double enginePower, String modelName) {
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

    /**
     * Increases the speed of the car, by calling the incrementSpeed method.
     * @param amount parameter that determines how much the car
     * will increase its speed. Essentially the gaspedal.
     */
    public void gas(double amount) {
        if (!(amount < 0 || amount > 1)){
            incrementSpeed(amount);
        }
    }

    /**
     * Decreases the speed of the car, by calling the decrementSpeed method.
     * @param amount parameter that determines how much the car
     * will decrease its speed. Essentially the breakpedal.
     */
    public void brake(double amount) {
        if (!(amount < 0 || amount > 1)){
            decrementSpeed(amount);
        }
    }

    /**
     * Moves the car. The distance it's moved is determined by
     * the current speed, and the direction it moves is determined
     * by the current direction.
     */
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


    /**
     * Turns the car to the left (rotates the car 90 degrees to the left).
     */
    @Override
    public void turnLeft() {
        currentDirection = findNextDirection(directionArrayLeft);
    }

    /**
     * Turns the car to the right (rotates the car 90 degrees to the right).
     */
    @Override
    public void turnRight() {
        currentDirection = findNextDirection(directionArrayRight);
    }

    /**
     * Determines which direction is next, is only called internally
     * by other methods in class, more specifically turnLeft and turnRight.
     * @param dirArr should receive an array containing all the possible directions,
     * in which the element after the current direction will be the next direction
     * @return returns the next direction.
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

    /**
     * Used by methods in this class to determine the increase
     * in speed demanded by the gas and break methods
     * @return calculates a value based on the properties defined in each car.
     */
    protected abstract double speedFactor();






    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
