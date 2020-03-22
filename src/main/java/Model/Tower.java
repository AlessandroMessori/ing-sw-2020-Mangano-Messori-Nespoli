package Model;

public class Tower {
    private int level;
    private boolean isDome;

    /**
     *
     * @return value of Level
     */
    public int getLevel() {
        return level;
    }

    /**
     * set value of Level
     * @param level value of Level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @return value of isDome
     */
    public boolean getIsDome() {
        return isDome;
    }

    /**
     * set value of isDome
     * @param dome value of isDome
     */
    public void setIsDome(boolean dome) {
        isDome = dome;
    }

    /**
     * constructor
     * @param level value of Level
     * @param isDome value of isDome
     */
    public Tower(int level, boolean isDome) {
        this.level = level;
        this.isDome = isDome;
    }
}
