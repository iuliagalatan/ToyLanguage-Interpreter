package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Type.Type;

public class CompoundStatement implements IStatement, Cloneable {

    IStatement first;
    IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;

    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    public String toString(){

        return "(" +first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public CompoundStatement clone() throws CloneNotSupportedException {
        CompoundStatement stmt = (CompoundStatement)super.clone();
        return stmt;
    }
}
