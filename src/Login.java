import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private Main mainApp;
    private Label usernameErrorLabel;
    private Label passwordErrorLabel;
    private Label errorMessageLabel;

    public Login(Main mainApp) {
        this.mainApp = mainApp;
        this.errorMessageLabel = new Label();
        this.usernameErrorLabel = new Label();
        this.passwordErrorLabel = new Label();
    }

    public GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button goToRegisterButton = new Button("Go to Register");

        usernameErrorLabel.setText("");
        passwordErrorLabel.setText("");
        errorMessageLabel.setText("");
        errorMessageLabel.setStyle("-fx-text-fill: red;");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Setel pesan kesalahan ke kosong saat validasi ulang
            usernameErrorLabel.setText("");
            passwordErrorLabel.setText("");
            errorMessageLabel.setText("");

            if (validateInput(username, password)) {
                if (validateUserCredentials(username, password)) {
                    // Logika login di sini
                    System.out.println("Login successful!");

                    // Navigasi ke halaman menu
                    mainApp.showMenuScreen(new Order());
                } else {
                    // Password tidak sesuai dengan yang ada di database
                    passwordErrorLabel.setText("Invalid password.");
                }
            } else {
                if (username.isEmpty()) {
                    usernameErrorLabel.setText("Username cannot be empty.");
                }
                if (password.isEmpty()) {
                    passwordErrorLabel.setText("Password must be filled");
                }

                errorMessageLabel.setText("Invalid username or password.");
            }
        });

        goToRegisterButton.setOnAction(e -> {
            // Pindah ke tampilan register
            mainApp.showRegisterScreen();
        });

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(usernameErrorLabel, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(passwordErrorLabel, 1, 3);
        grid.add(loginButton, 0, 4);
        grid.add(goToRegisterButton, 1, 4);
        grid.add(errorMessageLabel, 0, 5, 2, 1);

        return grid;
    }

    private boolean validateInput(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private boolean validateUserCredentials(String username, String password) {
        Connect connect = Connect.getConnection();
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connect.prepare(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
