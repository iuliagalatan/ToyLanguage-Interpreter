package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithmeticalExpression implements  Expression{

    private Expression expression1;
    private Expression expression2;
    private char operation;

    public ArithmeticalExpression(Expression ex1, Expression ex2, char operation) {
        this.expression1 = ex1;
        this.expression2 = ex2;
        this.operation = operation;
    }

    public ArithmeticalExpression(char operation, Expression ex1, Expression ex2) {
        this.operation = operation;
        this.expression1 = ex1;
        this.expression2 = ex2;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Integer, Value> heapTable) throws Exception {

        Value value1, value2;
        value1 = expression1.evaluate(symbolTable,heapTable );
        if ( value1.getType().equals(new IntType())) {
            value2 = expression2.evaluate(symbolTable,heapTable );
            if ( value2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)value1;
                IntValue i2 = (IntValue)value2;

                int nr1, nr2;
                nr1 = i1.getValue();
                nr2 = i2.getValue();

                if ( operation == '+')
                    return new IntValue(nr1+nr2);
                if ( operation == '*')
                    return new IntValue(nr1*nr2);
                if ( operation == '/') {
                    if (nr2 == 0)
                        throw new Exception("Division by 0!");
                    return new IntValue(nr1/nr2);
                }
                if ( operation == '-')
                    return new IntValue(nr1-nr2);


             }
            else
                throw new Exception("Second operand not integer!");
        }
        else
            throw new Exception("First operand not integer!");


        return null;
    }


    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
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


    public char getOperation() {
        return this.operation;
    }

    public Expression getFirst() {
        return this.expression1;
    }

    public Expression getSecond() {
        return this.expression2;
    }


}
