package Labb.Model;

import Labb.View.DrawPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *  Class that represents a specific car called Saab95.
 */
class Saab95 extends Car {

    private boolean turboOn;

    /**
     * Constructor for class, initiates hardcoded properties of the car.
     */
    public Saab95(double x, double y) {
        super(2, Color.red, 125, "Saab95", x, y);
        turboOn = false;
    }

    /**
     * Method for turning turbo on.
     */
    public void setTurboOn() {
        turboOn = true;
    }
    /**
     * Method for turning turbo off.
     */
    public void setTurboOff() {
        turboOn = false;
    }

    /**
     * Used in methods where the car accelerates or brakes.
     * @return calculates a factor based on turbo turned on/off.
     */
    protected double speedFactor() {
        double turbo = 1;
        if (turboOn) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }


    @Override
    public BufferedImage getIcon() {
        try{return ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/Saab95.jpg"));
        }catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
