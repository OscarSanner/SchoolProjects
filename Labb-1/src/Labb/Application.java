package Labb;

import Labb.Model.Model;
import Labb.View.CarView;

public class Application {
    public static void main(String[] args) {
        Model model = new Model(800, 800);
        CarView view = new CarView("Car simulator 1.1", model);
        CarController controller = new CarController(view, model);
    }
}