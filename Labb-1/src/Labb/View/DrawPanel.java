package Labb.View;

import Labb.IObserver;
import Labb.Model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


// This panel represent the animated part of the view with the car images.

public class DrawPanel extends JPanel implements IObserver {

    Model model;
    // To keep track of a single motorizedVehicles position

    // TODO: Make this general for all motorizedVehicles

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, Model model) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.pink);
        this.model = model;
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (IDrawable drawable : model.getDrawables()) {
            g.drawImage(drawable.getIcon(), (int)drawable.getX(), (int)drawable.getY(),null);
        }
    }

    @Override
    public void observerUpdate() {
        this.repaint();
    }
}
