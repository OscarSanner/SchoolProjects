package Labb;

public class Main {

    public static void main(String[] args) {new Main().program();}

    void program(){
        //For run/compile check.

        Volvo240 c1 = new Volvo240();
        Volvo240 c2 = new Volvo240();
        Workshop<Volvo240> v1 = new Workshop<>(1, 1, 5);
        Workshop<Volvo240> v2 = new Workshop<>(2, 2, 5);
        v1.loadCar(c1);
        v2.loadCar(c2);

        v2.loadCar(c1);
        System.out.println(c1.getX() + " . " + c2.getX());
    }
}
