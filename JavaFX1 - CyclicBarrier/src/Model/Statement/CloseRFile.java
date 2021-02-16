package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class CloseRFile implements  IStatement{
    private Expression expression;

    public CloseRFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value value = expression.evaluate(symTable, state.getHeap() );

        if (! value.getType().equals(new StringType())) {
            throw new MyException("Value not string type");
        }

        BufferedReader bf = fileTable.get((StringValue) value);
        if ( bf == null)
            throw new MyException("bufferedReader not found!");


        bf.close();
        fileTable.remove((StringValue) value);

        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typecheck(typeEnv);
        if ( expressionType.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("OpenRFile: expression does not have stringType");
    }


    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
