package Model.Statement;




import Model.ADT.Dictionary.IMyDictionary;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStatement implements IStatement {
    private String var;
    private static Lock lock = new ReentrantLock();

    public UnlockStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IntValue index = (IntValue) state.getSymbolsTable().get(var);
        Integer foundIndex = index.getValue();

        if( foundIndex == null )
            throw new Exception("No such variable in symbolTable");
        IMyDictionary<Integer, Integer> lockTable = state.getLockTable();
        Integer lockValue = lockTable.get(foundIndex);
        if (lockValue == null )
            throw new Exception("Found index is not a position in lockTable");
        if (lockValue.equals(state.getId())) {
            lockTable.put(foundIndex, -1);
            state.setLockTable(lockTable);
        }
        lock.unlock();
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "unlock( " + var + " )";
    }


}