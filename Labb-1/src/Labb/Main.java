package Labb;

public class Main {

    public static void main(String[] args) {new Main().program();}

    void program(){
        Volvo240 v1 = new Volvo240();
        Saab95 s1 = new Saab95();
        System.out.println(v1.getNrDoors());
        System.out.println(v1.getColor());
        System.out.println(s1.getCurrentSpeed());

        /*
        s1.turnLeft();
        System.out.println(s1.currentDirection);
        s1.turnLeft();
        System.out.println(s1.currentDirection);
        s1.turnLeft();
        System.out.println(s1.currentDirection);
        s1.turnLeft();
        System.out.println(s1.currentDirection);
        s1.turnLeft();
        System.out.println(s1.currentDirection);
        s1.turnLeft();
        System.out.println(s1.currentDirection);
        s1.startEngine();
        System.out.println(s1.getCurrentSpeed() + " " + s1.x + " " + s1.y);
        s1.move();
        System.out.println(s1.getCurrentSpeed() + " " + s1.x + " " + s1.y);
        s1.move();
        System.out.println(s1.getCurrentSpeed() + " " + s1.x + " " + s1.y);
        s1.setTurboOn();
        s1.move();
        System.out.println(s1.getCurrentSpeed() + " " + s1.x + " " + s1.y);


         */

    }
}
