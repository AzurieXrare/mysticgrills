import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {

    private Main mainApp;
    private Label errorMessageLabel;
    private Label usernameErrorLabel;
    private Label emailErrorLabel;
    private Label passwordErrorLabel;
    private Label confirmPasswordErrorLabel;

    public Register(Main mainApp) {
        this.mainApp = mainApp;
        this.errorMessageLabel = new Label();
        this.usernameErrorLabel = new Label();
        this.emailErrorLabel = new Label();
        this.passwordErrorLabel = new Label();
        this.confirmPasswordErrorLabel = new Label();
    }

    public GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameErrorLabel.setStyle("-fx-text-fill: red;"); // warna teks merah
        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailErrorLabel.setStyle("-fx-text-fill: red;"); // warna teks merah
        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordErrorLabel.setStyle("-fx-text-fill: red;"); // warna teks merah
        
        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordErrorLabel.setStyle("-fx-text-fill: red;"); // warna teks merah
        
        Button registerButton = new Button("Register");
        Button goToLoginButton = new Button("Go to Login");

        errorMessageLabel.setText("");
        
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Setel pesan kesalahan ke kosong saat validasi ulang
            usernameErrorLabel.setText("");
            emailErrorLabel.setText("");
            passwordErrorLabel.setText("");
            confirmPasswordErrorLabel.setText("");

            if (validateInput(username, email, password, confirmPassword) && validateEmail(email) && validatePassword(password, confirmPassword)) {
                if (isEmailUnique(email)) {
                    registerUser(username, email, password);
                    System.out.println("User registered successfully!");
                    errorMessageLabel.setText("");
                } else {
                    errorMessageLabel.setText("Email is already registered. Please use a different email.");
                }
            } else {
                errorMessageLabel.setText("Please fill in all fields, enter a valid email, and make sure passwords match and are at least 6 characters.");

                // Setel pesan kesalahan sesuai dengan kondisi yang tidak valid
                if (username.isEmpty()) {
                    usernameErrorLabel.setText("Username cannot be empty.");
                }

                if (email.isEmpty()) {
                    emailErrorLabel.setText("Email cannot be empty.");
                } else if (!validateEmail(email)) {
                    emailErrorLabel.setText("Invalid email format.");
                }

                if (password.isEmpty()) {
                    passwordErrorLabel.setText("Password cannot be empty.");
                } else if (password.length() < 6) {
                    passwordErrorLabel.setText("Password must be at least 6 characters.");
                }

                if (confirmPassword.isEmpty()) {
                    confirmPasswordErrorLabel.setText("Confirm Password cannot be empty.");
                } else if (!password.equals(confirmPassword)) {
                    confirmPasswordErrorLabel.setText("Passwords do not match.");
                }
            }
        });

        goToLoginButton.setOnAction(e -> {
            mainApp.showLoginScreen();
        });

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(usernameErrorLabel, 1, 1); // Tambahkan Label untuk pesan kesalahan

        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(emailErrorLabel, 1, 3); // Tambahkan Label untuk pesan kesalahan

        grid.add(passwordLabel, 0, 4);
        grid.add(passwordField, 1, 4);
        grid.add(passwordErrorLabel, 1, 5); // Tambahkan Label untuk pesan kesalahan

        grid.add(confirmPasswordLabel, 0, 6);
        grid.add(confirmPasswordField, 1, 6);
        grid.add(confirmPasswordErrorLabel, 1, 7); // Tambahkan Label untuk pesan kesalahan

        grid.add(registerButton, 0, 8);
        grid.add(goToLoginButton, 1, 8);

//        grid.add(errorMessageLabel, 0, 5, 2, 1);

        return grid;
    }

    private boolean validateInput(String username, String email, String password, String confirmPassword) {
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty();
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isEmailUnique(String email) {
        Connect connect = Connect.getConnection();
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connect.prepare(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validatePassword(String password, String confirmPassword) {
        return password.length() >= 6 && password.equals(confirmPassword);
    }

    private void registerUser(String username, String email, String password) {
        Connect connect = Connect.getConnection();
        connect.registerUser(username, email, password);
    }
}
