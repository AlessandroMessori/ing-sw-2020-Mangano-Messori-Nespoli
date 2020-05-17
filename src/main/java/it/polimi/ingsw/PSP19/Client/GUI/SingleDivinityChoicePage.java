package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.CLI.StringColor;
import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Server.Model.Grid;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.CastingHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SingleDivinityChoicePage extends Page implements Initializable {

    private URL layoutURL;

    private boolean alreadySetGame = false;

    private ImageView[] divButtonsArray;
    private boolean[] clicked;

    private int players;

    @FXML
    private ImageView goBtn;

    @FXML
    private ImageView div31;

    @FXML
    private ImageView div32;

    @FXML
    private ImageView div33;

    @FXML
    private ImageView div21;

    @FXML
    private ImageView div22;

    public SingleDivinityChoicePage() {
    }

    public String getPageName() {
        return "SingleDivinityChoice";
    }

    public void setGame(Game g) {
        game = g;

        if (!alreadySetGame) {

            boolean chosen;
            final int[] finalDiv = {0};
            final String[] divinityChoice = new String[1];

            //-- put in comment this to try the page
            ArrayList<String> possibleDivinities = CastingHelper.convertDivinityListToString(game.getPossibleDivinities());
            ArrayList<String> inGameDivinities = CastingHelper.convertDivinityListToString(game.getInGameDivinities());

            System.out.println("possibleDivinities: " + possibleDivinities);
            System.out.println("inGameDivinities: " + inGameDivinities);

            if (client.getThreePlayers()) {
                players = 3;
                clicked = new boolean[]{false, false, false};
                divButtonsArray = new ImageView[]{div31, div32, div33};
                div21.setDisable(true);
                div22.setDisable(true);

            } else {
                players = 2;
                clicked = new boolean[]{false, false};
                divButtonsArray = new ImageView[]{div21, div22};
                div31.setDisable(true);
                div32.setDisable(true);
                div33.setDisable(true);
                //TwoPlayersScenario(possibleDivinities, inGameDivinities);
            }


            for (int i = 0; i < players; i++) {
                chosen = false;
                int finalI = i;

                if (!inGameDivinities.contains(possibleDivinities.get(i))) {
                    chosen = true;
                }


                String divName = possibleDivinities.get(i).substring(0, 1) + possibleDivinities.get(i).toLowerCase().substring(1);


                if (!chosen) {
                    divButtonsArray[i].setImage(new Image("/Images/DivChoice/" + divName + ".png"));
                    divButtonsArray[i].setDisable(false);
                } else {
                    divButtonsArray[i].setImage(new Image("/Images/DivChoice/" + divName + "_chosen.png"));
                    divButtonsArray[i].setDisable(true);
                }

                divButtonsArray[i].setOnMouseClicked(event -> {
                    if ((!clicked[finalI]) && (finalDiv[0] < 1)) {

                        divButtonsArray[finalI].setImage(new Image("/Images/DivChoice/" + divName + "_chosen.png"));
                        divinityChoice[0] = possibleDivinities.get(finalI).toUpperCase();
                        finalDiv[0]++;
                        clicked[finalI] = true;
                        if (finalDiv[0] == 1) {

                            goBtn.setImage(new Image("/Images/DivChoice/Go_button.png"));

                            goBtn.setDisable(false);

                            goBtn.setOnMousePressed(e -> goBtn.setImage(new Image("/Images/DivChoice/Go_button_Pressed.png")));

                            goBtn.setOnMouseClicked(e -> {
                                System.out.println(divinityChoice[0]);
                                String message = messageSerializer.serializeDivinity(CastingHelper.convertDivinity(divinityChoice[0]), client.getPlayerUsername(), game.getCodGame()).toString();
                                RequestHandler.getRequestHandler().updateRequest(Commands.SEND_DIVINITY, message);

                            /*try {
                               -- client.setCurrentPage(new LoadingPage());
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }*/
                            });

                            goBtn.setOnMouseReleased(e -> goBtn.setImage(new Image("/Images/DivChoice/Go_button.png")));
                        }
                    } else if ((clicked[finalI]) && (finalDiv[0] <= 1)) {
                        divButtonsArray[finalI].setImage(new Image("/Images/DivChoice/" + possibleDivinities.get(finalI) + ".png"));
                        divinityChoice[0] = "";
                        finalDiv[0]--;
                        clicked[finalI] = false;
                        goBtn.setImage(new Image("/Images/DivChoice/Go_button_NONactive.png"));
                        goBtn.setDisable(true);

                    }
                });

            }
            alreadySetGame = true;
        }


    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}