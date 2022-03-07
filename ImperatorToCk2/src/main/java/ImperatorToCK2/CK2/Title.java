package ImperatorToCK2.CK2;

import java.util.Optional;
import ImperatorToCK2.Importer;
import java.io.IOException;

public class Title {
    public Title(String imperatorTag, String imperatorColor, String government, String imperatorCapital, String rank) throws IOException {
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

        if (imperatorCapital.equals("none")) {
            this.capital = Optional.empty();
        } else {
            Integer capital = Integer.parseInt(Importer.importConvList("provinceConversion.txt", Integer.parseInt(imperatorCapital))[1]);
            this.capital = Optional.of(capital);
        }

        this.rank = rank;
    }

    public final String getName() {
        return name;
    }

    private final String name;
    public final Optional<String> color;
    public final boolean isRepublic;
    public final boolean isEmpire;
    public final Optional<Integer> capital;
    public final String rank;
}
