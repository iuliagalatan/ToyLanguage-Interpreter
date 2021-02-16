package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Dictionary.MyDictionary;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

class RelationalExpressionTest {
    public void test_relational_expression_se_ok()
    {
        Value value = new IntValue(5);
        Value value2 = new IntValue(7);

        Expression expr1 = new ConstantExpression(value);
        Expression expr2 = new ConstantExpression(value2);

        RelationalExpression re = new RelationalExpression(expr1, expr2, "<=");

        IMyDictionary symbolTable = new MyDictionary<String, Value>();

        try {
            BoolValue newbool = (BoolValue) re.evaluate(symbolTable, null );
            boolean bool = newbool.getValue();
            assert bool;

        } catch (Exception exception) {
            exception.printStackTrace();
        }



    }
    public void test_relational_expression_ge_ok()
    {
        Value value = new IntValue(5);
        Value value2 = new IntValue(7);

        Expression expr1 = new ConstantExpression(value);
        Expression expr2 = new ConstantExpression(value2);

        RelationalExpression re = new RelationalExpression(expr1, expr2, ">=");

        IMyDictionary symbolTable = new MyDictionary<String, Value>();

        try {
            BoolValue newbool = (BoolValue) re.evaluate(symbolTable,null );
            boolean bool = newbool.getValue();
            assert !bool;

        } catch (Exception exception) {
            exception.printStackTrace();
        }



    }
}