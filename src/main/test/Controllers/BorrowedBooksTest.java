package Controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BorrowedBooksTest extends ApplicationTest {

    BorrowedBooks BB;
    LoginController LC;
    @Before
    public void setUp() throws Exception {
        BB= new BorrowedBooks();
        BB.Display = new ListView();
        BB.FilePath = "src/main/test/resources/UsersBooks.json";

        LC = new LoginController();
        LC.UsernameGlobal="casian_ciuban";
    }

    @After
    public void tearDown() throws Exception {
        BB = null;
        LC=null;
    }

    @Test
    public void calculateTimeLeftTest(){
        // iau data curenta si cu valoarea aceasta apelez metoda , care ar trebui sa returneze 14 zile in secunde adica 1209600;
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");
        java.util.Date borrowedDate;
        Calendar current = Calendar.getInstance();
        java.util.Date currentDate;
        String current_date = format.format(current.getTime());
        long wanted = BB.calculateRemainTime(current_date);
        assertEquals(1209600,wanted);
    }

    @Test
    public void displayedBooksTest(){
        //The Three Musketeers - Alexandre Dumas       PLEASE RETURN THIS BOOK !!!
        BB.DisplayBorrowedBooks();
        ObservableList list =BB.Display.getItems();
        assertEquals("The Three Musketeers - Alexandre Dumas       PLEASE RETURN THIS BOOK !!!",list.get(0).toString());

    }
}