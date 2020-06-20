package Controllers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class AddRemoveBooksMenuTest extends ApplicationTest {

    AddRemoveBooksMenu ARBM;
    @Before
    public void setUp() throws Exception {
        ARBM = new AddRemoveBooksMenu();
        ARBM.titleField=new TextField();
        ARBM.authorField=new TextField();
        ARBM.publishinghouseField=new TextField();
        ARBM.dateField=new TextField();
        ARBM.selectcategory=new ComboBox();
        ARBM.message=new Text();
    }

    @After
    public void tearDown() throws Exception {
        ARBM = null;
    }

    @Test
    public void testAddNewBook() {


        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try( Reader reader = new FileReader("src\\main\\test\\resources\\LibraryBooks.json")){

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

        String title="AAA"; String author="BBB"; String publishinghouse="CCC"; String date="DDD"; String category="EEE";
        ARBM.AddInFile("AAA","BBB","CCC","DDD","EEE","src\\main\\test\\resources\\LibraryBooks.json");

        JSONArray list1 = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try( Reader reader1 = new FileReader("src\\main\\test\\resources\\LibraryBooks.json")){

            JSONArray jsonArray1 = (JSONArray) parser1.parse(reader1);
            //System.out.println(jsonArray);


            Iterator<Object> it1 = jsonArray1.iterator();
            while (it1.hasNext()) {
                JSONObject obj1 = (JSONObject) it1.next();
                //System.out.println(obj.get("title") + " - " + obj.get("author") + " - " + obj.get("amount"));
                list1.add(obj1);

                //System.out.println(list.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int am_gasit=0;
        Iterator<Object> it = list1.iterator();
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();
            //System.out.println(obj);
            if ((am_gasit == 0) && (title.equals(obj.get("title")))
                    && (author.equals(obj.get("author")))
                    && (publishinghouse.equals(obj.get("publishinghouse")))
                    && (date.equals(obj.get("date")))
                    && (category.equals(obj.get("category")))) {
                //System.out.println(11);
                am_gasit = 1;
            }
        }

        //System.out.println(list);System.out.println(list1);
        try (FileWriter file = new FileWriter("src\\main\\test\\resources\\LibraryBooks.json")) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(am_gasit);
        Assert.assertTrue(am_gasit==1);

    }

    @Test
    public void testNoFildsMassages() throws FileNotFoundException {

        ARBM.titleField.setText("");
        ARBM.AddBookAction();
        assertEquals("Please type in a title!",ARBM.message.getText());

        ARBM.titleField.setText("1");
        ARBM.authorField.setText("");
        ARBM.AddBookAction();
        assertEquals("Please type in the author!",ARBM.message.getText());

        ARBM.titleField.setText("1");
        ARBM.authorField.setText("2");
        ARBM.publishinghouseField.setText("");
        ARBM.AddBookAction();
        assertEquals("Please type in a publishing house!",ARBM.message.getText());

        ARBM.titleField.setText("1");
        ARBM.authorField.setText("2");
        ARBM.publishinghouseField.setText("3");
        ARBM.dateField.setText("");
        ARBM.AddBookAction();
        assertEquals("Please type in a date!",ARBM.message.getText());

        ARBM.titleField.setText("1");
        ARBM.authorField.setText("2");
        ARBM.publishinghouseField.setText("3");
        ARBM.dateField.setText("4");
        ARBM.AddBookAction();
        assertEquals("Please select a category!",ARBM.message.getText());
    }

}