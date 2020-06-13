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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField searchfield;
    @FXML private RadioButton RB1;
    @FXML private RadioButton RB2;
    @FXML private RadioButton RB3;
    @FXML private RadioButton RB4;


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

    @FXML
    public void ListUsers(String path, int limitinf, int limitsup, String search) throws IOException {
        JSONArray list = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try (Reader reader1 = new FileReader(path)) {

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
            if (obj2.get("username").toString().contains(search)) {
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
                    if (days > limitinf && days < limitsup) {
                        afis.addAll(aux2, notify);
                        notify.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                obj.replace("message", "1");
                                try (Writer out = new FileWriter("src/main/resources/UsersBooks.json")) {
                                    out.write(list.toJSONString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                        });
                    }

                }

            }
        }
    }
    public static String x="";
    public void RB_all() throws IOException {
        searchfield.clear();
        ListUsers("src/main/resources/UsersBooks.json", -1000, 1000,x);
        x="";

    }

    public void RB_good() throws IOException {
        searchfield.clear();
        ListUsers("src/main/resources/UsersBooks.json", 5, 14, x);
        x="";
    }

    public void RB_atlimit() throws IOException {
        searchfield.clear();
        ListUsers("src/main/resources/UsersBooks.json", 0, 5, x);
        x="";
    }

    public void RB_overdue() throws IOException {
        searchfield.clear();
        ListUsers("src/main/resources/UsersBooks.json", -1000, 0, x);
        x="";
    }

    public void searchAction() throws IOException {
        x=searchfield.getText();
        if(RB1.isSelected()) {
            RB_all();
        }
        if(RB2.isSelected()) {
            RB_good();
        }
        if(RB3.isSelected()) {
            RB_atlimit();
        }
        if(RB4.isSelected()) {
            RB_overdue();
        }


    }

}

