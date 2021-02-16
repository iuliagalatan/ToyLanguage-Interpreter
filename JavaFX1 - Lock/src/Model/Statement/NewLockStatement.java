package Model.Statement;




import Model.ADT.Dictionary.IMyDictionary;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements IStatement {

    private String var;
    private static Lock lock = new ReentrantLock();

    public NewLockStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMyDictionary<Integer, Integer> lockTable = state.getLockTable();
        IMyDictionary<String, Value> symbolTable = state.getSymbolsTable();
        int location = state.getLocation();
        lockTable.put(location, -1);
        symbolTable.put(var, new IntValue(location));
        state.setSymbolsTable(symbolTable);
        state.setLockTable(lockTable);
        lock.unlock();
        return null;

    }


    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "newLock(" + var + ")";
    }
}