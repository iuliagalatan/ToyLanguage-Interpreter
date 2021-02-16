package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.ConstantExpression;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStatement  implements IStatement{
    private String var;
    private static Lock lock = new ReentrantLock();

    public CountDownStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMyDictionary<Integer, Integer> latchTable = state.getLatchTable();
        IMyDictionary<String, Value> symbolTable = state.getSymbolsTable();

        if ( !symbolTable.containsKey(var)) {
            lock.unlock();
            throw new MyException("var not in symbolstable");
        }
        Value foundIndex = symbolTable.get(var);
        IntValue fountIndexint  = (IntValue)foundIndex;

        if ( !latchTable.containsKey(fountIndexint.getValue())){
            lock.unlock();
            throw new MyException("index not in Latchtable");
        }


        if (latchTable.get(fountIndexint.getValue()) > 0) {

            latchTable.put(fountIndexint.getValue(), latchTable.get(fountIndexint.getValue()) -1);
            state.setLatchTable(latchTable);

        }
        IMyStack<IStatement> stack = state.getExecutionStack();
        stack.push(new PrintStatement(new ConstantExpression(new IntValue(state.getId()))));
        lock.unlock();
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }


    @Override
    public String toString() {
        return "CountDown(" + var +")";
    }
}
