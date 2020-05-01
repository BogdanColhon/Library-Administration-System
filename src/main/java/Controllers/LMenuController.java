package Controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;

public class LMenuController {
    @FXML
    public Button addremove;
    @FXML
    public void AddRemoveButtonAction() {
        try {
            Stage stage = (Stage) addremove.getScene().getWindow();
            Parent AddRemoveMenu = FXMLLoader.load(getClass().getClassLoader().getResource("Add_Remove_Books_menu.fxml"));
            Scene scene = new Scene(AddRemoveMenu, 750, 300);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

}
