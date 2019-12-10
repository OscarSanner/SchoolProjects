package Labb.Model;


/**
 * Interface used to describe a class which can load motorizedVehicles.
 * @param <T> Can be set a specific car or all motorizedVehicles.
 */
interface ICanLoadCars <T extends Car> {

    /**
     * Method for loading a car.
     * @param carToBeLoaded the car in question which is to be loaded on to the entity.
     */
    public void loadCar(T carToBeLoaded);

    /**
     * Method for confirming this ferry is the one asking to load the vehicle in question.
     * @param motorizedVehicleRequestedToBeLoaded the vehicle this entity wants to load, confirming it's actually this ferry.
     * @return true if this entity coincides with the entity the vehicle is wanting to be loaded on, otherwise false.
     */
    public boolean confirmLoad (MotorizedVehicle motorizedVehicleRequestedToBeLoaded);

    /**
     * Method for checking that the car to be loaded is within distance and isn't currently loaded on another entity.
     * @param carToBeLoaded car trying to be loaded.
     * @return true if car isn't currently loaded and is close enough to the entity.
     */
    public boolean loadCheck(T carToBeLoaded);

    /**
     * Method for getting y-cooridnate.
     * @return the y-coordinate.
     */
    public double getY();

    /**
     * Method for getting x-cooridnate.
     * @return the x-coordinate.
     */
    public double getX();

}
