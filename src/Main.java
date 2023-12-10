import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private BorderPane root = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Order Management System");


        // Set the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        // Show the login screen
        showLoginScreen();

        // Show the stage
        primaryStage.show();
    }

    public void showLoginScreen() {
        Login login = new Login(this);
        root.setCenter(login.getGrid());
    }

    public void showRegisterScreen() {
        Register register = new Register(this);
        root.setCenter(register.getGrid());
    }

    public void showMenuScreen(Order order) {
        Menu menuScreen = new Menu(this, order);
        root.setCenter(menuScreen.getGrid());
    }

    public void showOrderSummary(Order order) {
        OrderSummary orderSummary = new OrderSummary(this, order);
        root.setCenter(orderSummary.getGrid());
    }
}
