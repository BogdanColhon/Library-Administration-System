package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static Controllers.LoginController.UsernameGlobal;

public class BorrowedBooks implements Initializable {

    @FXML
    private Button back;
    @FXML
    private ListView Display;

    public long calculateRemainTime(String borrowed_date){


        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");

        java.util.Date borrowedDate;
        Calendar current = Calendar.getInstance();
        java.util.Date currentDate;
        String current_date = format.format(current.getTime());
        try {
            borrowedDate = format.parse(borrowed_date);
            currentDate = format.parse(current_date);
            long diffInMillies = currentDate.getTime() - borrowedDate.getTime();
            long diffence_in_seconds = TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);
            // 14 Days in seconds: 1209600
            return 1209600-diffence_in_seconds;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @FXML
    private void DisplayBorrowedBooks(){
        String username = UsernameGlobal;
        Display.getItems().clear();

        JSONArray list1 = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try( Reader reader1 = new FileReader("src\\main\\resources\\UsersBooks.json")){

            JSONArray jsonArray1 = (JSONArray) parser1.parse(reader1);

            Iterator<Object> it1 = jsonArray1.iterator();
            while (it1.hasNext()) {
                JSONObject obj1 = (JSONObject) it1.next();
                list1.add(obj1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Iterator<Object> it2 = list1.iterator();
        while (it2.hasNext()) {
            JSONObject obj2 = (JSONObject) it2.next();
            if(obj2.get("username").toString().equals(username)){
                JSONArray list = (JSONArray) obj2.get("books");
                Iterator<Object> i = list.iterator();
                while(i.hasNext()){
                    JSONObject obj = (JSONObject) i.next();
                    String aux = obj.get("title").toString() + " - " + obj.get("author").toString();
                    long x = calculateRemainTime(obj.get("time").toString());
                    int days = (int) (x / 86400);
                    int hours = (int) ((x - (days * 86400))/3600);
                    int min = (int) ((x - (days * 86400) - (hours * 3600))/60);
                    int sec = (int) (x - (days * 86400) - (hours * 3600) - (min * 60));
                    String aux1 ="Remain time: " + days + " days " + hours + " hours " + min + " min " + sec + " sec";
                    Display.getItems().addAll(aux,aux1);

                }
            }
        }

    }
    @FXML
    public void Back() {
        try {
            Stage stage = (Stage) back.getScene().getWindow();
            Parent BackMenu = FXMLLoader.load(getClass().getClassLoader().getResource("umenu.fxml"));
            Scene scene = new Scene(BackMenu, 600, 400);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DisplayBorrowedBooks();
    }
}
