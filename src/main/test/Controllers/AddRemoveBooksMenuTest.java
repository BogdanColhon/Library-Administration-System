package Controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class AddRemoveBooksMenuTest {

    AddRemoveBooksMenu ARBM;
    @Before
    public void setUp() throws Exception {
        ARBM = new AddRemoveBooksMenu();
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
        assertEquals(1, am_gasit);

    }

    @Test
    public void testAddExistingBook(){

        // Citesc din fisier si voi folosi prima carte citita
        // Daca amountul ei va creste cu 1 dupa apel atunci metoda functioneaza corect
        // Pt a verifica voi citi din nou fisierul intr-o lista noua
        // La final voi rescrie fisierul cu lista initiala, ca acesta sa ramana nemodificat dupa acest test

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

        JSONObject first;
        first = (JSONObject) list.get(0);
        //System.out.println(first.get("amount"));

        ARBM.AddInFile(first.get("title").toString(),first.get("author").toString(),first.get("publishinghouse").toString(),first.get("date").toString(),first.get("category").toString(),"src\\main\\test\\resources\\LibraryBooks.json");

        // A doua citire
        JSONArray list1 = new JSONArray();
        JSONParser parser1 = new JSONParser();
        try( Reader reader1 = new FileReader("src\\main\\test\\resources\\LibraryBooks.json")){

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

        JSONObject first1;
        first1 = (JSONObject) list1.get(0);
        //System.out.println(first1.get("amount"));

        // Pun in fisier prima lista pt a ramane neschimbat
        try (FileWriter file = new FileWriter("src\\main\\test\\resources\\LibraryBooks.json")) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(first.get("amount").hashCode() + 1,first1.get("amount").hashCode());
    }
}