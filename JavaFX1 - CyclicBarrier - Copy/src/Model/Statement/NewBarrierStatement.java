package Model.Statement;



        import Model.ADT.BarrierTable.IBarrierTable;
        import Model.ADT.Dictionary.IMyDictionary;
        import Model.Exception.MyException;
        import Model.Expression.Expression;
        import Model.ProgramState;
        import Model.Statement.IStatement;
        import Model.Type.IntType;
        import Model.Type.RefType;
        import Model.Type.Type;
        import Model.Value.IntValue;
        import Model.Value.RefValue;
        import Model.Value.Value;
        import javafx.util.Pair;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.locks.Lock;
        import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStatement implements IStatement {
    private String var;
    private Expression expression;
    private static Lock lock = new ReentrantLock();

    public NewBarrierStatement(String var, Expression expression){
        this.expression = expression;
        this.var = var;
    }


    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMyDictionary<String, Value> symbolTable = state.getSymbolsTable();
        IBarrierTable<Integer, Pair<Integer, List<Integer>>> barriertable = state.getBarrierTable();
        Value expValue = expression.evaluate(symbolTable, state.getHeap());
        if (!( expValue.getType() instanceof  IntType)){
            lock.unlock();
            throw new MyException("expression evaluation does not have INTTYPE");
        }

        IntValue intvalue = (IntValue)expValue;

        Integer location = state.getLocation();
        barriertable.put(location, new Pair<>(intvalue.getValue(), new ArrayList<>()));
        symbolTable.put(var, new IntValue(location));


        state.setBarrierTable(barriertable);
        state.setSymbolsTable(symbolTable);
        lock.unlock();
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        //implement the method typecheck for the statement newBarrier(var, 	exp) to verify if
        // both var and exp have the type int. Implement the method
        // typecheck for the statement await(var) to verify if var has the type int.

        Type typexpr = expression.typecheck( typeEnv );
        if ( !(typexpr  instanceof IntType)) {
            throw new MyException("expression does not have type int");
        }

        if (!(typeEnv.get(var) instanceof IntType)){
            if (typeEnv.get(var) instanceof RefType){
                RefValue ref = (RefValue)typeEnv.get(var);
                if ( !(ref.getType() instanceof  IntType)){
                    throw new MyException("var does not have int type 1 ");
                }
            }
            else throw new MyException("var does not have int type 2");
        }



        return typeEnv;

    }



    @Override
    public String toString(){
        return "newBarrierStatement(" + var + ", " + expression.toString() + ")";
    }
}