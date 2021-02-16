package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class aquire implements IStatement{
    private String var;
    private static Lock lock = new ReentrantLock();

    public aquire(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();;
        IMyDictionary<Integer, Pair<Integer, List<Integer>>>  semaphoretable = state.getSemaphoreTable();
        IMyDictionary<String, Value> symtable = state.getSymbolsTable();
        IMyStack<IStatement> executionStack = state.getExecutionStack();

        if (symtable.containsKey(var) )
            if (symtable.get(var).getType() instanceof IntType) {

                IntValue intval = (IntValue) symtable.get(var);
                if ( semaphoretable.containsKey(intval.getValue()))
                {
                    Integer N1  = semaphoretable.get(intval.getValue()).getKey();
                    List<Integer> ThreadsList = semaphoretable.get(intval.getValue()).getValue();
                    Integer NL = ThreadsList.size();
                    if ( N1 > NL){
                        if ( !ThreadsList.contains(state.getId())){
                            ThreadsList.add(state.getId());
                            state.getSemaphoreTable().put(intval.getValue(), new Pair<>(N1, ThreadsList));
                        }

                    }else
                    {
                        executionStack.push(this);
                        state.setExecutionStack(executionStack);
                    }

                }else {
                    lock.unlock();
                    throw new MyException("semaphore is not bound to that location");
                }

            }else {
                lock.unlock();
                throw  new MyException("Exception in aquire");}
        lock.unlock();
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
