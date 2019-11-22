package Labb;

import java.awt.*;
import java.util.Random;

/**
 * Abstract class for all motorized vehicles.
 * Contains methods for moving a vehicle in 2d.
 * Contains common properties of a motorized vehicle.
 */
public abstract class MotorizedVehicle implements IMovable {

    /**
     * Variable is set to true of the vehicle is loaded onto a carrier.
     */
    private boolean isLoadedOnACarrier;

    /**
     * The vehicle has a "ICanLoadCars" which is an object able to load the vehicle
     * (transporter, workshop, etc).
     */
    private ICanLoadCars carrier;

    /**
     * Method used to set the carrier, and to set the "isLoadedOnACarrier" boolean.
     * The method also turns the engine off. The method will only return ever do anything
     * if it's called upon by a workshop/transporter currently transporting the vehicle.
     * In other words, the potential carrier has to confirm that the vehicle i actually loaded by the carrier.
     * @param potentialCarrier the carrier this method is attempting to set as its current carrier.
     */
    void setLoaded(ICanLoadCars potentialCarrier) {
        if (potentialCarrier.confirmLoad(this)) {
            isLoadedOnACarrier = true;
            carrier = potentialCarrier;
            this.stopEngine();
            setX(potentialCarrier.getX());
            setY(potentialCarrier.getY());
        }
    }

    /**
     * Updates the vehicles coordinates to it's carrier. Usually called upon by the carrier when
     * the carrier is moved.
     */
    void updateWithCarrier() {
        if (carrier != null) {
            setX(carrier.getX());
            setY(carrier.getY());
        }
    }

    /**
     * Method usually called by the current carrier, in order to remove the vehicle. As a safety measure
     * the carrier also has to confirm that the vehicle is actually removed from it's inventory list.
     * The vehicle will roll out from the carrier to some random point 2-3 coordinates away from the vehicle.
     * in both x and y.
     */
    void rollOutFromCarrier() {
        if (!carrier.confirmLoad(this)) {
            isLoadedOnACarrier = false;
            carrier = null;
            this.setY(getY() + 2 + new Random().nextDouble());
            this.setX(getX() + 2 + new Random().nextDouble());
        }
    }


    /**
     * Private(!) method for setting the x coordinates.
     * @param x, the new x coordinate.
     */
    private void setX(double x) {
        this.x = x;
    }

    /**
     * Private(!) method for setting the y coordinates.
     * @param y, the new y coordinate.
     */
    private void setY(double y) {
        this.y = y;
    }

    /**
     * Getter for the current carrier.
     */
    public ICanLoadCars getCarrier() {
        return carrier;
    }

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
     * The number of doors on the vehicle.
     */
    private int nrDoors;

    /**
     * The engine power of the vehicle.
     */
    private double getEnginePower;

    /**
     * The current speed of the vehicle.
     */
    private double currentSpeed;

    /**
     * Color of the vehicle.
     */
    private Color color;

    /**
     * The model name of the vehicle.
     */
    private String modelName;

    /**
     * The current x coordinate of the vehicle.
     */
    private double x;

    /**
     * The current y coordinate of the vehicle.
     */
    private double y;


    /**
     * The current direction the vehicle is facing. (UP, DOWN, LEFT, RIGHT)
     */
    private Direction currentDirection;


    /**
     * Constructor for common properites of a vehicle, all cars initiated standing still.
     *
     * @param nrDoors     the number of doors on the vehicle.
     * @param color       the color of the vehicle.
     * @param enginePower the driving power of the vehicle, max speed.
     * @param modelName   the cars model.
     */
    public MotorizedVehicle(int nrDoors, Color color, double enginePower, String modelName) {
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
     * Method for getting the current direction the vehicle is facing in a 2d plane.
     *
     * @return the direction the vehicle is facing.
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Method for getting the current x-coordinate of the vehicle.
     *
     * @return the x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Method for getting the current y-coordinate of the vehicle.
     *
     * @return the y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the current speed of the vehicle to the given value.
     * Used as a helper method for starting the vehicle, acceleration and braking.
     *
     * @param currentSpeed the given value.
     */
    private void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * Stops the engine of the vehicle, setting its speed to 0.
     */
    public void stopEngine() {
        currentSpeed = 0;
    }

    /**
     * Method for getting the numbers of door on the vehicle.
     *
     * @return the number of doors on the vehicle.
     */
    public int getNrDoors() {
        return nrDoors;
    }

    /**
     * Method for getting the driving power of the vehicle, max speed.
     *
     * @return the maximum speed.
     */
    public double getEnginePower() {
        return getEnginePower;
    }

    /**
     * Method for getting the current speed of the vehicle.
     *
     * @return the current speed.
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Method for getting the color of the vehicle.
     *
     * @return the color of the vehicle.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Method for setting the color of the vehicle to the given color.
     *
     * @param clr the given color the vehicle will get.
     */
    public void setColor(Color clr) {
        color = clr;
    }

    /**
     * Method for starting the vehicle by giving it a starting speed.
     */
    public void startEngine() {
        if (!isLoadedOnACarrier) {
            currentSpeed = 0.1;
        }
    }

    /**
     * Increases the speed of the vehicle with the given amount from the gas-method.
     * The increase of speed is determined by adding a speedfactor (in conjuction with the amount) to the current speed.
     * The speedfactor is calculated through another method, specific for each type of vehicle.
     *
     * @param amount how much the cars speed will increase, given by gas-method.
     */
    private void incrementSpeed(double amount) {
        setCurrentSpeed(Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower()));
    }

    /**
     * Decreases the speed of the vehicle with the given amount from the brake-method.
     * The decrease of speed is determined by subtracting a speedfactor (in conjuction with the amount) to the current speed.
     * The speedfactor is calculated through another method, specific for each type of vehicle.
     *
     * @param amount how much the cars speed will decrease, given by brake-method.
     */
    private void decrementSpeed(double amount) {
        setCurrentSpeed(Math.max(getCurrentSpeed() - speedFactor() * amount, 0));
    }

    /**
     * Increases the speed of the vehicle, by calling the incrementSpeed method. The vehicle can not be loaded
     * onto a carrier for this to work.
     *
     * @param amount parameter that determines how much the vehicle
     * will increase its speed. Essentially the gaspedal.
     */
    public void gas(double amount) {
        if (!(amount < 0 || amount > 1) && !isLoadedOnACarrier) {
            incrementSpeed(amount);
        }
    }

    /**
     * Decreases the speed of the vehicle, by calling the decrementSpeed method.
     *
     * @param amount parameter that determines how much the vehicle
     *               will decrease its speed. Essentially the breakpedal.
     */
    public void brake(double amount) {
        if (!(amount < 0 || amount > 1)) {
            decrementSpeed(amount);
        }
    }

    /**
     * Moves the vehicle. The distance it's moved is determined by
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
     * Turns the vehicle to the left (rotates the vehicle 90 degrees to the left).
     */
    @Override
    public void turnLeft() {
        currentDirection = findNextDirection(directionArrayLeft);
    }

    /**
     * Turns the vehicle to the right (rotates the vehicle 90 degrees to the right).
     */
    @Override
    public void turnRight() {
        currentDirection = findNextDirection(directionArrayRight);
    }

    /**
     * Determines which direction is next, is only called internally
     * by other methods in class, more specifically turnLeft and turnRight.
     *
     * @param dirArr should receive an array containing all the possible directions,
     *               in which the element after the current direction will be the next direction
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
     *
     * @return calculates a value based on the properties defined in each vehicle.
     */
    protected abstract double speedFactor();


}
