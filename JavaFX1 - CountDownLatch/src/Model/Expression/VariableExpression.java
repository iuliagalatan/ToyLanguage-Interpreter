package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Type.Type;
import Model.Value.Value;

public class VariableExpression  implements Expression{
    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Integer, Value> heapTable) throws Exception {
       //returns the value to which is mapped the variable id;

        Value result = symbolTable.get(id);

        if ( result == null )
            throw new Exception("variable X is not defined");
        return result;

    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Type typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.get(id);
    }

}
