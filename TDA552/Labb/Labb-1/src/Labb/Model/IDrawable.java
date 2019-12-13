package Labb.Model;

import java.awt.image.BufferedImage;

public interface IDrawable {
    double getCurrentSpeed();
    double getX();
    double getY();
    BufferedImage getIcon();
    String getModelName();
}
