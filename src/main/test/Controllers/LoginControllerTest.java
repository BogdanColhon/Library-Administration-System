package Controllers;

import Controllers.LoginController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import static org.junit.Assert.*;

public class LoginControllerTest extends ApplicationTest {
    LoginController LC;
    @Before
    public void setUp() throws Exception {
        LC = new LoginController();
        LC.passwordField=new PasswordField();
        LC.usernameField=new TextField();
        LC.loginMessage=new Text();
    }
    @After
    public void tearDown() throws Exception {
        LC = null;
    }
    @Test
    public void testEmptyFieldsMassages() {

        LC.usernameField.setText("");
        LC.handleLoginButtonAction();
        assertEquals("Please type in a username!",LC.loginMessage.getText());

        LC.usernameField.setText("x");
        LC.passwordField.setText("");
        LC.handleLoginButtonAction();
        assertEquals("Please type in a password",LC.loginMessage.getText());

    }

    @Test
    public void testReadData() {

        String username;
        String password;
        JSONArray list = new JSONArray();
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("src\\main\\test\\resources\\users.json")) {

            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            //System.out.println(jsonArray);


            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = (JSONObject) it.next();
                list.add(obj);

                //System.out.println(list.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject first = (JSONObject) list.get(0);
        username = first.get("username").toString();
        password=first.get("password").toString();
        assertEquals("mars_cosmin", username);
        assertEquals("9573BFB179AFA4CEBE1ADCD7C64C45C31ED3A3B72EE262E1196D53BAE8FFE3D5",password);


    }
}