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
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField dateField;
    @FXML
    private Text message;
    @FXML
    public Button back;
    @FXML

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

    public void RemoveAllFromFile(String title, String author, String date, String path) throws FileNotFoundException {

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
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();

            //System.out.println(obj);
            if(title.equals(obj.get("title")) && author.equals(obj.get("author")) && date.equals(obj.get("date")))
            {
                obj1=obj;
            }
            else
                message.setText("Book does not exist!");

        }
        list.remove(obj1);
        // System.out.println(list);
        try (FileWriter file = new FileWriter(path)) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RemoveFromFile(String title, String author, String date, String path) throws FileNotFoundException {


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
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();

            //System.out.println(obj);
            if(title.equals(obj.get("title")) && author.equals(obj.get("author")) && date.equals(obj.get("date")))
            {
                obj.replace("amount", ((Long) obj.get("amount") - 1));
            }
            else {
                message.setText("Book does not exist!");
            }
        }
        //System.out.println(list);
        try (FileWriter file = new FileWriter(path)) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        RemoveAllFromFile(titleField.getText(), authorField.getText(), dateField.getText(),"src\\main\\resources\\LibraryBooks.json");

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

        RemoveFromFile(titleField.getText(), authorField.getText(), dateField.getText(),"src\\main\\resources\\LibraryBooks.json");

    }


    }





