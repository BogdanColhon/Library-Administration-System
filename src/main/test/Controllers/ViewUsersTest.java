package Controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

public class ViewUsersTest extends ApplicationTest {
    ViewUsers VU;
    @Before
    public void setUp() throws Exception {
        VU= new ViewUsers();
        VU.display= new ListView();
        VU.RB1=new RadioButton();
        VU.RB2=new RadioButton();
        VU.RB3=new RadioButton();
        VU.RB4=new RadioButton();
        VU.searchfield=new TextField();
    }

    @After
    public void tearDown() throws Exception {
        VU= null;

    }

    @Test

    public void calculateTimeLeftTest() throws ParseException {
        // iau data curenta si cu valoarea aceasta apelez metoda , care ar trebui sa returneze 14 zile in secunde adica 1209600;
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Calendar current = Calendar.getInstance();
        String current_date = format.format(current.getTime());
        long remaining = VU.calculateRemainTime(current_date,VU.currentTime());
        assertEquals(1209600,remaining);
    }

    @Test

    public void testRadiobutton1() throws IOException, ParseException {
        long SETDATE=1592600791000L;// a trebuit sa fixez o data, ca altfel, lista se actualiza mereu si se putea sa nu se mai atinga toate cazurile
        String path = "src\\main\\test\\resources\\UsersBooks.json";
        VU.RB_all(path,"",SETDATE);
        ObservableList list =VU.display.getItems();
        assertEquals("casian_ciuban - The Three Musketeers - Alexandre Dumas - Remain time: -13 days ",list.get(0).toString());
        assertEquals("casian_ciuban - The Memoirs of Sherlock Holmes - Arthur Conan Doyle - Remain time: 6 days ",list.get(2).toString());
    }

    @Test
    public void testRadiobutton2() throws IOException, ParseException {
        long SETDATE=1592600791000L;
        String path = "src\\main\\test\\resources\\UsersBooks.json";
        VU.RB_good(path,"",SETDATE);
        ObservableList list =VU.display.getItems();
        assertEquals("casian_ciuban - The Memoirs of Sherlock Holmes - Arthur Conan Doyle - Remain time: 6 days ", list.get(0).toString());

    }

    @Test
    public void testRadiobutton3() throws IOException, ParseException {
        long SETDATE=1592600791000L;
        String path = "src\\main\\test\\resources\\UsersBooks.json";
        VU.RB_atlimit(path,"",SETDATE);
        ObservableList list =VU.display.getItems();
        assertEquals("colhon_bogdan - The Three Musketeers - Alexandre Dumas - Remain time: 2 days ",list.get(0).toString());

    }

    @Test
    public void testRadiobutton4() throws IOException, ParseException {
        long SETDATE=1592600791000L;
        String path = "src\\main\\test\\resources\\UsersBooks.json";
        VU.RB_overdue(path,"",SETDATE);
        ObservableList list =VU.display.getItems();
        if(list.get(0).toString()!=null)
            assertEquals("casian_ciuban - The Three Musketeers - Alexandre Dumas - Remain time: -13 days ",list.get(0).toString());

    }

    @Test
    public void testSearch1() throws IOException, ParseException {
        VU.RB1.setSelected(true);
        long SETDATE=1592600791000L;
        VU.searchAction("src\\main\\test\\resources\\UsersBooks.json","cas",SETDATE);
        ObservableList list =VU.display.getItems();
            assertEquals("casian_ciuban - The Three Musketeers - Alexandre Dumas - Remain time: -13 days ",list.get(0).toString());

    }

    @Test
    public void testSearch2() throws IOException, ParseException {
        VU.RB2.setSelected(true);
        long SETDATE=1592600791000L;
        VU.searchAction("src\\main\\test\\resources\\UsersBooks.json","cas",SETDATE);
        ObservableList list =VU.display.getItems();
        assertEquals("casian_ciuban - The Memoirs of Sherlock Holmes - Arthur Conan Doyle - Remain time: 6 days ",list.get(0).toString());

    }

    @Test
    public void testSearch3() throws IOException, ParseException {
        VU.RB3.setSelected(true);
        long SETDATE=1592600791000L;
        VU.searchAction("src\\main\\test\\resources\\UsersBooks.json","c",SETDATE);
        ObservableList list =VU.display.getItems();
        assertEquals("colhon_bogdan - The Three Musketeers - Alexandre Dumas - Remain time: 2 days ",list.get(0).toString());

    }

    @Test
    public void testSearch4() throws IOException, ParseException {
        VU.RB4.setSelected(true);
        long SETDATE=1592600791000L;
        VU.searchAction("src\\main\\test\\resources\\UsersBooks.json","cas",SETDATE);
        ObservableList list =VU.display.getItems();
        assertEquals("casian_ciuban - The Three Musketeers - Alexandre Dumas - Remain time: -13 days ",list.get(0).toString());

    }





}