package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitBarrierStatement implements IStatement {

    private String var;
    private static Lock lock = new ReentrantLock();

    public AwaitBarrierStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMyDictionary<String, Value> symbolTable = state.getSymbolsTable();
        IMyDictionary<Integer, Pair<Integer, List<Integer>>> barrierTable = state.getBarrierTable();

        Value valueindex = symbolTable.get(var);
        IntValue integerIndex = (IntValue)valueindex;
        if (integerIndex == null)
            throw new Exception("value does not exist in symtable (barrier error)");

        Pair<Integer, List<Integer>> barrierValue = barrierTable.get(integerIndex.getValue());
        if (barrierValue == null)
            throw new Exception("null barrier value");
        List<Integer> threads = barrierValue.getValue();  //threads = L1  (list 1)
        Integer nr1 = barrierValue.getKey();
        Integer length = threads.size();

        if (nr1 > length){
            if (barrierValue.getValue().contains(state.getId()))
                state.getExecutionStack().push(this);
            else
            {
                threads.add(state.getId());
                state.getBarrierTable().put(integerIndex.getValue(), new Pair<>(nr1, threads));
                // state.getStack().push(this);
            }
        }

        lock.unlock();
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }



    @Override
    public String toString() {
        return "awaitBarrier( " + var + " )";
    }
}