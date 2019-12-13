package Labb.Model;

/**
 * Abstract class for common properties of all flatbeds.
 */
abstract class Flatbed {

    /**
     * The lowest angle the flatbed can have, set by constructor.
     */
    final protected int minAngle;

    /**
     * The max angle the flatbed can have, set by constructor.
     */
    final protected int maxAngle;

    /**
     * The current angle of the flatbed, set by constructor.
     */
    protected int currentAngle;

    /**
     * Constructor for common properties for a flatbed.
     * @param minAngle The lowest angle the flatbed can have, specific for each type of flatbed.
     * @param maxAngle The max angle the flatbed can have, specific for each type of flatbed.
     * @param currentAngle The current angle of the flatbed, set by each specific type of flatbed.
     */
    public Flatbed(int minAngle, int maxAngle, int currentAngle) {
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.currentAngle = currentAngle;
    }

    /**
     * Method for lowering the flatbed.
     */
    public void lowerFlatbed(){
        if(currentAngle != minAngle){
            this.currentAngle -= 1;
        }
    }

    /**
     * Method for raising the flatbed.
     */
    public void raiseFlatbed(){
        if(currentAngle != maxAngle){
            this.currentAngle += 1;
        }
    }

    /**
     * Method for getting the current angle of the flatbed.
     * @return the current angle of the flatbed.
     */
    public int getCurrentAngle() {
        return currentAngle;
    }

    /**
     * Method for getting the minimum angle of the flatbed.
     * @return the minimum angle of the flatbed.
     */
    public int getMinAngle() {
        return minAngle;
    }

    /**
     * Method for getting the minimum angle of the flatbed.
     * @return the minimum angle of the flatbed.
     */
    public int getMaxAngle() {
        return maxAngle;
    }
}
