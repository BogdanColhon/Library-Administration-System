import Services.FileSystemService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

public class Main extends Application{
    public static void main(String [] args)
    {
        launch(args);

    }
    public void start(Stage primaryStage) throws Exception
    {
        FileUtils.copyURLToFile(Main.class.getClassLoader().getResource("users.json"), FileSystemService.getPathToFile("classes", "users.json").toFile());
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Library Administration");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


}