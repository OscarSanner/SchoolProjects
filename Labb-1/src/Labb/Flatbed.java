package Labb;

public abstract class Flatbed {
    static final protected int minAngle = 0;
    static final protected int maxAngle = 70;

    protected int currentAngle;

    public abstract void raiseFlatbed();
    public abstract void lowerFlatbed();

    public int getCurrentAngle() {
        return currentAngle;
    }

}
