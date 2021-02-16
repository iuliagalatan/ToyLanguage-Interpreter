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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphore implements  IStatement {
    private String id;
    private Expression expression;
    private static Lock lock = new ReentrantLock();


    public CreateSemaphore(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        lock.lock();


        IMyStack<IStatement> stack = state.getExecutionStack();
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        IMyDictionary<Integer, Pair<Integer, List<Integer>>>  semaphoretable = state.getSemaphoreTable();
        IMyDictionary<String, Value> symtable = state.getSymbolsTable();


        if ( value.getType() instanceof IntType)
        {
            Integer location = state.getLocklocation();
            IntValue val = (IntValue)value;

            if (symtable.containsKey(id) )
                if (symtable.get(id).getType() instanceof IntType) {
                    semaphoretable.put(location, new Pair<Integer, List<Integer>>(val.getValue(), new ArrayList<>()));
                    symtable.put(id, new IntValue(location));
                    state.setSemaphoreTable(semaphoretable);
                    state.setSymbolsTable(symtable);
                }
            else{
                    lock.unlock();
                    throw new MyException("exception in createsemaphore");
                }

        }
        else {
            lock.unlock();
            throw new MyException("expression at createsemaphore does not evaluate to int type");
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
        return "createSemaphore(" + id + ", "+ expression.toString()+")";
    }
}
