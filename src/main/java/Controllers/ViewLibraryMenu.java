package Controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Observable;

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
    public ComboBox Filter;

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

    public JSONArray DisplayBooks(String title, String path, String category){

        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try( Reader reader = new FileReader(path)){

            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = (JSONObject) it.next();
                if (obj.get("amount").hashCode() >= 1) {
                    if (category == null || category.equals("All")) {
                        if (title == null || title.isEmpty()) {

                            list.add(obj);
                        } else {
                            if (obj.get("title").toString().contains(title)) {
                                list.add(obj);
                            }
                        }
                    } else {
                        if (category.equals(obj.get("category").toString())) {
                            if (title == null || title.isEmpty()) {

                                list.add(obj);
                            } else {
                                if (obj.get("title").toString().contains(title)) {
                                    list.add(obj);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @FXML
    public void Search(){

        String title = SearchBar.getText();
        String filter = (String) Filter.getValue();
        JSONArray list = DisplayBooks(title,"src\\main\\resources\\LibraryBooks.json",filter);
        ObservableList afis = Display.getItems();
        afis.clear();

        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();
            String aux = obj.get("title").toString()+" - "+obj.get("author").toString();
            afis.add(aux);
        }

    }
}
