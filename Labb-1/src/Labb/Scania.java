package Labb;

import java.awt.*;

public class Scania extends Car{

    private Flatbed flatbed = new Flatbed();

    public Scania() {
        super(2, Color.WHITE, 60, "Scania");
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }

    @Override
    public void move(){
        if (flatbed.getCurrentAngle() == 0){
        }
    }

}
