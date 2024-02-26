import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Scanner sc=new Scanner(System.in);
       
        //default car present
        Car car1 = new Car("C1", "Toyota", "Camry", 60.0); // Different base price per day for each car
        Car car2 = new Car("C2", "Honda", "Accord", 70.0);
        Car car3 = new Car("C3", "Mahindra", "Thar", 150.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        
        

        rentalSystem.menu();
        sc.close();
    }
}
