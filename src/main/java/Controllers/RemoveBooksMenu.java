package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;

public class RemoveBooksMenu {
    @FXML
    TextField titleField;
    @FXML
   TextField authorField;
    @FXML
    TextField dateField;
    @FXML
    Text message;
    @FXML
    public Button back;
    @FXML
private   int rez=0;
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

    public int RemoveAllFromFile(String title, String author, String date, String path) throws FileNotFoundException {

        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(path)) {

            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            //System.out.println(jsonArray);


            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = (JSONObject) it.next();
                list.add(obj);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<Object> it = list.iterator();
        JSONObject obj1 = new JSONObject();
        int gasit=0;
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();
            if(title.equals(obj.get("title")) && author.equals(obj.get("author")) && date.equals(obj.get("date")))
            {
                obj1=obj;
                gasit=1;
            }

        }
        list.remove(obj1);
        // System.out.println(list);
        try (FileWriter file = new FileWriter(path)) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gasit;
    }

    public int RemoveFromFile(String title, String author, String date, String path) throws FileNotFoundException {


        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(path)) {

            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            //System.out.println(jsonArray);


            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = (JSONObject) it.next();
                list.add(obj);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<Object> it = list.iterator();
        JSONObject obj1 = new JSONObject();
        int gasit = 0;
        int amo=0;
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();

            //System.out.println(obj);
            if(title.equals(obj.get("title")) && author.equals(obj.get("author")) && date.equals(obj.get("date")))
            {

                if((Long) obj.get("amount")<=0)
                {
                  amo=0;//daca cantitatea este 0 dar cartea este gasita returnam 1 si afisam rezultatul corespunzator
                  gasit=1;
                }
                else {
                    obj.replace("amount", ((Long) obj.get("amount") - 1));
                    gasit = 1; //daca cantitatea este mai mare de 0, amo=1 si cartea este gasita, returnam 2 si afisam rezultatul corespunzator
                    amo=1;
                }//daca cartea nu este gasita, rezulta ca amo=0 si gasit=0, returnam 0 si afisam mesajul corespunzator
            }
        }
        //System.out.println(list);
        try (FileWriter file = new FileWriter(path)) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gasit+amo;
    }
    @FXML
        public void RemoveAllBooksAction () throws FileNotFoundException {
        message.setText("");
        if (titleField.getText() == null || titleField.getText().isEmpty()) {

            message.setText("Please type in a title!");
            return;
        }

        if (authorField.getText() == null || authorField.getText().isEmpty()) {

            message.setText("Please type in the author!");
            return;
        }

        if (dateField.getText() == null || dateField.getText().isEmpty()) {

            message.setText("Please type in a date!");
            return;
        }
        if (RemoveAllFromFile(titleField.getText(), authorField.getText(), dateField.getText(), "src\\main\\resources\\LibraryBooks.json") == 0) {
            message.setText("Book does not exist!");
        }
    }
    @FXML
    public void RemoveBookAction () throws FileNotFoundException {


        message.setText("");
        if (titleField.getText() == null || titleField.getText().isEmpty()) {

            message.setText("Please type in a title!");
            return;
        }

        if (authorField.getText() == null || authorField.getText().isEmpty()) {

            message.setText("Please type in the author!");
            return;
        }

        if (dateField.getText() == null || dateField.getText().isEmpty()) {

            message.setText("Please type in a date!");
            return;
        }

        rez=RemoveFromFile(titleField.getText(), authorField.getText(), dateField.getText(), "src\\main\\resources\\LibraryBooks.json");
        if(rez==0)
            message.setText("Book does not exist!");
        else
            if(rez==1)
            message.setText("No copies left");


    }
}





