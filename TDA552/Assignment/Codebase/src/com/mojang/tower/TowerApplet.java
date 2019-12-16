package com.mojang.tower;

import java.applet.Applet;
import java.awt.BorderLayout;

/**
 * A java applet appears to be a type of main class meant to be ran from a website. This is apparently depreciated.
 * This class does the same thing as the main class, however it seems to do it for websites. This class is never
 * actually used in the program and should thus be ignored.
 */

//TODO: Completely unused class. (WILL NOT BE ADRESSED??)
public class TowerApplet extends Applet
{
    private static final long serialVersionUID = 1L;
    private TowerComponent tower;

    public void init()
    {
        tower = new TowerComponent(getWidth()/2, getHeight()/2);
        this.setLayout(new BorderLayout());
        add(tower, BorderLayout.CENTER);
    }
    
    public void start()
    {
        tower.unpause();
    }
    
    public void stop()
    {
        tower.pause();
    }

    public void destroy()
    {
        tower.stop();
    }
}