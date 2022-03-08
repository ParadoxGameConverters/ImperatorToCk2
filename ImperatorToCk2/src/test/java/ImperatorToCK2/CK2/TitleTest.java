package ImperatorToCK2.CK2;

import org.junit.Test;
import org.junit.Assert;
import java.io.IOException;
import java.util.Optional;

public class TitleTest {
    @Test
    public void in_full_constructor_name_is_derived_from_rank_and_tag() throws IOException {
        Optional<String> emptyOptionalString = Optional.empty();
        Optional<Integer> emptyOptionalInteger = Optional.empty();
        Title testTitle = new Title("TAG", emptyOptionalString, "irrelevant_government", emptyOptionalInteger, "testrank");
        Assert.assertEquals("testrank_TAG", testTitle.getName());
    }

    @Test
    public void in_palace_constructor_name_is_derived_from_tag() throws IOException {
        Title testTitle = new Title("TAG");
        Assert.assertEquals("b_TAG", testTitle.getName());
    }
}