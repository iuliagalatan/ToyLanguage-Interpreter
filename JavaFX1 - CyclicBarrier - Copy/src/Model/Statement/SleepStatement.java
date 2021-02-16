package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.IntValue;

public class SleepStatement implements  IStatement{
    private IntValue number;

    public SleepStatement(IntValue number) {
        this.number = number;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();

        if ( number.getValue() >  0)
        {
            stack.push(new SleepStatement(new IntValue(number.getValue()-1)));
        }
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "sleep ( " + number.toString() +")";
    }
}
