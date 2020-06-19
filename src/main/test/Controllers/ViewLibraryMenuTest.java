package Controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ViewLibraryMenuTest extends ApplicationTest {

    ViewLibraryMenu VLM;
    LoginController LC;
    @Before
    public void setUp() throws Exception {
        VLM = new ViewLibraryMenu();
        VLM.SearchBar = new TextField();
        VLM.Display = new ListView();
        VLM.Filter = new ComboBox();
        VLM.Message = new Label();
        VLM.Path1 = "src\\main\\test\\resources\\LibraryBooks.json";
        VLM.Path2 = "src\\main\\test\\resources\\UsersBooks.json";

        LC = new LoginController();

        LC.UsernameGlobal="casian_ciuban";
    }

    @After
    public void tearDown() throws Exception {
        VLM = null;
        LC = null;
    }

    @Test
    public void DisplayBookTest(){
        JSONArray list = VLM.DisplayBooks("The Memoirs of Sherlock Holmes","src\\main\\test\\resources\\LibraryBooks.json","Detective");
        //System.out.println(list);
        org.json.simple.JSONObject obj = (org.json.simple.JSONObject) list.get(0);
        //System.out.println(obj);
        assertEquals("1893",obj.get("date").toString());
        assertEquals("8",obj.get("amount").toString());
        assertEquals("Arthur Conan Doyle",obj.get("author").toString());
        assertEquals(" ALMA BOOKS",obj.get("publishinghouse").toString());
        assertEquals("The Memoirs of Sherlock Holmes",obj.get("title").toString());
        assertEquals("Detective",obj.get("category").toString());
    }

    @Test
    public void BorrowBookTest(){
        int x = VLM.BorrowBook("The Three Musketeers","Alexandre Dumas","src\\main\\test\\resources\\LibraryBooks.json","src\\main\\test\\resources\\UsersBooks.json");
        assertEquals(0,x);

        // Deschid fisierul UserBooks si citesc in list 1
        JSONArray list1 = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try( Reader reader1 = new FileReader("src\\main\\test\\resources\\UsersBooks.json")){

            JSONArray jsonArray1 = (JSONArray) parser1.parse(reader1);

            Iterator<Object> it1 = jsonArray1.iterator();
            while (it1.hasNext()) {
                org.json.simple.JSONObject obj1 = (JSONObject) it1.next();
                list1.add(obj1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(list1);

        // Deschid si citesc din fisierul LibraryBooks in list
        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try( Reader reader = new FileReader("src\\main\\test\\resources\\LibraryBooks.json")){

            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = (JSONObject) it.next();
                list.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        x = VLM.BorrowBook("The Memoirs of Sherlock Holmes","Arthur Conan Doyle","src\\main\\test\\resources\\LibraryBooks.json","src\\main\\test\\resources\\UsersBooks.json");
        assertEquals(1,x);

        try (FileWriter file = new FileWriter("src\\main\\test\\resources\\LibraryBooks.json")) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter("src\\main\\test\\resources\\UsersBooks.json")) {
            file.write(list1.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void SearchTest(){

        VLM.SearchBar.setText("The Three Musketeers");
        VLM.Filter.setValue("All");
        VLM.Search();
        ObservableList list = VLM.Display.getItems();
        //System.out.println(list);
        assertEquals("The Three Musketeers - Alexandre Dumas",list.get(0));
    }
}