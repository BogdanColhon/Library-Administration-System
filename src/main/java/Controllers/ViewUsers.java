package Controllers;

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
import java.util.concurrent.TimeUnit;

public class ViewUsers {
    @FXML
    public Button back;
    @FXML ListView display;
    @FXML TextField searchfield;
    @FXML RadioButton RB1;
    @FXML RadioButton RB2;
    @FXML RadioButton RB3;
    @FXML RadioButton RB4;
    private String path="src/main/resources/UsersBooks.json";
    private static long timpactual;
    public static long currentTime() throws java.text.ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");

        Calendar current = Calendar.getInstance();
        java.util.Date currentDate;
        String current_date = format.format(current.getTime());
        currentDate = format.parse(current_date);
        timpactual=currentDate.getTime();
        return timpactual;
    }



    public long calculateRemainTime(String borrowed_date,long setDate) throws java.text.ParseException {//am adaugat parametrul setDate ca sa pot sa fac teste. aveam nevoie de o data fixa

        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");

        java.util.Date borrowedDate;

        try {
            borrowedDate = format.parse(borrowed_date);

            long diffInMillies = setDate - borrowedDate.getTime();
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
            Scene scene = new Scene(BackMenu, 600, 280);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ListUsers(String path, int limitinf, int limitsup, String search,long SetDate) throws IOException, java.text.ParseException {//am adaugat parametrul search ca sa pot sa fac teste in care caut dupa un string
        JSONArray list = new JSONArray();                                                                                 //setDate a fost adaugat tot pentru a putea sa fac teste metodelor
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
                    long x = calculateRemainTime(obj.get("time").toString(),SetDate);
                    int days = (int) (x / 86400);
                    String aux2 = aux1 + " - " + "Remain time: " + days + " days ";
                    Button notify = new Button("Notify");
                    if (days >= limitinf && days <= limitsup) {
                        afis.addAll(aux2, notify);
                        notify.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                obj.replace("message", "1");//prin setarea pe 1 a fieldului message, programul va afisa un mesaj corespunzator userului
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
    ///For tests
    public void RB_all(String Path,String s,long time) throws IOException, java.text.ParseException {
        searchfield.clear();
        ListUsers(Path, -1000, 1000,s,time);
        s="";

    }

    public void RB_good(String Path,String s,long time) throws IOException, java.text.ParseException {
        searchfield.clear();
        ListUsers(Path, 5, 14, s,time);
        s="";
    }

    public void RB_atlimit(String Path,String s,long time) throws IOException, java.text.ParseException {
        searchfield.clear();
        ListUsers(Path, 0, 5, s,time);
        s="";
    }

    public void RB_overdue(String Path,String s,long time) throws IOException, java.text.ParseException {
        searchfield.clear();
        ListUsers(Path, -1000, 0, s,time);
        s="";
    }

// metode pentru programul principal, in care timpul este cel din interiorul clasei iar caracterul cautat este setat prin searchField.getText()
    public void RB_allFINAL() throws IOException, java.text.ParseException {//cazurile in care se apasa doar pe radio button, fara a se da search dupa un caracter
        currentTime();                                                      //Astfel, se vor afisa toate datele care respecta doar intervalul de timp
        searchfield.clear();
        RB_all(path,x,timpactual);
        x="";

    }

    public void RB_goodFINAL() throws IOException, java.text.ParseException {
        currentTime();
        searchfield.clear();
        RB_good(path,x,timpactual);
        x="";
    }

    public void RB_atlimitFINAL() throws IOException, java.text.ParseException {
        currentTime();
        searchfield.clear();
      RB_atlimit(path,x,timpactual);
        x="";
    }

    public void RB_overdueFINAL() throws IOException, java.text.ParseException {
        currentTime();
        searchfield.clear();
      RB_overdue(path,x,timpactual);
        x="";
    }
//For test

    public void searchAction(String Path,String s,long time) throws IOException, java.text.ParseException {

        if(RB1.isSelected()) {//cazurile in care se apasa pe radio button iar apoi se cauta dupa caracter in interiorul listei oferite de radiobutton
            RB_all(Path,s,time);//se vor afisa datele care respecta atat intervalul de timp dat de radiobutton cat si caracterul dat de search
        }
        if(RB2.isSelected()) {
            RB_good(Path,s,time);
        }
        if(RB3.isSelected()) {
            RB_atlimit(Path,s,time);
        }
        if(RB4.isSelected()) {
            RB_overdue(Path,s,time);
        }


    }
//metoda pentru programul principal
    public void searchActionFINAL() throws IOException, java.text.ParseException {
        currentTime();
        x=searchfield.getText();
        searchAction(path,x,timpactual);
        x="";
    }

}

