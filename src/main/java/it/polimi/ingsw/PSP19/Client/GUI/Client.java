package it.polimi.ingsw.PSP19.Client.GUI;

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

    double getPercentage(double value, double percentage) {
        return value / 100 * percentage;
    }

    double getCenteredX(double nodeWidth) {
        return width / 2 - nodeWidth / 2;
    }

    void drawPage() {

        ImageView header = (ImageView) root.lookup("#header");
        header.setLayoutX(getCenteredX(header.getImage().getWidth()));
        header.setLayoutY(getPercentage(height, 5));

        TextField textField1 = (TextField) root.lookup("#textField1");
        textField1.setPrefWidth(getPercentage(width, 25));
        textField1.setLayoutX(getCenteredX(textField1.getPrefWidth()));
        textField1.setLayoutY(getPercentage(height, 38));

        Text text1 = (Text) root.lookup("#text1");
        text1.setLayoutX(textField1.getLayoutX());
        text1.lookup("#text1").setLayoutY(getPercentage(height, 35));

        TextField textField2 = (TextField) root.lookup("#textField2");
        textField2.setPrefWidth(getPercentage(width, 25));
        textField2.setLayoutX(getCenteredX(textField2.getPrefWidth()));
        textField2.setLayoutY(getPercentage(height, 53));

        Text text2 = (Text) root.lookup("#text2");
        text2.setLayoutX(textField1.getLayoutX());
        text2.setLayoutY(getPercentage(height, 50));

        Text text3 = (Text) root.lookup("#text3");
        text3.setLayoutX(textField1.getLayoutX());
        text3.setLayoutY(getPercentage(height, 65));

        root.lookup("#3PlayersCheckBox").setLayoutX(textField1.getLayoutX()+200);
        root.lookup("#3PlayersCheckBox").setLayoutY(getPercentage(height, 63));


        Button button = (Button) root.lookup("#playBtn");
        button.setPrefWidth(getPercentage(width, 25));
        button.setLayoutX(getCenteredX(button.getPrefWidth()));
        button.setLayoutY(getPercentage(height, 80));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/Welcome/Welcome.fxml"));
        root.getStylesheets().add(getClass().getResource("/Welcome/Welcome.css").toExternalForm());

        drawPage();

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = (double) newVal;
            drawPage();
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = (double)newVal;
            drawPage();
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
