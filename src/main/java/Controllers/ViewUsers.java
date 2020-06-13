package Controllers;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class ViewUsers {
    @FXML
    public Button back;
    @FXML
    private ListView display;

    public long calculateRemainTime(String borrowed_date) {


        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");

        java.util.Date borrowedDate;
        Calendar current = Calendar.getInstance();
        java.util.Date currentDate;
        String current_date = format.format(current.getTime());
        try {
            borrowedDate = format.parse(borrowed_date);
            currentDate = format.parse(current_date);
            long diffInMillies = currentDate.getTime() - borrowedDate.getTime();
            long diffence_in_seconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            // 14 Days in seconds: 1209600
            return 1209600 - diffence_in_seconds;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void Back() {
        try {
            Stage stage = (Stage) back.getScene().getWindow();
            Parent BackMenu = FXMLLoader.load(getClass().getClassLoader().getResource("tmenu.fxml"));
            Scene scene = new Scene(BackMenu, 600, 400);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ListUsers() throws IOException {
        JSONArray list = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try (Reader reader1 = new FileReader("src\\main\\resources\\UsersBooks.json")) {

            JSONArray jsonArray1 = (JSONArray) parser1.parse(reader1);

            Iterator<Object> it1 = jsonArray1.iterator();
            while (it1.hasNext()) {
                JSONObject obj1 = (JSONObject) it1.next();
                list.add(obj1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ObservableList afis = display.getItems();
        afis.clear();
        Iterator<Object> it2 = list.iterator();
        while (it2.hasNext()) {
            JSONObject obj2 = (JSONObject) it2.next();

            String aux = obj2.get("username").toString();
            JSONArray list1 = (JSONArray) obj2.get("books");
            Iterator<Object> it3 = list1.iterator();
            while (it3.hasNext()) {
                JSONObject obj = (JSONObject) it3.next();
                String aux1 = aux + " - " + obj.get("title").toString() + " - " + obj.get("author").toString();
                long x = calculateRemainTime(obj.get("time").toString());
                int days = (int) (x / 86400);
                String aux2 = aux1 + " - " + "Remain time: " + days + " days ";
                Button notify = new Button("Notify");
                afis.addAll(aux2, notify);


            }

        }
    }
}

