package Labb;


import java.util.List;

public interface ICanLoadCars <T extends Car> {
    public void loadCar(T carToBeLoaded);

    public boolean confirmLoad (Vehicle vehicleRequestedToBeLoaded);

    public boolean loadCheck(T vehicleToBeLoaded);

    public double getY();

    public double getX();

}
