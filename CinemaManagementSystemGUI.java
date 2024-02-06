import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CinemaManagementSystemGUI extends Application {

    private Cinema cinema;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        cinema = new Cinema(100); // Adjust the size based on your needs

        primaryStage.setTitle("Cinema Management System");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label titleLabel = new Label("Cinema Management System");
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);

        Button addMovieButton = new Button("Add Movie");
        addMovieButton.setOnAction(e -> cinema.addMovie(displayInputDialog("Enter Movie Title:", "Enter Movie Genre:")));
        GridPane.setConstraints(addMovieButton, 0, 1);

        Button addCustomerButton = new Button("Add Customer");
        addCustomerButton.setOnAction(e -> cinema.addCustomer(displayInputDialog("Enter Customer Name:", "Enter Customer Age:")));
        GridPane.setConstraints(addCustomerButton, 1, 1);

        Button displayMoviesButton = new Button("Display Movies");
        displayMoviesButton.setOnAction(e -> displayAlert("Movies in the Cinema", cinema.displayMovies()));
        GridPane.setConstraints(displayMoviesButton, 0, 2);

        Button bookTicketButton = new Button("Book Ticket");
        bookTicketButton.setOnAction(e -> cinema.bookTicket(displayInputDialog("Enter Movie Index:", "Enter Customer Name:")));
        GridPane.setConstraints(bookTicketButton, 1, 2);

        Button displayCustomerQueueButton = new Button("Display Customer Queue");
        displayCustomerQueueButton.setOnAction(e -> displayAlert("Customer Queue", cinema.displayCustomerQueue()));
        GridPane.setConstraints(displayCustomerQueueButton, 0, 3);

        Button searchIndexButton = new Button("Search Index Before Booking");
        searchIndexButton.setOnAction(e -> cinema.searchIndexBeforeBooking(displayInputDialog("Enter Customer Name:", "")));
        GridPane.setConstraints(searchIndexButton, 1, 3);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());
        GridPane.setConstraints(exitButton, 0, 4, 2, 1);

        grid.getChildren().addAll(
                titleLabel,
                addMovieButton,
                addCustomerButton,
                displayMoviesButton,
                bookTicketButton,
                displayCustomerQueueButton,
                searchIndexButton,
                exitButton
        );

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String displayInputDialog(String prompt1, String prompt2) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt1);
        dialog.getDialogPane().getChildren().get(1).setOnMouseClicked(e -> {
            dialog.setContentText(prompt2);
        });

        return dialog.showAndWait().orElse("");
    }

    private void displayAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

