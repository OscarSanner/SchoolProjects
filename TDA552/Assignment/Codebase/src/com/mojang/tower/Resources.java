package com.mojang.tower;

public class Resources {
    //Could be enum?
    public static final int WOOD = 0;
    public static final int ROCK = 1;
    public static final int FOOD = 2;
    //Entry values.
    public int wood = 100;
    public int rock = 100;
    public int food = 100;

    //Keeps track of all the current resources. Adds to the counter.
    public void add(int resourceId, int count) {
        switch (resourceId) {
            case WOOD:
                wood += count;
                break;
            case ROCK:
                rock += count;
                break;
            case FOOD:
                food += count;
                break;
        }
    }

    //Reduces the int. Used when building.
    public void charge(HouseType type) {
        wood -= type.wood;
        rock -= type.rock;
        food -= type.food;
    }

    //Self explanatory.
    public boolean canAfford(HouseType type) {
        if (wood < type.wood) return false;
        if (rock < type.rock) return false;
        if (food < type.food) return false;
        return true;
    }
}
