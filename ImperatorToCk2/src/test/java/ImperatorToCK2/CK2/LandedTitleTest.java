package ImperatorToCK2.CK2;

import org.junit.Test;
import org.junit.Assert;
import java.io.IOException;
import java.util.Optional;

public class LandedTitleTest {
    @Test
    public void in_full_constructor_name_is_rank_underscore_tag() throws IOException {
        Optional<String> emptyOptionalString = Optional.empty();
        Optional<Integer> emptyOptionalInteger = Optional.empty();
        LandedTitle testTitle = new LandedTitle("TAG", emptyOptionalString, "irrelevant_government", emptyOptionalInteger,
                "testrank");
        Assert.assertEquals("testrank_TAG", testTitle.getName());
    }

    @Test
    public void in_palace_constructor_name_is_b_underscore_tag() throws IOException {
        LandedTitle testTitle = new LandedTitle("TAG");
        Assert.assertEquals("b_TAG", testTitle.getName());
    }

    @Test
    public void ck2_government_defaults_to_monarchy() throws IOException {
        Optional<String> emptyOptionalString = Optional.empty();
        Optional<Integer> emptyOptionalInteger = Optional.empty();
        LandedTitle testTitle = new LandedTitle("TAG", emptyOptionalString, "irrelevant_government", emptyOptionalInteger,
                "testrank");
        Assert.assertEquals(Government.MONARCHY, testTitle.getGovernment());
    }

    @Test
    public void imperator_republic_becomes_ck2_republic() throws IOException {
        Optional<String> emptyOptionalString = Optional.empty();
        Optional<Integer> emptyOptionalInteger = Optional.empty();
        LandedTitle testTitle = new LandedTitle("TAG", emptyOptionalString, "republic", emptyOptionalInteger, "testrank");
        Assert.assertEquals(Government.REPUBLIC, testTitle.getGovernment());
    }

    @Test
    public void imperator_imperium_with_rank_e_becomes_ck2_empire() throws IOException {
        Optional<String> emptyOptionalString = Optional.empty();
        Optional<Integer> emptyOptionalInteger = Optional.empty();
        LandedTitle testTitle = new LandedTitle("TAG", emptyOptionalString, "imperium", emptyOptionalInteger, "e");
        Assert.assertEquals(Government.EMPIRE, testTitle.getGovernment());
    }

    @Test
    public void imperator_imperium_without_rank_e_becomes_ck2_monarchy() throws IOException {
        Optional<String> emptyOptionalString = Optional.empty();
        Optional<Integer> emptyOptionalInteger = Optional.empty();
        LandedTitle testTitle = new LandedTitle("TAG", emptyOptionalString, "imperium", emptyOptionalInteger, "k");
        Assert.assertEquals(Government.MONARCHY, testTitle.getGovernment());
    }
}