package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.ADT.Stack.MyStack;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

import java.sql.Statement;

public class ForkStatement implements  IStatement{
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        ProgramState pr = new ProgramState(new MyStack<>(), state.getSymbolsTable().clone(), state.getList(), this.statement, state.getFileTable(), state.getHeap(), state.getId()*10, state.getBarrierTable());
        return pr;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
