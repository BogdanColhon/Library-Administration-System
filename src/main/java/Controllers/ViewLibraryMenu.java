package Controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.*;

import static Controllers.LoginController.UsernameGlobal;

public class ViewLibraryMenu {

    @FXML
    private Button Back;
    @FXML
    private TextField SearchBar;
    @FXML
    private ListView Display;
    @FXML
    private Button Search;
    @FXML
    private ComboBox Filter;
    @FXML
    private Label Message;

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

    public int BorrowBook(String title, String author, String path1, String path2){

        // Un user poate imprumuta doar un singur exemplar din fiecare carte

        String username = UsernameGlobal;

        // Deschid si citesc din fisierul LibraryBooks

        JSONObject cartea = new JSONObject();

        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try( Reader reader = new FileReader(path1)){

            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = (JSONObject) it.next();
                if(obj.get("title").toString().equals(title) && obj.get("author").toString().equals(author)){
                    String time = new SimpleDateFormat("yyyy MM dd HH mm ss").format(Calendar.getInstance().getTime());
                    cartea.put("title",obj.get("title").toString());
                    cartea.put("author",obj.get("author").toString());
                    cartea.put("date",obj.get("date").toString());
                    cartea.put("publishinghouse",obj.get("publishinghouse").toString());
                    cartea.put("category",obj.get("category").toString());
                    cartea.put("time",time);
                    cartea.put("message","0");
                }
                list.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Deschid fisierul UserBooks si caut sa vad daca exista cartea
        JSONArray list1 = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try( Reader reader1 = new FileReader(path2)){

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

        // Verific sa nu aiba deja cartea
        Iterator<Object> it2 = list1.iterator();
        while (it2.hasNext()) {
            JSONObject obj2 = (JSONObject) it2.next();
            if(obj2.get("username").toString().equals(username)){
                if(obj2.get("books") != null){
                    JSONArray caut = (JSONArray) obj2.get("books");
                    Iterator<Object> i = caut.iterator();
                    while (i.hasNext()){
                        JSONObject o = (JSONObject) i.next();
                        if(o.get("title").toString().equals(title)){
                            return 0;
                        }
                    }
                }
            }
        }

        // Daca nu mai exista cartea atunci decrementez cu 1 cartea din LibraryBooks
        Iterator<Object> it1 = list.iterator();
        while (it1.hasNext()) {
            JSONObject obj = (JSONObject) it1.next();
            if(obj.get("title").toString().equals(title) && obj.get("author").toString().equals(author)){
                obj.replace("amount",obj.get("amount").hashCode()-1);
            }
        }

        // Scriu cartea in UserBooks si rescriu LibraryBooks

        Iterator<Object> it3 = list1.iterator();
        while (it3.hasNext()) {
            JSONObject obj2 = (JSONObject) it3.next();
            if(obj2.get("username").toString().equals(username)){

                JSONArray caut = (JSONArray) obj2.get("books");
                caut.add(cartea);
            }
        }

        try (FileWriter file = new FileWriter(path1)) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter(path2)) {
            file.write(list1.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return 1;
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
            Button borrow = new Button("Borrow");
            String aux = obj.get("title").toString()+" - "+obj.get("author").toString();
            afis.addAll(aux,borrow);
            borrow.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String title = obj.get("title").toString();
                    String author = obj.get("author").toString();
                    if(BorrowBook(title,author,"src\\main\\resources\\LibraryBooks.json","src\\main\\resources\\UsersBooks.json") == 0){
                        Message.setText("You already borrowed this book!");
                    }
                    else{
                        Message.setText("");
                    }
                }
            });
        }

    }
}
