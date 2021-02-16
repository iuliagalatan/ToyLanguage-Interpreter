package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadRFile implements IStatement{
    private Expression expression;
    private String var_name;

    public ReadRFile(Expression expression, String var) {
        this.expression = expression;
        this.var_name = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        // IStatement statement = stack.pop();

        if ( !symTable.containsKey(var_name))
            throw new MyException("var not defined");
        Value val = symTable.get(var_name);
        if (!val.getType().equals(new IntType()))
        {
            throw new MyException("var not int type");
        }

        Value value = expression.evaluate(symTable, state.getHeap());

        if ( ! value.equals(new StringValue()) ) {

            throw new MyException("value not String Value");
        }

        BufferedReader bf = fileTable.get((StringValue) value);
        try{

            String line = bf.readLine();
            Value intValue;
            IntType type = new IntType();

            if ( line == null){
                intValue = type.initialValue();
            }

            else
                intValue = new IntValue(Integer.parseInt(line));
            symTable.put(var_name, intValue);

        }catch (IOException exc) {
            throw new MyException("IO Exception");
        }
        return null;

    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.get(var_name);
        Type typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new StringType()) && typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("ReadRFile: name of the file is not a string or the variable given is not an int!");

    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + var_name + ")";
    }
}
