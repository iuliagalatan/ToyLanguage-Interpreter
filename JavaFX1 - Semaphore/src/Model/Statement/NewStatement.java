package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class NewStatement implements IStatement {
    private String var_name;
    private Expression expression;

    public NewStatement(String var_name, Expression expression) {
        this.expression = expression;
        this.var_name = var_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyHeap<Integer, Value> heap = state.getHeap();


        if ( symTable.containsKey(var_name)) {
            //de vazut daca functioneaza
            Value val =  symTable.get(var_name);
            if (val.getType().getClass() == RefType.class) {

                Value evaluated = expression.evaluate(symTable, heap);
                RefValue refvalue = (RefValue)val;


                    Integer freeAdress = heap.getFreeAdress();
                    heap.put(freeAdress, evaluated);
                    RefValue newrefvalue = new RefValue(freeAdress, refvalue.getType());
                    state.getSymbolsTable().put(var_name, newrefvalue);
                    state.getHeap().setFreeAdress(freeAdress+1);


            }
            else throw new MyException("Var not reference type!");
        }
        else
            throw  new MyException("var not in symtable");
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.get(var_name);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }


    @Override
    public String toString() {
        return "new(" + var_name + "," + expression.toString() + ")";
    }
}
