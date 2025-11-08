import java.util.Map;
public enum Gem {
    YELLOW,
    GREEN,
    BLUE,
    PINK;
    public Gem upgrade() {
        switch (this) {
            case YELLOW:
                return GREEN;
            case GREEN:
                return BLUE;
            case BLUE:
                return PINK;
            case PINK:
            default:
                return PINK;
        }
    }
    public static String gemMapToString(Map<Gem, Integer> gems) {
        int y = gems.getOrDefault(YELLOW, 0);
        int g = gems.getOrDefault(GREEN, 0);
        int b = gems.getOrDefault(BLUE, 0);
        int p = gems.getOrDefault(PINK, 0);
        return y + " YELLOW, " + g + " GREEN, " + b + " BLUE, " + p + " PINK";
    }
}
