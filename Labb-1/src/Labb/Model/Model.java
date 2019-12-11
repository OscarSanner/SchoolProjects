package Labb.Model;

import Labb.IObservable;
import Labb.IObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Model implements IObservable {

    List<IObserver> observers;
    List<MotorizedVehicle> vehicles;
    final int WIDTH;
    final int HEIGHT;
    private final int delay;
    public Timer timer;

    public Model(int width, int height){
        this.vehicles = vehicleFactory();
        this.HEIGHT = height;
        this.WIDTH = width;
        observers = new ArrayList<>();
        delay = 10;
        timer = new Timer(delay, new TimerListener());

    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void update(){
        for(MotorizedVehicle motorizedVehicle: vehicles){
            motorizedVehicle.move();
            if(collision(motorizedVehicle)){
                motorizedVehicle.turnLeft();
                motorizedVehicle.turnLeft();
            }
        }
        notifyAllObservers();
    }

    private boolean collision(MotorizedVehicle motorizedVehicle){
        return (motorizedVehicle.getX() > WIDTH -  motorizedVehicle.getIcon().getWidth()    ||
                motorizedVehicle.getX() < 0 ||
                motorizedVehicle.getY() > HEIGHT -  motorizedVehicle.getIcon().getHeight()  ||
                motorizedVehicle.getY() < 0);
    }

    public void gas(int amount){
        double gas = ((double) amount) / 100;
        for (MotorizedVehicle vehicle : vehicles){
            vehicle.gas(gas);
        }
    }

    public void brake(int amount){
        double brake = ((double) amount) / 100;
        for (MotorizedVehicle vehicle : vehicles){
            vehicle.brake(brake);
        }
    }

    public void setTurboOn(){
        for (MotorizedVehicle vehicle : vehicles){
            if (vehicle.getModelName().equals("Saab95")){
                Saab95 saab = (Saab95) vehicle;
                saab.setTurboOn();
            }
        }
    }

    public void setTurboOff(){
        for (MotorizedVehicle vehicle : vehicles){
            if (vehicle.getModelName().equals("Saab95")){
                Saab95 saab = (Saab95) vehicle;
                saab.setTurboOff();
            }
        }
    }

    public void raiseFlatbed(){
        for (MotorizedVehicle vehicle : vehicles){
            if (vehicle.getModelName().equals("Scania")){
                Scania scania = (Scania) vehicle;
                scania.raiseFlatbed();
            }
        }
    }

    public void lowerFlatbed(){
        for (MotorizedVehicle vehicle : vehicles){
            if (vehicle.getModelName().equals("Scania")){
                Scania scania = (Scania) vehicle;
                scania.lowerFlatbed();
            }
        }
    }

    public void startEngine(){
        for (MotorizedVehicle vehicle : vehicles){
            vehicle.startEngine();
        }
    }

    public void stopEngine(){
        for (MotorizedVehicle vehicle : vehicles){
            vehicle.stopEngine();
        }
    }

    public ArrayList<IDrawable> getDrawables(){
        return new ArrayList<IDrawable>(vehicles);
    }

    private List<MotorizedVehicle> vehicleFactory() {
        List<MotorizedVehicle> vehicles = new ArrayList<>();
        //vehicles.add(volvo240Factory(0));
        vehicles.add(saab95Factory(100));
        vehicles.add(saab95Factory(200));
        vehicles.add(saab95Factory(300));
        vehicles.add(saab95Factory(400));
        //vehicles.add(scaniaFactory(200));
        //vehicles.add(volvo240Factory(300));
        return vehicles;
    }

    private Scania scaniaFactory(int y) {
        return new Scania(0, y);
    }
    private Saab95 saab95Factory(int y) {
        return new Saab95(0, y);
    }
    private Volvo240 volvo240Factory(int y) {
        return new Volvo240(0, y);
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (IObserver observer : observers){
            observer.observerUpdate();
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            update();
            // repaint() calls the paintComponent method of the panel
        }
    }
}
