package ImperatorToCK2.CK2;

public class Title {
    public Title(String imperatorTag, String irColor, String government, String capital, String rank) {
        this.name = rank +"_" + imperatorTag;
        this.irColor = irColor;
        this.government = government;
        this.capital = capital;
        this.rank = rank;
    }

    public final String getName() {
        return name;
    }

    private final String name;
    public final String irColor;
    public final String government;
    public final String capital;
    public final String rank;
}
