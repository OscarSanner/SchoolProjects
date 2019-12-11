package Labb.View;

import Labb.IObserver;
import Labb.Model.IDrawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


// This panel represent the animated part of the view with the car images.

public class DrawPanel extends JPanel implements IObserver {

    List<IDrawable> drawableList;

    // To keep track of a single motorizedVehicles position

    // TODO: Make this general for all motorizedVehicles

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, ArrayList<IDrawable> drawableList) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.pink);
        this.drawableList = drawableList;

    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (IDrawable drawable : drawableList) {
            g.drawImage(drawable.getIcon(), (int)drawable.getX(), (int)drawable.getY(),null);
        }
    }

    @Override
    public void observerUpdate() {
        this.repaint();
    }
}
