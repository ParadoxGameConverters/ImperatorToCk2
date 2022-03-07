package ImperatorToCK2.CK2;

import java.util.Optional;

public class Title {
    public Title(String imperatorTag, String imperatorColor, String government, String capital, String rank) {
        this.name = rank + "_" + imperatorTag;

        if (imperatorColor.equals("none")) {
            this.color = Optional.empty();
        } else {
            this.color = Optional.of(imperatorColor);
        }

        if (government.equals("republic")) {
            this.isRepublic = true;
        } else {
            this.isRepublic = false;
        }
        if (government.equals("imperium") && rank.equals("e")) {
            this.isEmpire = true;
        } else {
            this.isEmpire = false;
        }

        this.capital = capital;
        this.rank = rank;
    }

    public final String getName() {
        return name;
    }

    private final String name;
    public final Optional<String> color;
    public final boolean isRepublic;
    public final boolean isEmpire;
    public final String capital;
    public final String rank;
}
