package Model.Expression;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExpression  implements  Expression{
    private Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Integer, Value> heapTable) throws Exception {

        Value value = expression.evaluate(symbolTable, heapTable);
        if ( value.getType().getClass() == RefType.class ) {
            RefValue refValue = (RefValue)value;
            int adress = refValue.getHeapAdress();

            if ( heapTable.containsAdress(adress)) {

                Value valueFromTable = heapTable.get(adress);
                return valueFromTable;
            }
            else
                throw new MyException("Adress is not key in heaptable");

        }
        else
            throw new MyException("Expression does not evaluate to Refvalue");


    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }

    @Override
    public Type typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type type=expression.typecheck(typeEnv);
        if (type instanceof RefType)
        {
            RefType reftype = (RefType) type;
            return reftype.getInnner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }
}
