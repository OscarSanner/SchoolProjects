package Labb;

public class Main {

    public static void main(String[] args) {new Main().program();}

    void program(){

        Workshop<Saab95> w = new Workshop<>();
        w.loadCar(new Saab95());
        System.out.println(w.getLoadedCars());


        //For run/compile check.

        /*

        Skapade en avståndschecker för att kolla att bil som skall laddas på flatbed är i rätt avstånd (valde gotyckliga avståndet 2(meter))
        Gjorde temporära setters för x/y till vehicle för att kunna loada/unloada bilar efter att de har lastats/avlastats.
         -> loadCar och unloadCar
         -> uppdaterade move i carTransporter för att move:a bilar som är lastade också


         Ändrade typen hos StateFlatbed till generisk som extendar Vehicle, satte även i konstruktorn för CarTransporter att dess generiska typ är <Car>.



           ok scratch allt vi skrivit,
           total ombyggnad av loadcars, se UML
           fortfarande säkring av getX/getY m.m.








         */
    }
}
