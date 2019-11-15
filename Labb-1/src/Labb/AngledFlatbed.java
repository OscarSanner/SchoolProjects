package Labb;

public class AngledFlatbed extends Flatbed {

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
}
