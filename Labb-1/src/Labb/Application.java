package Labb;

import Labb.Model.Model;
import Labb.View.CarView;
import Labb.View.SpeedPanel;

public class Application {
    public static void main(String[] args) {
        Model model = new Model(800, 1000);
        SpeedPanel speedPanel = new SpeedPanel(model);
        CarView view = new CarView("Car simulator 1.1", model);
        CarController controller = new CarController(view, model);
        model.timer.start();
    }
}