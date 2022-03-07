package ImperatorToCK2.CK2;

public class Title {
    public Title(String imperatorTag, String irColor, String government, String capital, String rank) {
        this.tag = imperatorTag;
        this.irColor = irColor;
        this.government = government;
        this.capital = capital;
        this.rank = rank;
    }

    public final String getName() {
        return rank +"_" + tag;
    }

    private final String tag;
    public final String irColor;
    public final String government;
    public final String capital;
    public final String rank;
}
