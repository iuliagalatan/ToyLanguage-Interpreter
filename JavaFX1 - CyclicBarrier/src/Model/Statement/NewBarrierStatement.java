package Model.Statement;



        import Model.ADT.Dictionary.IMyDictionary;
        import Model.Exception.MyException;
        import Model.Expression.Expression;
        import Model.ProgramState;
        import Model.Statement.IStatement;
        import Model.Type.Type;
        import Model.Value.IntValue;
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
        IMyDictionary<Integer, Pair<Integer, List<Integer>>> barriertable = state.getBarrierTable();
        Value expValue = expression.evaluate(symbolTable, state.getHeap());
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
        return typeEnv;
    }



    @Override
    public String toString(){
        return "newBarrierStatement(" + var + ", " + expression.toString() + ")";
    }
}