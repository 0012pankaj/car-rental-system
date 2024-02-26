import java.util.*;

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private int pass=1234;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public int totalcar() {
        return cars.size();
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {

        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            car.returnCar();

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {

        Scanner scanner = new Scanner(System.in);
        String bold = "\u001B[1m";
        // String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        // String ANSI_WHITE = "\u001B[37m";
        int n = 0;
        System.out.println("____________________________________________________________________________________________");
        System.out.println(ANSI_GREEN + bold + "===== Car Rental System =====");
        System.out.println(ANSI_PURPLE +"===== Login  =====");
        System.out.print(ANSI_CYAN + "Enter Pass Code:");
        n = scanner.nextInt();

        if (n == pass) {
            while (true) {
                System.out.println();
                System.out.println("____________________________________________________________________________________________");
                System.out.println();
                System.out.println(ANSI_GREEN + bold + "===== Car Rental System =====");
                System.out.println(ANSI_YELLOW + "0. Add New Car");
                System.out.println(ANSI_YELLOW + "1. Rent a Car");
                System.out.println(ANSI_YELLOW + "2. Return a Car");
                System.out.println(ANSI_YELLOW + "3. Exit");
                System.out.print(ANSI_BLUE + "Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    System.out.println("____________________________________________________________________________________________");

                    System.out.println(ANSI_GREEN + "\n== Rent a Car ==\n");
                    System.out.print(ANSI_BLUE + "Enter your name: ");
                    String customerName = scanner.nextLine();

                    System.out.println(ANSI_GREEN + "\nAvailable Cars:");
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            System.out
                                    .println(ANSI_YELLOW + car.getCarId() + " - " + car.getBrand() + " "
                                            + car.getModel());
                        }
                    }

                    System.out.print(ANSI_BLUE + "\nEnter the car ID you want to rent: ");
                    String carId = scanner.nextLine();

                    System.out.print(ANSI_BLUE + "Enter the number of days for rental: ");
                    int rentalDays = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && car.isAvailable()) {
                            selectedCar = car;
                            break;
                        }
                    }

                    if (selectedCar != null) {
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.println(ANSI_GREEN + "\n----------Rental Information----------\n");
                        System.out.println(ANSI_YELLOW + "Customer ID: " + newCustomer.getCustomerId());
                        System.out.println(ANSI_YELLOW + "Customer Name: " + newCustomer.getName());
                        System.out
                                .println(ANSI_YELLOW + "Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                        System.out.println(ANSI_YELLOW + "Rental Days: " + rentalDays);
                        System.out.printf(ANSI_CYAN + "Total Price: $%.2f%n", totalPrice);
                        System.out.println(ANSI_GREEN + "\n---------------------------------------\n");

                        System.out.print(ANSI_BLUE + "\nConfirm rental (Y/N): ");
                        String confirm = scanner.nextLine();

                        if (confirm.equalsIgnoreCase("Y")) {
                            rentCar(selectedCar, newCustomer, rentalDays);
                            System.out.println(ANSI_GREEN + "\nCar rented successfully.");
                        } else {
                            System.out.println(ANSI_RED + "\nRental canceled.");
                        }
                    } else {
                        System.out.println(ANSI_RED + "\nInvalid car selection or car not available for rent.");
                    }
                } else if (choice == 2) {
                    System.out.println("____________________________________________________________________________________________");

                    System.out.println(ANSI_GREEN + "\n== Return a Car ==\n");
                    System.out.print(ANSI_BLUE + "Enter the car ID you want to return: ");
                    String carId = scanner.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getCar() == carToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }

                        if (customer != null) {
                            returnCar(carToReturn);
                            System.out.println(ANSI_GREEN + "Car returned successfully by " + customer.getName());
                        } else {
                            System.out.println(ANSI_RED + "Car was not rented or rental information is missing.");
                        }
                    } else {
                        System.out.println(ANSI_RED + "Invalid car ID or car is not rented.");
                    }
                } else if (choice == 0) {
                    System.out.println("____________________________________________________________________________________________");

                    System.out.print(ANSI_CYAN + "WANT TO ADD CAR IN SYSTEM (y/n):");
                    String ans = scanner.nextLine();

                    if (ans.equalsIgnoreCase("Y")) {

                        System.out.println();
                        System.out.println(ANSI_GREEN + "===== Car Rental System (Admin Panel)=====");
                        System.out.print(ANSI_BLUE + "Enter Car Name(Model): ");
                        String name = scanner.next();
                        System.out.print(ANSI_BLUE + "Enter Car Brand: ");
                        String brand = scanner.next();
                        System.out.print(ANSI_BLUE + "Enter Base Price Per Day : ");
                        double bpd = scanner.nextDouble();
                        Car newcar = new Car("C" + (totalcar() + 1), brand, name, bpd);
                        addCar(newcar);
                        System.out.print(ANSI_YELLOW + "Car Added Succesfully");

                    }
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println(ANSI_GREEN + "Invalid choice. Please enter a valid option.");
                }
            }
        } else {
            System.out.print(ANSI_RED + "Your are not Valid User !! ");
        }

        System.out.println(ANSI_CYAN + "\nThank you for using the Car Rental System!");

        scanner.close();
    }

}
