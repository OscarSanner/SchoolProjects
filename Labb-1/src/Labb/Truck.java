package Labb;

import java.awt.*;

public abstract class Truck extends MotorizedVehicle {


    public Truck(int nrDoors, Color color, double enginePower, String modelName) {
        super(nrDoors, color, enginePower, modelName);
    }

    public abstract int getCurrentAngle();


}
