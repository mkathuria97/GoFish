public class NaiveStrategy implements PlayerStrategy{

    private int currentPlayerNumber;
    private int totalNumberOfPlayers;

    @Override
    public void initialize(int yourPlayerNumber, int totalNumberOfPlayers) {
        this.currentPlayerNumber = yourPlayerNumber;
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    @Override
    public Play doTurn(Hand hand){
        int randomTargetPlayerNumber;
        do {
            randomTargetPlayerNumber = (int) (Math.random() * totalNumberOfPlayers);
        }while(currentPlayerNumber == randomTargetPlayerNumber);
        int randomCardNumber = (int) (Math.random() * hand.size());
        int targetRank = hand.getCard(randomCardNumber).getRank();
        return new Play(randomTargetPlayerNumber, targetRank);
    }

    @Override
    public void playOccurred(RecordedPlay recordedPlay) {

    }
}
