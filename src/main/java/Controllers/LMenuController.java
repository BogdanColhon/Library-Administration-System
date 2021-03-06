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
    public Button add;
    @FXML
    public Button removemenu;
    @FXML
    public Button viewusers;
    @FXML
    public void AddRemoveButtonAction() {
        try {
            Stage stage = (Stage) add.getScene().getWindow();
            Parent AddRemoveMenu = FXMLLoader.load(getClass().getClassLoader().getResource("Add_Remove_Books_menu.fxml"));
            Scene scene = new Scene(AddRemoveMenu, 750, 320);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void RemoveButtonAction () {
            try {
                Stage stage = (Stage) removemenu.getScene().getWindow();
                Parent AddRemoveMenu = FXMLLoader.load(getClass().getClassLoader().getResource("Remove_Books_menu.fxml"));
                Scene scene = new Scene(AddRemoveMenu, 400, 320);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }
    public void ViewUsersButtonAction() {
        try {
            Stage stage = (Stage) viewusers.getScene().getWindow();
            Parent AddRemoveMenu = FXMLLoader.load(getClass().getClassLoader().getResource("View_Users.fxml"));
            Scene scene = new Scene(AddRemoveMenu, 800, 400);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    }


