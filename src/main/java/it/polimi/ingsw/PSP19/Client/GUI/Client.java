package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Client extends Application {

    double width = 1500;
    double height = 900;
    Parent root;


    @Override
    public void start(Stage primaryStage) throws Exception {

        GamePage currentPage = new Welcome();
        String currentPageName = currentPage.getPageName();
        root = FXMLLoader.load(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".fxml"));
        root.getStylesheets().add(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".css").toExternalForm());

        currentPage.setGame(null);
        currentPage.drawPage(root, width, height);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = (double) newVal;
            currentPage.drawPage(root, width, height);
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = (double) newVal;
            currentPage.drawPage(root, width, height);
        });


        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.setTitle("SANTORINI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
