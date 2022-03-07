package ImperatorToCK2.CK2;

import java.util.Optional;
import ImperatorToCK2.Importer;
import java.io.IOException;

public class Title {
    private final String name;
    private final Optional<String> color;
    private final boolean isRepublic;
    private final boolean isEmpire;
    private final Optional<Integer> capital;

    public Title(String imperatorTag, Optional<String> imperatorColor, String government, Optional<Integer> imperatorCapital,
            String rank) throws IOException {
        this.name = rank + "_" + imperatorTag;

        this.color = imperatorColor;

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

        this.capital = convertCapital(imperatorCapital);
    }

    public Title(String imperatorTag) {
        this.name = "b_" + imperatorTag;
        this.color = Optional.empty();
        this.isRepublic = false;
        this.isEmpire = false;
        this.capital = Optional.empty();
    }

    public String getName() {
        return name;
    }

    public Optional<String> getColor() {
        return color;
    }

    public boolean isRepublic() {
        return isRepublic;
    }

    public boolean isEmpire() {
        return isEmpire;
    }

    public Optional<Integer> getCapital() {
        return capital;
    }

    private Optional<Integer> convertCapital(Optional<Integer> imperatorCapital) throws IOException {
        if (imperatorCapital.isEmpty()) {
            return Optional.empty();
        } else {
            Integer capital = Integer
                    .parseInt(Importer.importConvList("provinceConversion.txt", imperatorCapital.get())[1]);
            return Optional.of(capital);
        }
    }
}
