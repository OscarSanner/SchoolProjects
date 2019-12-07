package Labb;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;


// This panel represent the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    // Just a single image, TODO: Generalize
    BufferedImage volvoImage;
    BufferedImage saabImage;
    BufferedImage scaniaImage;
    BufferedImage crash;
    BufferedImage sadImage;
    boolean crashHasHappened;
    // To keep track of a singel motorizedVehicles position

    Map<MotorizedVehicle, Point> vehiclePointMap;

    // TODO: Make this genereal for all motorizedVehicles

    void moveit(MotorizedVehicle motorizedVehicle) {
        Point temp = vehiclePointMap.get(motorizedVehicle);
        temp.x = (int) Math.round(motorizedVehicle.getX());
        temp.y = (int) Math.round(motorizedVehicle.getY());
    }

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, ArrayList<MotorizedVehicle> motorizedVehicles) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.pink);
        initiateVehiclePointMap(motorizedVehicles);

        // Print an error message in case file is not found with a try/catch block
        try {
            // You can remove the "pics" part if running outside of IntelliJ and
            // everything is in the same main folder.
            // volvoImage = ImageIO.read(new File("Volvo240.jpg"));

            // Rememember to rightclick src New -> Package -> name: pics -> MOVE *.jpg to pics.
            // if you are starting in IntelliJ.
            volvoImage = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/Volvo240.jpg"));
            saabImage = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/Saab95.jpg"));
            scaniaImage = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/Scania.jpg"));
            crash = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/Crash.jpg"));
            sadImage = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/Sad.jpg"));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void initiateVehiclePointMap(ArrayList<MotorizedVehicle> motorizedVehicles) {
        vehiclePointMap = new HashMap<>();
        for (MotorizedVehicle mv : motorizedVehicles){
            vehiclePointMap.put(mv, matchModel(mv));
        }
    }

    public void crashEvent(MotorizedVehicle mv, ArrayList<MotorizedVehicle> list){
        saabImage = crash;
        mv.stopEngine();
        for(MotorizedVehicle m : list){
            if (m.getModelName().equals("Sad")){
                Sad s;
                s = (Sad)m;
                s.setX(mv.getX());
                s.setY(mv.getY());
                s.startEngine();
            }
        }
    }

    private Point matchModel(MotorizedVehicle mv) {
        return new Point((int)mv.getX(), (int)mv.getY());
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Map.Entry<MotorizedVehicle, Point> entry : vehiclePointMap.entrySet()) {
            g.drawImage(matchImage(entry.getKey()), entry.getValue().x, entry.getValue().y , null);
        }
    }

    private BufferedImage matchImage(MotorizedVehicle mv){
        if (mv.getModelName().equals("Volvo240")) {
            return volvoImage;
        }
        if (mv.getModelName().equals("Saab95")) {
            return saabImage;
        }
        if (mv.getModelName().equals("Scania")) {
            return scaniaImage;
        }
        if (mv.getModelName().equals("Sad")){
            return sadImage;
        }
        return null; // WILL NEVER BE REACHED.
    }
}
