package Client.Network;

import Server.Model.*;

public interface ServerObserver {
    void receiveNewPlayerConnected(Player player,String gameID);

    void receiveDivinities(String divinities);

    void receivePossibleDivinities(String response);

    void receiveMoves(MoveList moves, Grid grid);

    void receiveEndGame(Grid grid);

    void receiveModelUpdate(Game game);
}