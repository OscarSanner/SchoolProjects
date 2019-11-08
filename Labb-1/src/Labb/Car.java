package Labb;

import java.awt.*;

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

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void stopEngine() {
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

    public void setColor(Color clr) {
        color = clr;
    }

    public void startEngine() {
        currentSpeed = 0.1;
    }


    private void incrementSpeed(double amount) {
        setCurrentSpeed(Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower()));
    }

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
