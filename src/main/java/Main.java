import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main extends Application{
    public static void copyFile(File src, File dest) throws IOException{
        FileUtils.copyFileToDirectory(src,dest);
    }
    public static void main(String [] args)
    {
        File user=new File("src\\main\\resources\\users.json").getAbsoluteFile();
        File books=new File("src\\main\\resources\\LibraryBooks.json").getAbsoluteFile();
        File userbooks=new File("src\\main\\resources\\UsersBooks.json").getAbsoluteFile();
        File to=new File("target\\src\\main\\resources");
        try
        {
            copyFile(user,to);
            copyFile(books,to);
            copyFile(userbooks,to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);

    }
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Library Administration");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


}