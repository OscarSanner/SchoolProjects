package Labb;

import java.awt.*;

/**
 * Class that represents a specific truck called Scania.
 */
public class Scania extends Truck {

    /**
     * The flatbed on the Scania.
     */
    private Flatbed flatbed = new AngledFlatbed();

    /**
     * Constructor for Scania, initiates the properties for a scania using the constructor in the superclass Truck and in turn MotorizedVehicle.
     */
    public Scania(double x, double y) {
        super(2, Color.WHITE, 300, "Scania", x, y);
    }

    /**
     * Method for raising the flatbed.
     * Can only be done if the Scania is not moving.
     */
    public void raiseFlatbed(){
        if(getCurrentSpeed() == 0){
            flatbed.raiseFlatbed();
        }
    }

    /**
     * Method for lowering the flatbed.
     */
    public void lowerFlatbed(){
            flatbed.lowerFlatbed();
    }

    /**
     * Method for getting the current angle of the flatbed on the Scania.
     * @return the angle of the flatbed.
     */
    public int getCurrentAngle(){
        return flatbed.currentAngle;
    }

    /**
     * Overrides the move-method in MotorizedVehicle to add extra conditions for movement.
     * Moves only when the flatbed is lowered.
     */
    @Override
    public void move(){
        if (flatbed.getCurrentAngle() == flatbed.getMinAngle()){
            super.move();
        }
    }

    /**
     * Method used by methods in MotorizedVehicle for increasing and decreasing speed.
     * @return calculates a factor based on the engine power.
     */
    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.003;
    }
}
