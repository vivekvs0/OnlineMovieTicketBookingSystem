import java.util.*;

class Movie {
    String title;
    int totalSeats;
    int availableSeats;
    Map<String, List<Boolean>> seatMap;

    public Movie(String title, int totalSeats) {
        this.title = title;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.seatMap = new HashMap<>();
        initializeSeatMap();
    }

    private void initializeSeatMap() {
        for (int i = 1; i <= totalSeats; i++) {
            seatMap.put(Integer.toString(i), new ArrayList<>(Collections.singletonList(false)));
        }
    }
}

class BookingSystem {
    Map<String, Movie> movies;
    Map<String, List<String>> bookings;

    public BookingSystem() {
        movies = new HashMap<>();
        bookings = new HashMap<>();
    }

    public void addMovie(String title, int totalSeats) {
        Movie movie = new Movie(title, totalSeats);
        movies.put(title, movie);
    }

    public void displayMovies() {
        System.out.println("Available Movies:");
        for (Movie movie : movies.values()) {
            System.out.println(movie.title + " - Available Seats: " + movie.availableSeats);
        }
    }

    public void displayShowtimes(String movieTitle) {
        Movie movie = movies.get(movieTitle);
        if (movie != null) {
            System.out.println("Showtimes for " + movie.title + ":");
            System.out.println("1. Morning Show");
            System.out.println("2. Afternoon Show");
            System.out.println("3. Evening Show");
        } else {
            System.out.println("Movie not found.");
        }
    }

    public void bookTicket(String movieTitle, int showtime, List<String> selectedSeats) {
        Movie movie = movies.get(movieTitle);
        if (movie != null && movie.availableSeats >= selectedSeats.size()) {
            for (String seat : selectedSeats) {
                movie.seatMap.get(seat).set(showtime - 1, true);
            }
            movie.availableSeats -= selectedSeats.size();
            System.out.println("Booking successful! Enjoy the movie.");
            bookings.put(movieTitle, selectedSeats);
        } else {
            System.out.println("Booking failed. Not enough seats available.");
        }
    }

    public void cancelTicket(String movieTitle) {
        List<String> canceledSeats = bookings.remove(movieTitle);
        if (canceledSeats != null) {
            Movie movie = movies.get(movieTitle);
            for (String seat : canceledSeats) {
                movie.seatMap.get(seat).set(0, false);
            }
            movie.availableSeats += canceledSeats.size();
            System.out.println("Ticket cancellation successful.");
        } else {
            System.out.println("No booking found for the movie.");
        }
    }
}

public class OnlineMovieSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem bookingSystem = new BookingSystem();

        // Adding some sample movies
        bookingSystem.addMovie("Inception", 50);
        bookingSystem.addMovie("The Dark Knight", 40);

        while (true) {
            System.out.println("\n1. Display Movies");
            System.out.println("2. Display Showtimes");
            System.out.println("3. Book Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookingSystem.displayMovies();
                    break;
                case 2:
                    System.out.print("Enter the movie title: ");
                    String showtimeMovieTitle = scanner.next();
                    bookingSystem.displayShowtimes(showtimeMovieTitle);
                    break;
                case 3:
                    System.out.print("Enter the movie title: ");
                    String bookMovieTitle = scanner.next();
                    System.out.print("Enter the showtime (1, 2, or 3): ");
                    int showtime = scanner.nextInt();
                    System.out.print("Enter the number of seats to book: ");
                    int numSeats = scanner.nextInt();

                    List<String> selectedSeats = new ArrayList<>();
                    for (int i = 0; i < numSeats; i++) {
                        System.out.print("Enter seat number " + (i + 1) + ": ");
                        selectedSeats.add(scanner.next());
                    }

                    bookingSystem.bookTicket(bookMovieTitle, showtime, selectedSeats);
                    break;
                case 4:
                    System.out.print("Enter the movie title to cancel tickets: ");
                    String cancelMovieTitle = scanner.next();
                    bookingSystem.cancelTicket(cancelMovieTitle);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
