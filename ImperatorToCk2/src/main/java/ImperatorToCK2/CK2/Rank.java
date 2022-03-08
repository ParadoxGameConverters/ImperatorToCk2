package ImperatorToCK2.CK2;

public enum Rank {
    BARONY('b'),
    COUNTY('c'),
    DUCHY('d'),
    KINGDOM('k'),
    EMPIRE('e');

    private char letter;

    Rank(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }
}