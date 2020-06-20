package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;


public class LoginController {

    public static String UsernameGlobal = null;
    @FXML
     Text loginMessage;
    @FXML
     PasswordField passwordField;
    @FXML
     TextField usernameField;

    @FXML
    public void initialize() {

    }


    private String algorithm="SHA-256";
    public void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Criptare x=new Criptare();
        UsernameGlobal = username;

        if (username == null || username.isEmpty()) {
            System.out.println(loginMessage.getText());
            loginMessage.setText("Please type in a username!");
            return;
        }

        if (password == null || password.isEmpty()) {
            loginMessage.setText("Please type in a password");
            return;
        }
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("src\\main\\resources\\users.json")) {

            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            //System.out.println(jsonArray);

            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext())
            {
                JSONObject obj = (JSONObject) it.next();
                //System.out.println(obj.get("username") + " - " + obj.get("password"));
                if(obj.get("username").equals(username))
                {
                    //System.out.println(x.generateHash(password,algorithm));
                    if (obj.get("password").equals(x.generateHash(password,algorithm))) {
                        if (obj.get("role").equals("librarian")) {
                            try {
                                Stage stage = (Stage) loginMessage.getScene().getWindow();
                                Parent TeacherMenu = FXMLLoader.load(getClass().getClassLoader().getResource("tmenu.fxml"));
                                Scene scene = new Scene(TeacherMenu, 600, 280);
                                stage.setScene(scene);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            return;
                        } else {
                            if (obj.get("role").equals("user")) {
                                try {
                                    Stage stage = (Stage) loginMessage.getScene().getWindow();
                                    Parent UserMenu = FXMLLoader.load(getClass().getClassLoader().getResource("umenu.fxml"));
                                    Scene scene = new Scene(UserMenu, 600, 280);
                                    stage.setScene(scene);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                return;
                            }
                        }

                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        loginMessage.setText("Incorrect credentials!");
    }
}