package Labb.Model;

import Labb.View.DrawPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class that represents a specific car called volvo240.
 */
class Volvo240 extends Car {

    private final static double trimFactor = 1.25;
    BufferedImage icon;


    /**
     * Constructor for initialising variables declared in MotorizedVehicle/Car (super class).
     */
    public Volvo240(double x, double y) {
        super(4, Color.black, 100, "Volvo240", x, y);
        try{icon = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/Volvo240.jpg"));
        }catch (IOException ex)
        {
            ex.printStackTrace();
            icon = null;
        }
    }

    /**
     * Method used by methods in MotorizedVehicle for increasing and decreasing speed.
     * @return calculates a factor based on the engine power and the trimfactor.
     */
    protected double speedFactor() {
        return getEnginePower() * 0.01 * trimFactor;
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }
}

