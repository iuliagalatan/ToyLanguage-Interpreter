package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.Value;



public class AssignmentStatement implements IStatement{

    private String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression) {

        this.id = id;
        this.expression = expression;

    }
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyStack<IStatement> stack = state.getExecutionStack();

        if ( symTable.containsKey(id) ){
            Value value = expression.evaluate(symTable, state.getHeap());
            Type type = symTable.get(id).getType();
            if ( value.getType().equals(type)){
                symTable.put(id, value);
            }
            else
                throw new Exception("Declared type of variable " + id + " and type of the assigned expression do not match");


        }
        else
            throw new Exception("Variable " + id+ " not declared before");

        return null;


    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.get(id);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");

    }

    @Override
    public String toString() {
        return this.id + "=" + expression.toString();
    }
}
