package Labb;

/**
 * Abstract class for common properties of all flatbeds.
 */
public abstract class Flatbed {

    /**
     *
     */
    final protected int minAngle;
    final protected int maxAngle;
    protected int currentAngle;


    public Flatbed(int minAngle, int maxAngle, int currentAngle) {
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.currentAngle = currentAngle;
    }

    public void lowerFlatbed(){
        if(currentAngle != minAngle){
            this.currentAngle -= 1;
        }
    }

    public void raiseFlatbed(){
        if(currentAngle != maxAngle){
            this.currentAngle += 1;
        }
    }

    public int getCurrentAngle() {
        return currentAngle;
    }
    public int getMinAngle() {
        return minAngle;
    }
    public int getMaxAngle() {
        return maxAngle;
    }
}
