public class Play {

    private int targetPlayer;
    private int rank;

    public Play(int targetPlayer, int rank) {
        this.targetPlayer = targetPlayer;
        this.rank = rank;
    }

    public int getTargetPlayer() {
        return targetPlayer;
    }

    public int getRank() {
        return rank;
    }
}
