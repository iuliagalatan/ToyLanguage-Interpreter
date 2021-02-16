package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelationalExpression implements  Expression{
    private Expression expression1;
    private Expression expression2;
    private String operator;

    public RelationalExpression(Expression expression1, Expression expression2, String operator) {

        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Integer, Value> heapTable) throws Exception {
        Value value1, value2;
        value1 = expression1.evaluate(symbolTable, heapTable );
        value2 = expression2.evaluate(symbolTable, heapTable);

        Integer nr1, nr2;

        if ( value1.getType().equals(new IntType())) {
            value2 = expression2.evaluate(symbolTable, heapTable);
            if (value2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) value1;
                IntValue i2 = (IntValue) value2;


                nr1 = i1.getValue();
                nr2 = i2.getValue();
            }
            else
                throw new Exception("Second operand not integer");
        }
        else
            throw new Exception("First operand not integer!");

        boolean boolResult;
        switch (operator){

            case "<" :
                boolResult = nr1 < nr2;
                break;
            case "<=" :
                boolResult = nr1 <= nr2;
                break;
            case ">" :
                boolResult = nr1 > nr2;
                break;
            case ">=" :
                boolResult = nr1 >= nr2;
                break;
            case "==" :
                boolResult = nr1.equals(nr2);
                break;
            case "!=" :
                boolResult = !nr1.equals(nr2);
                break;
            default:
                throw new Exception(operator + " is not a valid comparison operator.");
        }
        return new BoolValue(boolResult);
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operator + " " + expression2.toString();
    }

    @Override
    public Type typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;

        type1=expression1.typecheck(typeEnv);
        type2=expression2.typecheck(typeEnv);

        if (type1.equals(new IntType()))
        {
            if (type2.equals(new IntType()) ) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }

}
