package it.polimi.ingsw.PSP19.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Welcome/Welcome.fxml"));
        root.getStylesheets().add(getClass().getResource("/Welcome/Welcome.css").toExternalForm());
        Scene scene = new Scene(root, 1500, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SANTORINI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
