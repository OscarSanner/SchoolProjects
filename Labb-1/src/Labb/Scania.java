package Labb;

import java.awt.*;

public class Scania extends Truck {

    private Flatbed flatbed = new AngledFlatbed();

    public Scania() {
        super(2, Color.WHITE, 300, "Scania");
    }

    public void raiseFlatbed(){
        if(getCurrentSpeed() == 0){
            flatbed.raiseFlatbed();
        }
    }

    public void lowerFlatbed(){
            flatbed.lowerFlatbed();
    }

    @Override
    public void move(){
        if (flatbed.getCurrentAngle() == flatbed.getMinAngle()){
            super.move();
        }
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.003;
    }
}
