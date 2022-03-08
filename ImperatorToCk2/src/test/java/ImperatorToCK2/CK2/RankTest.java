package ImperatorToCK2.CK2;

import org.junit.Assert;
import org.junit.Test;

public class RankTest {
    @Test
    public void empire_character_is_e() {
        Assert.assertEquals('e', Rank.EMPIRE.getLetter());
    }

    @Test
    public void kingdom_character_is_k() {
        Assert.assertEquals('k', Rank.KINGDOM.getLetter());
    }

    @Test
    public void duchy_character_is_d() {
        Assert.assertEquals('d', Rank.DUCHY.getLetter());
    }

    @Test
    public void county_character_is_c() {
        Assert.assertEquals('c', Rank.COUNTY.getLetter());
    }

    @Test
    public void barony_character_is_b() {
        Assert.assertEquals('b', Rank.BARONY.getLetter());
    }
}