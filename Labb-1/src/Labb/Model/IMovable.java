package Labb.Model;

/**
 * Interface for movable objects.
 */
interface IMovable {
    /**
     * Moves the movable object, under the right conditions.
     */
    void move();

    /**
     * Turns the movable object left.
     */
    void turnLeft();

    /**
     * Turns the movable object right.
     */
    void turnRight();
}
