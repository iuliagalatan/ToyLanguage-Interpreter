package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatch implements  IStatement {
    private String var;
    private Expression expression;
    private static Lock lock = new ReentrantLock();


    public NewLatch(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMyDictionary<Integer, Integer> latchTable = state.getLatchTable();
        IMyDictionary<String, Value> symbolTable = state.getSymbolsTable();
        int location = state.getLocation();

        Value num1 = expression.evaluate(symbolTable, state.getHeap());

        if (! (num1.getType() instanceof IntType)){
            lock.unlock();
            throw new MyException("expression does not evaluate to int");
        }


        IntValue intnum = (IntValue)num1;
        latchTable.put(location, intnum.getValue());

        if (!symbolTable.containsKey(var)){
            lock.unlock();
            throw new MyException("var not in symbolstable");
        }

        symbolTable.put(var, new IntValue(location));
        state.setSymbolsTable(symbolTable);
        state.setLatchTable(latchTable);
        lock.unlock();
        return null;

    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", "+ expression.toString() + ")";
    }
}
