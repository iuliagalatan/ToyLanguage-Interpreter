package Model.Value;

import static org.junit.jupiter.api.Assertions.*;

public class StringValueTest {

    public void test_equals_returnstrue()
    {
        StringValue strval  = new StringValue("salut");
        StringValue strval2  = new StringValue("salut");

         assert strval.equals(strval2) == true;

    }
    public void test_equals_returnsfalse()
    {
        StringValue strval  = new StringValue("salut");
        StringValue strval2  = new StringValue("beesalut");

        assert strval.equals(strval2) == false;

        BoolValue bval = new BoolValue(true);

        assert strval.equals(bval) == false;

    }
}