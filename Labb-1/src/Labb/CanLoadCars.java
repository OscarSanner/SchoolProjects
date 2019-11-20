package Labb;

import java.util.Deque;

public interface CanLoadCars<T extends Vehicle>  {
    public Deque<T> getLoadedCars();
    public void loadCar(T carToBeLoaded);
    public void unloadFirstCar();
    public void unloadLastCar();
}
