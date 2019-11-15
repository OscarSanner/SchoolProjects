package Labb;

import java.awt.*;

public abstract class Car extends Vehicle{

    boolean isLoadedOnOtherVehicle = false;
    StateFlatbed carrier;

    public Car(int nrDoors, Color color, double enginePower, String modelName) {
        super(nrDoors, color, enginePower, modelName);
    }

    @Override
    public void move(){
        if (isLoadedOnOtherVehicle){

        }
    }


}
