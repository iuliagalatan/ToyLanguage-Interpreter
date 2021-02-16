package Model.Statement;


import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStatement implements IStatement{
    private String var_name;
    private Expression expression;

    public WriteHeapStatement(String var_name, Expression expression) {
        this.var_name = var_name;
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyHeap<Integer, Value> heapTable = state.getHeap();
        IMyStack<IStatement> stack = state.getExecutionStack();


        if ( symTable.containsKey(var_name)) {
            Value valueFromSTable = symTable.get(var_name);
            if ( valueFromSTable.getType().getClass() == RefType.class) {
                RefValue refValue = (RefValue)valueFromSTable;
                int adress = refValue.getHeapAdress();
                if ( heapTable.containsAdress(adress)){
                    Value evaluated = expression.evaluate(symTable, heapTable);
                    heapTable.update(adress, evaluated);

                }
                else throw new MyException("heap does not contain adress");
            }
            else
                throw new MyException("Value from sytable not reftype");

        }
        else throw new MyException("var_name not defined in SymbolTable");
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typecheck(typeEnv);
        Type variableType = typeEnv.get(var_name);
        if ( variableType.equals(new RefType(expressionType))) {
            return typeEnv;
        }
        else
            throw new MyException("WriteHeap: left hand side and right hand side have different types");



        

    }

    @Override
    public String toString() {
        return "writeHeap(" + var_name + "," + expression.toString() + ")";
    }
}