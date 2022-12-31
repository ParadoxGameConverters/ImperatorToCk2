package imperatortock2.ck2;

import java.util.Optional;
import java.util.Arrays;

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

    public static Optional<Rank> get(String rankString) {
        if (rankString.isEmpty()) {
            return Optional.empty();
        }

        return Arrays.stream(Rank.values())
                .filter(rank -> rank.letter == rankString.charAt(0))
                .findFirst();
    }
}