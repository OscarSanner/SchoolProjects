package Labb;

public class Main {

    public static void main(String[] args) {new Main().program();}

    void program(){
        Volvo240 v1 = new Volvo240();
        Saab95 s1 = new Saab95();
        System.out.println(v1.getNrDoors());
        System.out.println(v1.getColor());
        System.out.println(s1.getCurrentSpeed());

        s1.setCurrentSpeed(10000);
    }
}
