package Labb;

import java.awt.*;

public abstract class Truck extends Vehicle {

    Flatbed flatbed;

    public Truck(int nrDoors, Color color, double enginePower, String modelName) {
        super(nrDoors, color, enginePower, modelName);
    }

    @Override
    public void move(){
        if (flatbed.getCurrentAngle() == 0){
            super.move();
        }
    }
    public void raiseFlatbed(){
        flatbed.raiseFlatbed();
    }

    public void lowerFlatbed(){
        flatbed.lowerFlatbed();
    }

    public void setFlatbed(Flatbed flatbed) {
        this.flatbed = flatbed;
    }
}
