package Labb;

public class Flatbed {
    static final private int minAngle = 0;
    static final private int maxAngle = 70;


    private int currentAngle;

    public Flatbed(){
        currentAngle = 0;
    }

    public void lowerFlatbed(){
        if(currentAngle != minAngle){
            setCurrentAngle(getCurrentAngle() - 1);
        }
    }

    public void raiseFlatbed(double currentSpeed){
        if(currentAngle != maxAngle && currentSpeed == 0){
            setCurrentAngle(getCurrentAngle() + 1);
        }
    }

    public int getCurrentAngle() {
        return currentAngle;
    }

    private void setCurrentAngle(int currentAngle) {
        this.currentAngle = currentAngle;
    }

}
