package Client;

public enum Pages {
    WELCOME,                 //initial page
    LOBBY,                   //waiting for other players to join the game
    DIVINITIESCHOICE,        //choosing the in game divinities
    DIVINITYCHOICE,          //choosing the player divinity
    STARTINGPOSITIONCHOICE,  //choosing the starting position
    GAME,                    //playing
    ENDGAME,                 //final page
    LOADINGWELCOMEDATA,      //loading data from server
    LOADINGDIVINITIES,
    LOADINGDIVINITY,
}
