package Labb;

import java.awt.*;

public class Scania extends Truck {




    public Scania() {
        super(2, Color.WHITE, 60, "Scania");
            
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }

}
