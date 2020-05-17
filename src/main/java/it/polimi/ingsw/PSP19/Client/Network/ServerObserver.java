package it.polimi.ingsw.PSP19.Client.Network;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Player;

import java.io.IOException;

public interface ServerObserver {

    void receiveUsernameTaken(String Response);

    void receiveNewPlayerConnected(Player player, String gameID);

    void receiveDivinities(String divinities) throws IOException;

    void receivePossibleDivinities(String response) throws IOException;

    void receiveCanComeUp(String canComeUp);

    void receivePawn(String pawn);

    void receiveStartingPosition(String position);

    void receiveMoves(String moves);

    void receiveEndGame(String endGame);

    void receiveModelUpdate(Game game) throws IOException;
}