package Model.Statement;

import Model.Expression.ConstantExpression;
import Model.Expression.Expression;
import Model.Value.StringValue;
import Model.Value.Value;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileTest {

    public void test_open_read_close_ok()
    {
        String varf;
        varf="test.in";
        Value value = new StringValue(varf);
        Expression expr = new ConstantExpression(value);
        OpenRFile open = new OpenRFile(expr);
      /*  open.execute();
        int varc;
        readFile(varf,varc);
        print(varc);
        readFile(varf,varc);
        print(varc);
        closeRFile(varf);*/
    }
}