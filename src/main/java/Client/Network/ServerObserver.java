package Client.Network;

import Server.Model.*;

import java.util.ArrayList;

public interface ServerObserver {
    void receiveNewPlayerConnected(Player player);

    void receiveDivinities(ArrayList<Divinity> divinities);

    void receivePossibleDivinities(ArrayList<Divinity> divinities);

    void receiveMoves(MoveList moves, Grid grid);

    void receiveEndGame(Grid grid);

    void receiveModelUpdate(Game game);
}