package Controllers;

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
import java.util.Iterator;

import static org.junit.Assert.*;

public class RemoveBooksMenuTest extends ApplicationTest {

    RemoveBooksMenu RBM;
    @Before
    public void setUp() throws Exception {
        RBM = new RemoveBooksMenu();
        RBM.titleField=new TextField();
        RBM.authorField=new TextField();
        RBM.dateField=new TextField();
        RBM.message=new Text();
    }

    @After
    public void tearDown() throws Exception {
        RBM = null;
    }

    @Test
    public void testRemoveAllFromFile() throws FileNotFoundException {

        // Citesc intr-o lista fisierul, salvez prima carte
        //apelez metoda cu prima carte si salvez val returnata ,daca aceasta e 0 inseamna ca nu a gasit cartea cea ce e gresit
        //citesc din fisier dinou si verific daca a fost stearsa cartea, daca nu e gresit

        String path = "src\\main\\test\\resources\\LibraryBooks.json";
        String title;
        String author;
        String date;

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

        JSONObject first = (JSONObject) list.get(0);
        title = first.get("title").toString();
        author = first.get("author").toString();
        date = first.get("date").toString();

        int gasit = RBM.RemoveAllFromFile(title,author,date,path);

        JSONArray list1 = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try( Reader reader1 = new FileReader(path)){

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

        // Caut cartea daca o gasesc e gresit
        int am_gasit=0;
        Iterator<Object> it = list1.iterator();
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();

            if ((am_gasit == 0) && (title.equals(obj.get("title")))
                    && (author.equals(obj.get("author")))
                    && (date.equals(obj.get("date")))){

                am_gasit = 1;
            }
        }

        try (FileWriter file = new FileWriter(path)) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertTrue((gasit == 1 && am_gasit == 0));
    }

    @Test
    public void testRemoveFromFile() throws FileNotFoundException {

        String path = "src\\main\\test\\resources\\LibraryBooks.json";
        String title;
        String author;
        String date;
        int amount;

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

        JSONObject first = (JSONObject) list.get(0);
        title = first.get("title").toString();
        author = first.get("author").toString();
        date = first.get("date").toString();
        amount = first.get("amount").hashCode();

        int gasit = RBM.RemoveFromFile(title,author,date,path);

        JSONArray list1 = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try( Reader reader1 = new FileReader(path)){

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

        int amount1 = 0;
        Iterator<Object> it = list1.iterator();
        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();

            if ((title.equals(obj.get("title")))
                    && (author.equals(obj.get("author")))
                    && (date.equals(obj.get("date")))){
                amount1 = obj.get("amount").hashCode();
            }
        }

        try (FileWriter file = new FileWriter(path)) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(amount-1 , amount1);
    }

    @Test
    public void testNoFildsMassagesRemoveAllBooksAction() throws FileNotFoundException {

        RBM.titleField.setText("");
        RBM.RemoveAllBooksAction ();
        assertEquals("Please type in a title!",RBM.message.getText());

        RBM.titleField.setText("1");
        RBM.authorField.setText("");
        RBM.RemoveAllBooksAction ();
        assertEquals("Please type in the author!",RBM.message.getText());

        RBM.titleField.setText("1");
        RBM.authorField.setText("2");
        RBM.dateField.setText("");
        RBM.RemoveAllBooksAction ();
        assertEquals("Please type in a date!",RBM.message.getText());

        /*RBM.titleField.setText("1");
        RBM.authorField.setText("2");
        RBM.dateField.setText("3");
        RBM.RemoveAllBooksAction ();
        assertEquals("Book does not exist!",RBM.message.getText());*/
    }

    @Test
    public void testNoFildsMassagesRemoveBookAction() throws FileNotFoundException {

        RBM.titleField.setText("");
        RBM.RemoveBookAction ();
        assertEquals("Please type in a title!",RBM.message.getText());

        RBM.titleField.setText("1");
        RBM.authorField.setText("");
        RBM.RemoveBookAction ();
        assertEquals("Please type in the author!",RBM.message.getText());

        RBM.titleField.setText("1");
        RBM.authorField.setText("2");
        RBM.dateField.setText("");
        RBM.RemoveBookAction ();
        assertEquals("Please type in a date!",RBM.message.getText());

        /*RBM.titleField.setText("1");
        RBM.authorField.setText("2");
        RBM.dateField.setText("3");
        RBM.RemoveBookAction ();
        assertEquals("Book does not exist!",RBM.message.getText());*/
    }
}