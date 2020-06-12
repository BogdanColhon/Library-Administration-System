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

import static Controllers.LoginController.UsernameGlobal;

public class BorrowedBooks implements Initializable {

    @FXML
    private Button back;
    @FXML
    private ListView Display;

    private int CalculateTimeLeft(String y){
        String[] x=y.split("\\W");
        int year = Integer.parseInt(x[0]);
        int month = Integer.parseInt(x[1]);
        int day = Integer.parseInt(x[2]);
        int hour = Integer.parseInt(x[3]);
        int min = Integer.parseInt(x[4]);
        int second = Integer.parseInt(x[5]);
        //System.out.println(year + " " + month + " " + day + " " + hour + " " + min + " " + second);

        String time = new SimpleDateFormat("yyyy MM dd HH mm ss").format(Calendar.getInstance().getTime());
        String[] z=time.split("\\W");
        int current_year = Integer.parseInt(z[0]);
        int current_month = Integer.parseInt(z[1]);
        int current_day = Integer.parseInt(z[2]);
        int current_hour = Integer.parseInt(z[3]);
        int current_min = Integer.parseInt(z[4]);
        int current_second = Integer.parseInt(z[5]);
        //System.out.println("AAAAAAAA\n" + current_year+ " " + current_month + " " + current_day + " " + current_hour + " " + current_min + " " + current_second);
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
                    Display.getItems().add(aux);
                    CalculateTimeLeft(obj.get("time").toString());
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
