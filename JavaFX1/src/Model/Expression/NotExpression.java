package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class NotExpression implements Expression {
    private Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Integer, Value> heapTable) throws Exception {

        Value val = this.expression.evaluate(symbolTable, heapTable);

        if (val.getType() instanceof BoolType) {
            BoolValue value = (BoolValue) val;

            if (value.isTrue()) {
                return new BoolValue(false);
            } else
                return new BoolValue(true);

        }

        if (val.getType() instanceof IntType) {
            IntValue value = (IntValue) val;

            if (value.getValue() != 0) {
                return new IntValue(0);
            } else
                return new IntValue(1);

        }
        return null;

    }

    public String toString() {
        return "!" + this.expression.toString();
    }

    @Override
    public Type typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return expression.typecheck(typeEnv);
    }
}