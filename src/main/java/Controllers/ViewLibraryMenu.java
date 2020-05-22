package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ViewLibraryMenu {

    @FXML
    public Button Back;
    @FXML
    public TextField SearchBar;
    @FXML
    public ListView Display;
    @FXML
    public Button Search;
    @FXML
    public Button Filter;

    @FXML
    public void Back() {
        try {
            Stage stage = (Stage) Back.getScene().getWindow();
            Parent BackMenu = FXMLLoader.load(getClass().getClassLoader().getResource("umenu.fxml"));
            Scene scene = new Scene(BackMenu, 600, 400);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
