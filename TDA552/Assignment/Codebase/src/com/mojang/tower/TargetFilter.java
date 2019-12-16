package com.mojang.tower;

/**
 * This class is ran when something has to set it's target. Interesting way of achieving this. Each time a new targetFilter
 * is created, the method "accepts" is overridden to return true when the target if valid. IE if your looking to target a monster
 * you would rewrite the method to return true if e=monster. Sort of. No real comment on this.
 */
public class TargetFilter
{
    public boolean accepts(Entity e)
    {
        return true;
    }
}
