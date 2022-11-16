package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//Javadoc files will be submitted in a folder titled "Javadoc"

/** This is the main class. Will set the initial stage and present the First Screen fxml.
 *
 * FUTURE ENHANCEMENT: As the inventory of parts and products grows, it would be useful to be able to delete multiple parts at one. Select
 * all the object that you would want to delete, and on the confirmation alert, it could list all selected parts with a checkbox next to them. That
 * way if you see one that you didn't want to delete, you don't have to go back and re-select the parts. The user could just uncheck the part that
 * they don't actually want to delete.
 *
 * */
public class Main extends Application {

    /** This is where the First Screen is loaded, set, and shown */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }


    /** Launches the application
     *
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }
}
