import java.util.Scanner;

class Movie {
    String title;
    String genre;

    public Movie(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
}

class Customer {
    String name;
    int age;

    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Ticket {
    Movie movie;
    Customer customer;

    public Ticket(Movie movie, Customer customer) {
        this.movie = movie;
        this.customer = customer;
    }

    public boolean isCustomerAllowedToWatch() {
        // Add age restrictions based on genre
        if (movie.genre.equals("Romantic") && customer.age < 15) {
            System.out.println(customer.name + " is not allowed to watch " + movie.title + " due to age restrictions.");
            return false;
        }
        return true;
    }
}

class Cinema {
    Movie[] movies;
    Customer[] customers;
    Ticket[] tickets;
    Customer[] customerQueue;

    int movieCount;
    int customerCount;
    int ticketCount;
    int queueFront;
    int queueRear;

    public Cinema(int maxSize) {
        this.movies = new Movie[maxSize];
        this.customers = new Customer[maxSize];
        this.tickets = new Ticket[maxSize];
        this.customerQueue = new Customer[maxSize];
        this.movieCount = 0;
        this.customerCount = 0;
        this.ticketCount = 0;
        this.queueFront = 0;
        this.queueRear = -1;
    }

    public void addMovie(String title, String genre) {
        Movie movie = new Movie(title, genre);
        movies[movieCount++] = movie;
    }

    public void addMovie(Scanner scanner) {
        char addAnother;
        do {
            System.out.print("Enter movie title: ");
            String title = scanner.nextLine();
            System.out.print("Enter movie genre: ");
            String genre = scanner.nextLine();

            addMovie(title, genre);

            System.out.print("Do you want to add another movie? (Y/N): ");
            addAnother = scanner.nextLine().toUpperCase().charAt(0);

        } while (addAnother == 'Y');
    }

    public void addCustomer(String name, int age) {
        Customer customer = new Customer(name.toLowerCase(), age); // Convert to lowercase
        customers[customerCount++] = customer;
        customerQueue[++queueRear] = customer;
    }

    public void addCustomer(Scanner scanner) {
        char addAnother;
        do {
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            int customerAge = 0;

            // Input validation for age
            while (true) {
                try {
                    System.out.print("Enter customer age: ");
                    customerAge = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer for age.");
                }
            }

            addCustomer(customerName, customerAge);

            System.out.print("Do you want to add another customer? (Y/N): ");
            addAnother = scanner.nextLine().toUpperCase().charAt(0);

        } while (addAnother == 'Y');
    }

    public void displayMovies() {
        System.out.println("Movies in the Cinema:");
        for (int i = 0; i < movieCount; i++) {
            System.out.println(i + 1 + ". " + movies[i].title + " - " + movies[i].genre);
        }
    }

// Inside the Cinema class
public void bookTicket(Scanner scanner) {
    displayMovies(); // Display movies with index numbers

    System.out.print("Enter the index number of the movie: ");
    int movieIndex = 0;

    // Input validation for movie index
    while (true) {
        try {
            movieIndex = Integer.parseInt(scanner.nextLine()) - 1; // Adjust for 0-based index
            if (movieIndex >= 0 && movieIndex < movieCount) {
                break;
            } else {
                System.out.println("Invalid index. Please enter a valid index number.");
                System.out.print("Enter the index number of the movie: ");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer for the index.");
            System.out.print("Enter the index number of the movie: ");
        }
    }

    System.out.print("Enter customer name: ");
    String customerName = scanner.nextLine().toLowerCase(); // Convert to lowercase

    Movie selectedMovie = movies[movieIndex];
    int customerIndex = findCustomerIndex(customerName);

    if (customerIndex != -1) {
        Customer selectedCustomer = customerQueue[customerIndex];
        Ticket ticket = new Ticket(selectedMovie, selectedCustomer);

        // Check age restriction before booking the ticket
        if (ticket.isCustomerAllowedToWatch()) {
            tickets[ticketCount++] = ticket;
            System.out.println("Ticket booked successfully for " + selectedCustomer.name + " - " + selectedMovie.title);
        }

        // Ask whether to book tickets for another customer
        System.out.print("Do you want to book tickets for another customer? (Y/N): ");
        char bookAnother = scanner.nextLine().toUpperCase().charAt(0);

        if (bookAnother == 'Y') {
            bookTicket(scanner);
        }
    } else {
        System.out.println("Invalid customer name.");
    }
}



    public void displayCustomerQueue() {
        System.out.println("Customer Queue:");
        for (int i = queueFront; i <= queueRear; i++) {
            System.out.println(i + ". " + customerQueue[i].name + " - " + customerQueue[i].age);
        }
    }

    public void searchIndexBeforeBooking(Scanner scanner) {
        System.out.print("Enter customer name to search for index: ");
        String customerName = scanner.nextLine();
        int customerIndex = findCustomerIndex(customerName);
    
        if (customerIndex != -1) {
            System.out.println("Customer found at index: " + customerIndex);
        } else {
            System.out.println("Customer not found.");
        }
    }
    

    // private Movie findMovieByTitle(String title) {
    //     for (int i = 0; i < movieCount; i++) {
    //         if (movies[i].title.equals(title)) {
    //             return movies[i];
    //         }
    //     }
    //     return null;
    // }

    private int findCustomerIndex(String customerName) {
        for (int i = queueFront; i <= queueRear; i++) {
            if (customerQueue[i].name.equals(customerName)) {
                return i;
            }
        }
        return -1; // Customer not found in the queue
    }
}



public class CinemaManagementSystem {
    public static void main(String[] args) {
        Cinema cinema = new Cinema(100); // Adjust the size based on your needs
        
         // Start the JavaFX GUI
       CinemaManagementSystemGUI .launch(CinemaManagementSystemGUI.class, args);
        
        Scanner scanner = new Scanner(System.in);
        

        while (true) {
            System.out.println("1. Add Movie");
            System.out.println("2. Add Customer");
            System.out.println("3. Display Movies");
            System.out.println("4. Display Customer Queue");
            System.out.println("5. Search Index Before Booking");
            System.out.println("6. Book Ticket");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = 0;

            // Input validation for choice
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer for choice.");
                    System.out.print("Enter your choice: ");
                }
            }

            switch (choice) {
                case 1:
                    cinema.addMovie(scanner);
                    break;

                case 2:
                    cinema.addCustomer(scanner);
                    break;

                case 3:
                    cinema.displayMovies();
                    break;

                case 4:
                    cinema.displayCustomerQueue();
                    break;

                case 5:
                    cinema.searchIndexBeforeBooking(scanner);
                    break;

                // Inside the switch statement in CinemaSystem class
                case 6:
                cinema.bookTicket(scanner);
                break;


                case 7:
                    System.out.println("Exiting the program.");
                    scanner.close(); // Close the scanner before exiting
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

