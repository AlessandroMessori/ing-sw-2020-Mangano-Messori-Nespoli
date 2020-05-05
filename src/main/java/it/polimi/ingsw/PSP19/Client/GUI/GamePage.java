package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.scene.Parent;

public abstract class GamePage {

    public abstract String getPageName();

    public abstract void drawPage(Parent root, double width, double height);

    public abstract void setGame(Game g);

}
