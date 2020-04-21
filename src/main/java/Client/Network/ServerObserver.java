package Client.Network;

import Server.Model.*;

public interface ServerObserver {
    void receiveNewPlayerConnected(Player player, String gameID);

    void receiveDivinities(String divinities);

    void receivePossibleDivinities(String response);

    void receivePawn(String pawn);

    void receiveMoves(String moves);

    void receiveEndGame(String endGame);

    void receiveModelUpdate(Game game);
}