import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String [] args)
    {
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