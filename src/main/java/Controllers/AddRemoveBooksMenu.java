package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class AddRemoveBooksMenu  {
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField publishinghouseField;
    @FXML
    private TextField dateField;
    @FXML
    private ComboBox selectcategory;
    @FXML
    private Text message;

    @FXML
    public void AddBookAction() throws FileNotFoundException {

        //System.out.println(titleField.getText());
        if(titleField.getText() == null || titleField.getText().isEmpty()){

            message.setText("Please type in a title!");
            return;
        }

        if(authorField.getText() == null || authorField.getText().isEmpty()){

            message.setText("Please type in the author!");
            return;
        }

        if(publishinghouseField.getText() == null || publishinghouseField.getText().isEmpty()){

            message.setText("Please type in a publishing house!");
            return;
        }

        if(dateField.getText() == null || dateField.getText().isEmpty()){

            message.setText("Please type in a date!");
            return;
        }

        if(selectcategory.getValue() == null){
            message.setText("Please select a category!");
            return;
        }

// Citesc din fisier

        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try( Reader reader = new FileReader("src\\main\\resources\\LibraryBooks.json")){

            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            //System.out.println(jsonArray);


            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = (JSONObject) it.next();
                //System.out.println(obj.get("title") + " - " + obj.get("author") + " - " + obj.get("amount"));
                list.add(obj);

                //System.out.println(list.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


// Adaug daca trebuie sau incrementez

        int am_gasit=0;
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();
            //System.out.println(obj);
            if ((am_gasit == 0) && (titleField.getText().equals(obj.get("title")))
                    && (authorField.getText().equals(obj.get("author")))
                    && (publishinghouseField.getText().equals(obj.get("publishinghouse")))
                    && (dateField.getText().equals(obj.get("date")))
                    && (selectcategory.getValue().equals(obj.get("category")))) {
                obj.replace("amount", ((Long) obj.get("amount") + 1));
                am_gasit = 1;
                //System.out.println("1");

            }
        }
        if(am_gasit != 1)
            {
                JSONObject obj1 = new JSONObject();
                obj1.put("title", titleField.getText());
                obj1.put("author", authorField.getText());
                obj1.put("publishinghouse", publishinghouseField.getText());
                obj1.put("date", dateField.getText());
                obj1.put("category", selectcategory.getValue());
                obj1.put("amount", 1);

                list.add(obj1);
                //System.out.println("2");
            }
        try (FileWriter file = new FileWriter("src\\main\\resources\\LibraryBooks.json")) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
