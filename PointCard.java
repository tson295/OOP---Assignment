import java.util.Map;
import java.util.EnumMap;
import java.util.ArrayList;
import java.util.List;
public class PointCard extends Card {
    private final Map<Gem, Integer> cost;
    private final int points;
    public PointCard(Map<Gem, Integer> cost, int points) {
        this.cost = new EnumMap<>(Gem.class);
        if (cost != null) {
            this.cost.putAll(cost);
        }
        this.points = points;
    }
    @Override
    public Map<Gem, Integer> cost() {
        return this.cost;
    }
    public int points() {
        return this.points;
    }
    @Override
    public String toString() {
        return "Points: " + formatGemMap(this.cost) + " -> " + this.points;
    }
    private static String formatGemMap(Map<Gem, Integer> map) {
        List<String> parts = new ArrayList<>();
        for (Gem g : Gem.values()) {
            int v = map.getOrDefault(g, 0);
            if (v > 0) {
                parts.add(v + " " + g.name());
            }
        }
        if (parts.isEmpty()) {
            return "Nothing";
        }
        return String.join(", ", parts);
    }
}
