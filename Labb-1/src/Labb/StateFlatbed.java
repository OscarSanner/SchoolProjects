package Labb;

public class StateFlatbed extends Flatbed {

    public void lowerFlatbed(){
        if(currentAngle == maxAngle){
            this.currentAngle = minAngle;
        }
    }

    public void raiseFlatbed(){
        if(currentAngle == minAngle){
            this.currentAngle = maxAngle;
        }
    }
}
