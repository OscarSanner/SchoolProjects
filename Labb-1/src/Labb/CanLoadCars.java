package Labb;

import java.util.Deque;

public interface CanLoadCars<T extends Vehicle>  {
    public Deque<T> getLoadedCars();
    public void loadCar(T carToBeLoaded, Vehicle loadedOnTo);
    public void unloadCar();
    public boolean loadCheck(T vehicleToBeLoaded, Vehicle loadedOnTo);
}
