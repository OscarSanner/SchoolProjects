package Labb;

import java.awt.*;
import java.util.Random;

public class Sad extends MotorizedVehicle {


    public Sad(double x, double y) {
        super(0, Color.BLACK, 100, "Sad", x, y);
        this.startEngine();
        setRandomDirection();
    }

    private void setRandomDirection() {
        Random random = new Random();
        int a = random.nextInt(4);
        for(int i = 0; i < a; i++){
            super.turnLeft();
        }
    }

    @Override
    protected double speedFactor() {
        return 0;
    }

    @Override
    public void gas(double amount){
        return;
    }
}
