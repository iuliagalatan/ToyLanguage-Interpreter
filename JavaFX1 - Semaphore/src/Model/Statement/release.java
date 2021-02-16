package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class release implements IStatement{
    private String var;
    private static Lock lock = new ReentrantLock();


    public release(String var) {
        this.var = var;

    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMyDictionary<Integer, Pair<Integer, List<Integer>>>  semaphoretable = state.getSemaphoreTable();
        IMyDictionary<String, Value> symtable = state.getSymbolsTable();
        IMyStack<IStatement> executionStack = state.getExecutionStack();

        //aici trebuie verificat sa fie si int
        if (!symtable.containsKey(var) ){
            lock.unlock();
            throw new MyException("var not in symtable");
        }

        IntValue foundIndexvalue = (IntValue) symtable.get(var);
        Integer foundIndex = foundIndexvalue.getValue();

        Integer N1  = semaphoretable.get(foundIndex).getKey();
        List<Integer> ThreadsList = semaphoretable.get(foundIndex).getValue();
        Integer NL = ThreadsList.size();

        if ( ThreadsList.contains(state.getId())){
            ThreadsList.remove(state.getId());
            state.getSemaphoreTable().put(foundIndex, new Pair<>(N1, ThreadsList));
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
        return "release( " + var + " )";
    }

}
