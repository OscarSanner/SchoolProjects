package com.mojang.tower;

import java.awt.Graphics2D;

public class FarmPlot extends Entity {
    public static final int GROW_SPEED = 200;
    private int age;
    private int stamina = 0;
    private int yield = 0; //unused

    public FarmPlot(double x, double y, int age) {
        super(x, y, 0);
        this.yield = this.stamina = this.age = age;
    }

    //Max growth appears to be 7*Grow speed
    public void tick() {
        if (age < 7 * GROW_SPEED) {
            age++;
            stamina++;
            yield++;
        }
    }

    //TODO: Each entity appears to be responsible for it's own rendering. It draws itself at it's x and y position. This is a major violation of SOLID.
    //TODO: Furthermore, the rendering is called to by TowerComponents render, while traversing it's list of entities.
    //TODO: A fix for this would be to simply have TowerComponent fetch X,Y and bitmaps. Increasing extensibility.
    public void render(Graphics2D g, double alpha) {
        int x = (int) (xr - 4);
        int y = -(int) (yr / 2 + 5);

        g.drawImage(bitmaps.farmPlots[7 - age / GROW_SPEED], x, y, null);
    }

    //Adds to the list of unused methods.
    public void cut() {
        alive = false;
    }

    //The wheat appears to have life. When you gather wheat you kill it. Not really true.
    public boolean gatherResource(int resourceId) {
        stamina -= 64;
        if (stamina <= 0) {
            alive = false;
            return true;
        }
        return false;
    }

    public int getAge() {
        return age / GROW_SPEED;
    }

    //Override to empty method. Left gray as this has already been addressed in superclass.
    public boolean givesResource(int resourceId) {
        return getAge() > 6 && resourceId == Resources.FOOD;
    }

}