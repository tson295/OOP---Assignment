import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Game {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Game <deckFile>");
            return;
        }
        List<Card> merchantCards = new ArrayList<>();
        List<PointCard> pointCards = new ArrayList<>();
        if (!loadDeck(args[0], merchantCards, pointCards)) {
            return;
        }
        Player player = new Player();
        Scanner in = new Scanner(System.in);
        System.out.println("Let's Play Decade!");
        while (true) {
            if (player.hasFivePointCards() || pointCards.isEmpty()) {
                break;
            }
            System.out.println("Would you like to:");
            System.out.println("0. Play a card");
            System.out.println("1. Rest");
            System.out.println("2. Buy a Merchant Card");
            System.out.println("3. Claim a Point Card");
            System.out.print("Enter your selection: ");
            if (!in.hasNextInt()) {
                in.nextLine();
                continue;
            }
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 0:
                    if (player.handSize() == 0) {
                        System.out.println("You cannot play that card.");
                        break;
                    }
                    System.out.println("============");
                    System.out.println("Current Hand");
                    System.out.println("============");
                    player.printHand();
                    System.out.println("============");
                    System.out.println("You have the following gems:");
                    player.printCaravan();
                    System.out.println("Which card would you like to play?");
                    System.out.print("Enter your selection: ");
                    if (!in.hasNextInt()) {
                        in.nextLine();
                        System.out.println("You cannot play that card.");
                        break;
                    }
                    int playIdx = in.nextInt();
                    in.nextLine();
                    if (!player.canPlay(playIdx)) {
                        System.out.println("You cannot play that card.");
                    } else {
                        player.play(playIdx);
                        System.out.println("You played the card.");
                        System.out.println("You have the following gems:");
                        player.printCaravan();
                    }
                    break;
                case 1:
                    player.rest();
                    System.out.println("You rested and refilled your hand.");
                    break;
                case 2:
                    System.out.println("======================");
                    System.out.println("Current Merchant Cards");
                    System.out.println("======================");
                    for (int i = 0; i < merchantCards.size(); i++) {
                        System.out.println(i + ". " + merchantCards.get(i));
                    }
                    System.out.println("======================");
                    System.out.print("You have the following gems: ");
                    player.printCaravan();
                    System.out.println("Which card would you like to buy?");
                    System.out.print("Enter your selection: ");
                    if (!in.hasNextInt()) {
                        in.nextLine();
                        System.out.println("You cannot buy that card.");
                        break;
                    }
                    int buyIdx = in.nextInt();
                    in.nextLine();
                    if (buyIdx < 0 || buyIdx >= merchantCards.size() || !player.canBuy(buyIdx)) {
                        System.out.println("You cannot buy that card.");
                    } else {
                        Card c = merchantCards.remove(buyIdx);
                        player.buy(buyIdx, c);
                        System.out.println("You bought the card.");
                    }
                    break;
                case 3:
                    System.out.println("===================");
                    System.out.println("Current Point Cards");
                    System.out.println("===================");
                    for (int i = 0; i < pointCards.size(); i++) {
                        System.out.println(i + ". " + pointCards.get(i));
                    }
                    System.out.println("===================");
                    System.out.print("You have the following gems: ");
                    player.printCaravan();
                    System.out.println("Which card would you like to claim?");
                    System.out.print("Enter your selection: ");
                    if (!in.hasNextInt()) {
                        in.nextLine();
                        System.out.println("You cannot claim that card.");
                        break;
                    }
                    int claimIdx = in.nextInt();
                    in.nextLine();
                    if (claimIdx < 0 || claimIdx >= pointCards.size()
                            || !player.canClaim(pointCards.get(claimIdx))) {
                        System.out.println("You cannot claim that card.");
                    } else {
                        PointCard p = pointCards.remove(claimIdx);
                        player.claim(p);
                        System.out.println("You claimed the card.");
                    }
                    break;
                default:
                    break;
            }
        }
        int finalScore = player.score();
        System.out.println("The player scored " + finalScore + " points!");
    }
    private static boolean loadDeck(String filename, List<Card> merchantCards, List<PointCard> pointCards) {
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty())
                    continue;
                if (line.equals("Trade")) {
                    if (!sc.hasNextLine())
                        break;
                    String costLine = sc.nextLine().trim();
                    if (!sc.hasNextLine())
                        break;
                    String valueLine = sc.nextLine().trim();
                    Map<Gem, Integer> cost = parseGemLine(costLine);
                    Map<Gem, Integer> value = parseGemLine(valueLine);
                    merchantCards.add(new TradeCard(cost, value));
                } else if (line.equals("Upgrade")) {
                    if (!sc.hasNextLine())
                        break;
                    int up = Integer.parseInt(sc.nextLine().trim());
                    merchantCards.add(new UpgradeCard(up));
                } else if (line.equals("Point")) {
                    if (!sc.hasNextLine())
                        break;
                    String costLine = sc.nextLine().trim();
                    if (!sc.hasNextLine())
                        break;
                    int pts = Integer.parseInt(sc.nextLine().trim());
                    Map<Gem, Integer> cost = parseGemLine(costLine);
                    pointCards.add(new PointCard(cost, pts));
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Deck file not found.");
            return false;
        }
    }
    private static Map<Gem, Integer> parseGemLine(String line) {
        String[] parts = line.split(",");
        Map<Gem, Integer> map = new EnumMap<>(Gem.class);
        Gem[] gems = Gem.values();
        for (int i = 0; i < parts.length && i < gems.length; i++) {
            int v = Integer.parseInt(parts[i].trim());
            if (v > 0) {
                map.put(gems[i], v);
            }
        }
        return map;
    }
}
