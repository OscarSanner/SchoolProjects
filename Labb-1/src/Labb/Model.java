package Labb;

import java.util.ArrayList;
import java.util.List;

public class Model {

    List<MotorizedVehicle> vehicles;

    public Model(){
        this.vehicles = vehicleFactory();
    }

    private List<MotorizedVehicle> vehicleFactory() {
        List<MotorizedVehicle> vehicles = new ArrayList<>();
        vehicles.add(volvo240Factory(0));
        vehicles.add(saab95Factory(100));
        vehicles.add(scaniaFactory(200));
        vehicles.add(volvo240Factory(300));
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

}
