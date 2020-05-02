package it.polimi.ingsw.PSP19.Client.CLI;

public enum StringColor {
    ANSI_BLUE ("\u001B[34m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN ("\u001B[32m"),
    ANSI_YELLOW ("\u001B[93m"),
    ANSI_WHITE ("\u001B[30m"),
    ANSI_PINK ("\u001B[95m"),
    BACKGROUND_BLUE ("\u001B[44m" + "\u001B[1m"),
    BACKGROUND_RED("\u001B[41m" + "\u001B[1m"),
    BACKGROUND_GREEN ("\u001B[42m" + "\u001B[1m"),
    BACKGROUND_YELLOW ("\u001B[103m" + "\u001B[1m"),
    BACKGROUND_WHITE ("\u001B[40m" + "\u001B[37m" + "\u001B[1m"),
    BACKGROUND_PINK ("\u001B[105m" + "\u001B[1m");


    static final String RESET = "\u001B[0m";
    private String escape;
    StringColor(String escape)
    {
        this.escape = escape;
    }
    public String getEscape()
    {
        return escape;
    }
    @Override
    public String toString()
    {
        return escape;
    }
}
