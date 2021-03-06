package com.mojang.tower;

import java.awt.Graphics2D;

//TODO: Has a lot in common with other resources. Farmplot and tree. Could be a super class.
public class Rock extends Entity
{
    private int type = 0;
    private int stamina = 5000;
    private int life = 16;
    
    public Rock(double x, double y)
    {
        super(x, y, 5);
        type = random.nextInt(4);
    }

    //TODO: Doesn't have to override as tick exists in super.
    public void tick()
    {
    }

    public void render(Graphics2D g, double alpha)
    {
        int x = (int) (xr - 4);
        int y = -(int) (yr / 2 + 8-2);

        g.drawImage(bitmaps.rocks[type], x, y, null);
    }

    //Identical to FarmPlot.
    public boolean gatherResource(int resourceId)
    {
        stamina -= 64;
        if (stamina <= 0)
        {
            stamina+=5000;
            if (--life==0) alive = false;
            return true;
        }
        return false;
    }
    
    public boolean givesResource(int resourceId)
    {
        return resourceId==Resources.ROCK;
    }
    
}