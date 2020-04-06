package Client.Network;

import Server.Model.Divinity;
import Server.Model.Grid;
import Server.Model.MoveList;
import Server.Model.Player;

import java.util.ArrayList;

public interface ServerObserver
{
    void receiveNewPlayerConnected(Player player);
    void receiveDivinities(ArrayList<Divinity> divinities);
    void receivePossibleDivinities(ArrayList<Divinity> divinities);
    void receiveMoves(MoveList moves,Grid grid);
    void receiveEndGame(Grid grid);
}