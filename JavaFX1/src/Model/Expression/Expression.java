package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Type.Type;
import Model.Value.Value;

public interface Expression {
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Integer, Value> heapTable) throws Exception;
    public String toString();
    Type typecheck(IMyDictionary<String,Type> typeEnv) throws MyException;


}

