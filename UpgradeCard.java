import java.util.Map;
public class UpgradeCard extends Card {
    private final int upgrades;
    public UpgradeCard(int upgrades) {
        this.upgrades = upgrades;
    }
    public int upgrades() {
        return upgrades;
    }
    @Override
    public Map<Gem, Integer> cost() {
        return Map.of();
    }
    @Override
    public String toString() {
        return "Upgrade: " + upgrades;
    }
}
