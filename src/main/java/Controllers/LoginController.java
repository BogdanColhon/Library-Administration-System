package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
public class LoginController {

    @FXML
    public Text loginMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    @FXML
    public void initialize() {

    }

    @FXML
    public void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty()) {
            loginMessage.setText("Please type in a username!");
            return;
        }

        if (password == null || password.isEmpty()) {
            loginMessage.setText("Password cannot be empty");
            return;
        }

        if (username.equals("user") && password.equals("user")) {
            loginMessage.setText("Logged in as user!");
            return;
        }

        if (username.equals("librarian") && password.equals("librarian")) {
            try {
                Stage stage = (Stage) loginMessage.getScene().getWindow();
                Parent TeacherMenu = FXMLLoader.load(getClass().getResource("tmenu.fxml"));
                Scene scene = new Scene(TeacherMenu, 600, 400);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }


        loginMessage.setText("Incorrect login!");
    }
}