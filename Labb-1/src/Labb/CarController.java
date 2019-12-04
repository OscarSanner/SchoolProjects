package Labb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 10;
    // The timer is started with an listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of motorizedVehicles, modify if needed
    ArrayList<MotorizedVehicle> motorizedVehicles = new ArrayList<>();

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        cc.motorizedVehicles.add(new Volvo240(0,0));
        cc.motorizedVehicles.add(new Saab95(0,100));
        cc.motorizedVehicles.add(new Scania(0,200));
        cc.motorizedVehicles.add(new Volvo240(0,300));

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Start the timer
        cc.timer.start();
    }

    /* Each step the TimerListener moves all the motorizedVehicles in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (MotorizedVehicle motorizedVehicle : motorizedVehicles) {
                motorizedVehicle.move();

                if(collision(motorizedVehicle)){
                    motorizedVehicle.turnLeft();
                    motorizedVehicle.turnLeft();
                }

                frame.drawPanel.moveit(motorizedVehicle);
                // repaint() calls the paintComponent method of the panel
                frame.drawPanel.repaint();
            }
        }

        private boolean collision(MotorizedVehicle motorizedVehicle){
            return (motorizedVehicle.getX() > frame.drawPanel.getWidth() -  frame.drawPanel.volvoImage.getWidth()  ||
                    motorizedVehicle.getX() < 0 ||
                    motorizedVehicle.getY() > frame.drawPanel.getHeight() - frame.drawPanel.volvoImage.getHeight() ||
                    motorizedVehicle.getY() < 0);
        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (MotorizedVehicle motorizedVehicle : motorizedVehicles
                ) {
            motorizedVehicle.gas(gas);
        }
    }
    // Calls the brake method for each car once
    void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (MotorizedVehicle motorizedVehicle : motorizedVehicles
        ) {
            motorizedVehicle.brake(brake);
        }
    }

}
