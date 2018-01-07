import java.util.ArrayList;

import java.util.Map;
public class Main {
    public static void main(String[] args){
        String[] playerStrategies = args;
        Deck deck = new Deck();
        Deal deal = new Deal(playerStrategies, deck);
        int currentPlayerNumber = deal.switchCurrentPlayer(deal.getDealerPlayerNumber());
        ArrayList<Player> players = deal.getPlayersAndHands();
        Game game = new Game(currentPlayerNumber, players, deck);
        Map<String, Integer> scores = game.playGame();
        int maxScore = 0;
        String winnerName = "";
        for(String playerName: scores.keySet()){
            int playerScore = scores.get(playerName);
            if(playerScore > maxScore){
                winnerName = playerName;
                maxScore = playerScore;

            }
            System.out.println(playerName + " had " + playerScore + " points.");
        }
        System.out.println(winnerName + " won!");

    }
}
