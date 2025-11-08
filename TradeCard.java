import java.util.Map;
import java.util.EnumMap;
import java.util.ArrayList;
import java.util.List;
public class TradeCard extends Card {
    private final Map<Gem, Integer> cost;
    private final Map<Gem, Integer> value;
    public TradeCard(Map<Gem, Integer> cost, Map<Gem, Integer> value) {
        this.cost = new EnumMap<>(Gem.class);
        if (cost != null) {
            this.cost.putAll(cost);
        }
        this.value = new EnumMap<>(Gem.class);
        if (value != null) {
            this.value.putAll(value);
        }
    }
    @Override
    public Map<Gem, Integer> cost() {
        return this.cost;
    }

    public Map<Gem, Integer> value() {
        return this.value;
    }
    @Override
    public String toString() {
        return "Trade: " + formatGemMap(this.cost) + " -> " + formatGemMap(this.value);
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
