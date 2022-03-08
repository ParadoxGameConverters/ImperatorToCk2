package ImperatorToCK2.CK2;

import java.util.Optional;

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

    @Test
    public void string_starting_with_e_gets_empire_rank() {
        Optional<Rank> testRank = Rank.get("empire");
        Assert.assertTrue(testRank.isPresent());
        Assert.assertEquals(Rank.EMPIRE, testRank.get());
    }

    @Test
    public void string_starting_with_k_gets_kingdom_rank() {
        Optional<Rank> testRank = Rank.get("kingdom");
        Assert.assertTrue(testRank.isPresent());
        Assert.assertEquals(Rank.KINGDOM, testRank.get());
    }

    @Test
    public void string_starting_with_d_gets_duchy_rank() {
        Optional<Rank> testRank = Rank.get("duchy");
        Assert.assertTrue(testRank.isPresent());
        Assert.assertEquals(Rank.DUCHY, testRank.get());
    }

    @Test
    public void string_starting_with_c_gets_county_rank() {
        Optional<Rank> testRank = Rank.get("county");
        Assert.assertTrue(testRank.isPresent());
        Assert.assertEquals(Rank.COUNTY, testRank.get());
    }

    @Test
    public void string_starting_with_b_gets_barony_rank() {
        Optional<Rank> testRank = Rank.get("barony");
        Assert.assertTrue(testRank.isPresent());
        Assert.assertEquals(Rank.BARONY, testRank.get());
    }

    @Test
    public void string_starting_with_other_letters_gets_no_rank() {
        Optional<Rank> testRank = Rank.get("false_rank");
        Assert.assertTrue(testRank.isEmpty());
    }

    @Test
    public void empty_string_gets_no_rank() {
        Optional<Rank> testRank = Rank.get("");
        Assert.assertTrue(testRank.isEmpty());
    }
}