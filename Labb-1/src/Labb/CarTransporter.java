package Labb;

import java.awt.*;

public class CarTransporter extends Truck {

    private StateFlatbed flatbed = new StateFlatbed();

    public CarTransporter() {
        super(2, Color.YELLOW, 300, "Car Transporter");
    }

    public void raiseFlatbed(){
        flatbed.raiseFlatbed();
    }

    public void lowerFlatbed(){
        if(this.getCurrentSpeed() == 0){
            flatbed.lowerFlatbed();
        }
    }

    @Override
    public void move(){
        if (flatbed.getCurrentAngle() == flatbed.getMaxAngle()){
            super.move();
        }
        for(Car c : flatbed.loadedCars){
            c.syncroniseWithCarrier(this);
        }
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.003;
    }
}
