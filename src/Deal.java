import java.util.ArrayList;
import java.util.List;

public class Deal {
    private int totalNumberOfPlayers;
    private String[] playerStrategies;
    private Deck deck;
    private ArrayList<Player> players;
    private int handSize;
    private int dealer;

    public Deal(String[] playerStrategies, Deck deck){
        this.totalNumberOfPlayers = playerStrategies.length;
        assert totalNumberOfPlayers < 2 || totalNumberOfPlayers >5 : "Minimum number of players should be 2" +
                "and maximum number of players should be 5";
        this.playerStrategies = playerStrategies;
        this.deck = deck;
        this.players = new ArrayList<Player>();
        this.dealer = -1;
        if (totalNumberOfPlayers == 2 || totalNumberOfPlayers == 3) {
            handSize = 7;
        } else if (totalNumberOfPlayers == 4 || totalNumberOfPlayers == 5) {
            handSize = 5;
        }


    }

    public int getDealerPlayerNumber(){
        int minRank = 14;
        for(int playerNumber = 0; playerNumber < totalNumberOfPlayers; playerNumber++){
            int randomCardNumber = (int) (Math.random() * deck.cards.size());
            Card randomCard = deck.cards.get(randomCardNumber);
            deck.cards.remove(randomCardNumber);
            Player player = new Player(playerNumber + 1, playerStrategies[0]);
            player.addCardToHand(randomCard);
            players.add(player);
            if(randomCard.getRank() < minRank){
                minRank = randomCard.getRank();
                dealer = playerNumber;
            }
        }
        return dealer;
    }

    public ArrayList<Player> getPlayersAndHands(){
        int startingPlayerNumber = switchCurrentPlayer(dealer);
        int currentPlayerNumber = startingPlayerNumber;
        for(int i = 0; i < handSize - 1; i++){
            do {
                int randomCardNumber = (int) (Math.random() * deck.cards.size());
                Card randomCard = deck.cards.get(randomCardNumber);
                deck.cards.remove(randomCardNumber);
                Player currentPlayer = players.get(currentPlayerNumber);
                currentPlayer.addCardToHand(randomCard);
                currentPlayerNumber = switchCurrentPlayer(currentPlayerNumber);
            } while(currentPlayerNumber != startingPlayerNumber);
        }
        return players;
    }

    public int switchCurrentPlayer(int currentPlayerNumber){
        if (currentPlayerNumber == 0) {
            currentPlayerNumber = totalNumberOfPlayers - 1;
        } else {
            currentPlayerNumber--;
        }
        return currentPlayerNumber;
    }
}
