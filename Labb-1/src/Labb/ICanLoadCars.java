package Labb;


public interface ICanLoadCars <T extends Car> {
    public void loadCar(T carToBeLoaded);

    public boolean confirmLoad (MotorizedVehicle motorizedVehicleRequestedToBeLoaded);

    public boolean loadCheck(T vehicleToBeLoaded);

    public double getY();

    public double getX();

}
