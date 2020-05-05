package it.polimi.ingsw.PSP19.Client.Network;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Player;

public interface ServerObserver {

    void receiveUsernameTaken(String Response);

    void receiveNewPlayerConnected(Player player, String gameID);

    void receiveDivinities(String divinities);

    void receivePossibleDivinities(String response);

    void receiveCanComeUp(String canComeUp);

    void receivePawn(String pawn);

    void receiveMoves(String moves);

    void receiveEndGame(String endGame);

    void receiveModelUpdate(Game game);
}