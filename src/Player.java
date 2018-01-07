import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Player {

    //stores the player's number
    private int playerNumber;

    //stores the player's strategy to play the game
    private String strategy;

    //stores the list of cards in the player's hand
    private List<Card> hand;

    //stores the player's score
    private int score;

    /**
     *
     * @param playerNumber player's number
     * @param strategy player's strategy to play the game
     */
    public Player(int playerNumber, String strategy){
        this.playerNumber = playerNumber;
        this.strategy = strategy;
        this.hand = new ArrayList<Card>();
        score = 0;
    }

    /**
     *
     * @return player's name
     */
    public String getPlayerName() {
        return "Player" + playerNumber;
    }

    /**
     *
     * @return player's strategy
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     *
     * @return player's hand
     */
    public Hand getPlayerHand() {
        return new Hand(hand);
    }

    /**
     *
     * @param card
     * adds the given card to the player's hand
     */
    public void addCardToHand(Card card){
        hand.add(card);
        calcScoreAndRemoveBook();
    }

    /**
     *
     * @param cards list of cards
     * add list of cards to the player's hand
     */
    public void addCardsToHand(List<Card> cards){
        hand.addAll(cards);
        calcScoreAndRemoveBook();
    }

    /**
     *
     * updates the score if the player wins a book and removes the cards of that rank from the player's hand
     */
    private void calcScoreAndRemoveBook(){
        //stores the ranks present in the player's hand along with their frequency
        Map<Integer, Integer> rankAndItsFrequency = getRankAndItsFrequency();

        for(int rank: rankAndItsFrequency.keySet()){
            //stores the frequency of the rank in the player's hand
            int frequency = rankAndItsFrequency.get(rank);

            int book = 4;

            if (frequency == book) {
                score++;
                removeCardsOfTargetRank(rank);
            }
        }
    }

    /**
     *
     * @return map containing ranks present in the player's hand along with their frequency
     */
    public Map<Integer, Integer> getRankAndItsFrequency(){
        //stores the ranks present in the player's hand along with their frequency
        Map<Integer, Integer> rankAndItsFrequency = new HashMap<Integer, Integer>();

        for(Card card: hand){
            //stores the rank of the card in the player's hand
            int rank = card.getRank();

            if (rankAndItsFrequency.containsKey(rank)) {
                int frequency = rankAndItsFrequency.get(rank);
                frequency++;
                rankAndItsFrequency.put(rank, frequency);
            } else {
                rankAndItsFrequency.put(rank, 1);
            }
        }

        return rankAndItsFrequency;
    }

    /**
     *
     * @param removeRank rank that is supposed to be removed from the player's hand
     * remove cards of the given rank from the player's hand
     */
    public void removeCardsOfTargetRank(int removeRank){
        for(int i = 0; i < hand.size(); i++){
            //stores the rank of the card in the player's hand
            int cardRank = hand.get(i).getRank();

            if (cardRank == removeRank) {
                hand.remove(i);
                i--;
            }
        }
    }

    /**
     *
     * @return player's score
     */
    public int getScore(){
        return score;
    }
}
