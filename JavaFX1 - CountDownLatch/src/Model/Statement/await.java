package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class await implements IStatement{
    private String var;
    private static Lock lock = new ReentrantLock();

    public await(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        IMyDictionary<Integer, Integer> latchTable = state.getLatchTable();
        IMyDictionary<String, Value> symbolTable = state.getSymbolsTable();

        if ( !symbolTable.containsKey(var)) {

            throw new MyException("var not in symbolstable");
        }
        Value foundIndex = symbolTable.get(var);
        IntValue fountIndexint  = (IntValue)foundIndex;

        if ( !latchTable.containsKey(fountIndexint.getValue())){

            throw new MyException("index not in Latchtable");
        }
        if (latchTable.get(fountIndexint.getValue()) != 0) {
            state.getExecutionStack().push(this);
        }

        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }


    @Override
    public String toString() {
        return "await("+var+")";
    }
}
