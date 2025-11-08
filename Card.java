import java.util.Map;

public abstract class Card {
    public abstract Map<Gem, Integer> cost();
    @Override
    public abstract String toString();
}
