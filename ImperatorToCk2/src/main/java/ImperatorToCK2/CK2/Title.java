package ImperatorToCK2.CK2;

import java.util.Optional;

public class Title {
    public Title(String imperatorTag, String imperatorColor, String government, String capital, String rank) {
        this.name = rank +"_" + imperatorTag;

        if (imperatorColor.equals("none")) {
            this.color = Optional.empty();
        } else {
            this.color = Optional.of(imperatorColor);
        }

        this.government = government;
        this.capital = capital;
        this.rank = rank;
    }

    public final String getName() {
        return name;
    }

    private final String name;
    public final Optional<String> color;
    public final String government;
    public final String capital;
    public final String rank;
}
