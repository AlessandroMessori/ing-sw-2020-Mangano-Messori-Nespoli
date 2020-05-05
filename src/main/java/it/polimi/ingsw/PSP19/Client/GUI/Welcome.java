package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.GuiHelper;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Welcome extends GamePage {

    private Game game;

    public Welcome() {
    }

    public void setGame(Game g) {
        game = g;
    }

    public String getPageName() {
        return "Welcome";
    }

    public void playBtnClick() {
        System.out.println("Clicked the Play Button");
    }

    public void drawPage(Parent root, double width, double height) {
        ImageView header = (ImageView) root.lookup("#header");
        header.setLayoutX(GuiHelper.getCenteredX(width, header.getImage().getWidth()));
        header.setLayoutY(GuiHelper.getPercentage(height, 5));

        TextField textField1 = (TextField) root.lookup("#textField1");
        textField1.setPrefWidth(GuiHelper.getPercentage(width, 25));
        textField1.setLayoutX(GuiHelper.getCenteredX(width, textField1.getPrefWidth()));
        textField1.setLayoutY(GuiHelper.getPercentage(height, 38));

        Text text1 = (Text) root.lookup("#text1");
        text1.setLayoutX(textField1.getLayoutX());
        text1.lookup("#text1").setLayoutY(GuiHelper.getPercentage(height, 35));

        TextField textField2 = (TextField) root.lookup("#textField2");
        textField2.setPrefWidth(GuiHelper.getPercentage(width, 25));
        textField2.setLayoutX(GuiHelper.getCenteredX(width, textField2.getPrefWidth()));
        textField2.setLayoutY(GuiHelper.getPercentage(height, 53));

        Text text2 = (Text) root.lookup("#text2");
        text2.setLayoutX(textField1.getLayoutX());
        text2.setLayoutY(GuiHelper.getPercentage(height, 50));

        Text text3 = (Text) root.lookup("#text3");
        text3.setLayoutX(textField1.getLayoutX());
        text3.setLayoutY(GuiHelper.getPercentage(height, 65));

        root.lookup("#3PlayersCheckBox").setLayoutX(textField1.getLayoutX() + 200);
        root.lookup("#3PlayersCheckBox").setLayoutY(GuiHelper.getPercentage(height, 63));


        Button button = (Button) root.lookup("#playBtn");
        button.setPrefWidth(GuiHelper.getPercentage(width, 25));
        button.setLayoutX(GuiHelper.getCenteredX(width, button.getPrefWidth()));
        button.setLayoutY(GuiHelper.getPercentage(height, 80));
    }

}
