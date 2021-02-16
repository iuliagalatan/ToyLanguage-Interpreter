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
import java.io.FileReader;

public class OpenRFile implements IStatement {
    private Expression expression;


    public OpenRFile(Expression e) {
        this.expression = e;
    }



    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
       // IStatement statement = stack.pop();

        Value value = expression.evaluate(symTable, state.getHeap());
        if ( value.getType().equals(new StringType()) )
        {
            StringValue strvalue = (StringValue)value;

            if ( fileTable.containsKey(strvalue))
            {
                throw new MyException("value string already exists in symtable");

            }
            BufferedReader b = new BufferedReader(new FileReader(strvalue.getValue()));
            fileTable.put(strvalue, b);

        }
        else
            throw new MyException("execution stoped. Value type not StringType");
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
        return "openRFile("  + expression.toString() +")";
    }

}
