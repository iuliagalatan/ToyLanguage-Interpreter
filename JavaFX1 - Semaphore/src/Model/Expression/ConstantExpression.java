package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Type.Type;
import Model.Value.Value;

public class ConstantExpression implements Expression {
    private Value value;

    public ConstantExpression(Value value) {

        this.value = value;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Integer, Value> heapTable) {

        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Type typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return value.getType();
    }
}
