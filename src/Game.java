import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;

public class Game {

    //stores the player number of the player who has the turn
    private int currentPlayerNumber;

    //stores the list of players in the game
    private ArrayList<Player> players;

    //stores all the players in the game along with their final score
    private Map<String, Integer> scores;

    private Deck deck;

    /**
     *
     * @param currentPlayerNumber player number of the player who has the turn
     * @param players list of players in the game
     * @param deck
     */
    public Game(int currentPlayerNumber, ArrayList<Player> players, Deck deck){
        this.currentPlayerNumber = currentPlayerNumber;
        this.players = players;
        this.scores = new TreeMap<>();
        this.deck = deck;
    }

    /**
     *
     * @return map containing all the players in the game along with their final score
     */
    public Map<String, Integer> playGame(){
        PlayerStrategy playerStrategy;

        while(players.size() > 0){
            if(currentPlayerNumber < players.size() && players.get(currentPlayerNumber).getPlayerHand().size() != 0) {
                Player currentPlayer = players.get(currentPlayerNumber);

                if (currentPlayer.getStrategy().equalsIgnoreCase("smart")) {
                    playerStrategy = new SmartStrategy();
                } else {
                    playerStrategy = new NaiveStrategy();
                }

                int totalNumberOfPlayers = players.size();
                playerStrategy.initialize(currentPlayerNumber, totalNumberOfPlayers);
                Hand currentPlayerHand = currentPlayer.getPlayerHand();
                System.out.print(currentPlayer.getPlayerName() + "   ");
                System.out.print(deck.cards.size()+ "  ");
                System.out.print(currentPlayerHand+ "  ");

                Play play = playerStrategy.doTurn(currentPlayerHand);
                int targetPlayerNumber = play.getTargetPlayer();
                Player targetPlayer = players.get(targetPlayerNumber);
                System.out.print(targetPlayer.getPlayerName()+ "  ");
                System.out.print(targetPlayer.getPlayerHand());
                int targetRank = play.getRank();
                System.out.print(targetRank+ " ");
                ArrayList<Card> cardsFromOpponent = getCardsFromOpponent(targetRank, targetPlayer);
                RecordedPlay round = new RecordedPlay(currentPlayerNumber, targetPlayerNumber, targetRank, cardsFromOpponent);
                playerStrategy.playOccurred(round);
                String currentPlayerName = currentPlayer.getPlayerName();
                String targetPlayerName = targetPlayer.getPlayerName();
                int numCardsFromOpponent = cardsFromOpponent.size();
                System.out.println(currentPlayerName + " asks " + targetPlayerName + " for " + targetRank +"s and got "
                        + numCardsFromOpponent + " card(s).");

                if (targetPlayer.getRankAndItsFrequency().containsKey(targetRank)) {
                    updatePlayerHands(currentPlayer, targetPlayer, targetRank, cardsFromOpponent);
                } else {
                    drawCardAndSwitchPlayer(currentPlayer);
                }

                checkHandAndUpdateScore();
            }else {
                currentPlayerNumber = switchCurrentPlayer(currentPlayerNumber);
            }

        }
        return scores;
    }

    /**
     *
     * @param currentPlayer player who has the turn
     * @param targetPlayer player from whom the current player asks for cards
     * @param targetRank the rank the current player asks from the target player
     * @param cardsFromOpponent list of cards returned from the opponent player
     */
    private void updatePlayerHands(Player currentPlayer, Player targetPlayer, int targetRank, ArrayList<Card> cardsFromOpponent){

        currentPlayer.addCardsToHand(cardsFromOpponent);

        if (currentPlayer.getPlayerHand().size() == 0 && deck.cards.isEmpty()) {
            currentPlayerNumber = switchCurrentPlayer(currentPlayerNumber);
        }

        targetPlayer.removeCardsOfTargetRank(targetRank);
    }

    private void drawCardAndSwitchPlayer(Player currentPlayer){
        if (deck.cards.size() != 0) {
            Card topCardOfStock = deck.draw();
            currentPlayer.addCardToHand(topCardOfStock);
        }

        currentPlayerNumber = switchCurrentPlayer(currentPlayerNumber);
    }

    /**
     *
     * @param currentPlayerNumber
     * @return
     */
    private int switchCurrentPlayer(int currentPlayerNumber){
        int totalNumberOfPlayers = players.size();

        if (currentPlayerNumber == 0) {
            currentPlayerNumber = totalNumberOfPlayers - 1;
        } else {
            currentPlayerNumber--;
        }

        return currentPlayerNumber;
    }


    private ArrayList<Card> getCardsFromOpponent(int targetRank, Player targetPlayer){

        Hand targetPlayerHand = targetPlayer.getPlayerHand();
        List<Card> cardsInHand = targetPlayerHand.cards;

        ArrayList<Card> cardsOfTargetRank = new ArrayList<Card>();

        for(int i = 0; i < cardsInHand.size(); i++){
            Card card = cardsInHand.get(i);
            if(card.getRank() == targetRank){
                cardsOfTargetRank.add(card);
            }
        }

        return cardsOfTargetRank;
    }

    private void checkHandAndUpdateScore(){
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getPlayerHand().size() == 0 && deck.cards.isEmpty()){
                scores.put(player.getPlayerName(), player.getScore());
                players.remove(i);
                i--;
            }
        }
    }
}
