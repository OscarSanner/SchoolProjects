package Labb.Model;

class MotorizedVehicleFactory {

    MotorizedVehicle addCar(int yCoordinate, int randomNumber) {
        switch (randomNumber) {
            case (0):
                return scaniaFactory(yCoordinate);
            case (1):
                return saab95Factory(yCoordinate);
            case (2):
                return volvo240Factory(yCoordinate);
            default:
                return null;
        }
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

}
