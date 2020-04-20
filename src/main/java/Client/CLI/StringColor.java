package Client.CLI;

public enum StringColor {
    ANSI_BLUE ("\u001B[34m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN ("\u001B[32m"),
    ANSI_YELLOW ("\u001B[93m"),
    ANSI_WHITE ("\u001B[37m"),
    ANSI_PINK ("\u001B[95m");


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
