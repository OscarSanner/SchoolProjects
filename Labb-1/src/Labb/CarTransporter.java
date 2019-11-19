package Labb;

import java.awt.*;

public class CarTransporter extends Truck {

    private StateFlatbed flatbed = new StateFlatbed();
    private boolean initRollout;

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
            for (Car ch : flatbed.getLoadedCars()){
                updateWithCarrier();
            }
        }
    }

    public void addCar(Car car){
        boolean closeEnough = car.getX() - this.getX() < 2 && car.getY() - this.getY() < 2;
        if(flatbed.getCurrentAngle() == flatbed.getMinAngle() && closeEnough){
            car.stopEngine();
            flatbed.loadCar(car);
            car.beingTransported(this);
        }
    }

    public void removeCar(Car car){
        if(flatbed.getCurrentAngle() == flatbed.getMinAngle()){
            flatbed.deloadCar(car);
            initRollout = true;
            car.rollOut(this);
            car.noLongerBeingTransported(this);
        }
    }

    public boolean confirmTransporting(Car car){
        if (flatbed.getLoadedCars().contains(car)){
            return true;
        }
        return false;
    }

    public boolean confirmRollout(){
        if(initRollout = true){
            initRollout = false;
            return true;
        }
        return false;
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.003;
    }
}
