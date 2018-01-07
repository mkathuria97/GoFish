import java.util.ArrayList;

/**
 * Created by mayankkathuria on 3/6/17.
 */
public class SmartStrategy implements  PlayerStrategy {
    private int currentPlayerNumber;
    private int totalNumberOfPlayers;
    private ArrayList<RecordedPlay> history = new ArrayList<RecordedPlay>();

    @Override
    public void initialize(int yourPlayerNumber, int totalNumberOfPlayers) {
        this.currentPlayerNumber = yourPlayerNumber;
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    @Override
    public Play doTurn(Hand hand) {
        boolean rankFoundInHistory = false;

        for(RecordedPlay playRound: history){
            int targetPlayerNumber = playRound.getSourcePlayer();
            int rank = playRound.getRank();
            if(targetPlayerNumber != currentPlayerNumber && hand.hasRank(rank)){
                return new Play(targetPlayerNumber, rank);
            }
        }

        if(!rankFoundInHistory){
            int randomTargetPlayerNumber;
            do {
                randomTargetPlayerNumber = (int) (Math.random() * totalNumberOfPlayers);
            }while(currentPlayerNumber == randomTargetPlayerNumber);
            int randomCardNumber = (int) (Math.random() * hand.size());
            int targetRank = hand.getCard(randomCardNumber).getRank();
            return new Play(randomTargetPlayerNumber, targetRank);
        }
        return null;
    }

    @Override
    public void playOccurred(RecordedPlay recordedPlay) {
        history.add(recordedPlay);

    }
}
