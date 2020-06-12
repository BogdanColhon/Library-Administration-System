package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.awt.*;
import java.io.IOException;


public class UMenuController {

    @FXML
    public Button viewbutton;
    @FXML
    public Button borrowed;

    @FXML
    public void ViewLibraryButtonAction(){
        try {
            Stage stage = (Stage) viewbutton.getScene().getWindow();
            Parent ViewLibraryMenu = FXMLLoader.load(getClass().getClassLoader().getResource("View_Library_menu.fxml"));
            Scene scene = new Scene(ViewLibraryMenu, 725, 525);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void BorrowedBooksAction(){
        try {
            Stage stage = (Stage) borrowed.getScene().getWindow();
            Parent BorrowedBooks = FXMLLoader.load(getClass().getClassLoader().getResource("Borrowed_Books.fxml"));
            Scene scene = new Scene(BorrowedBooks, 725, 525);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
