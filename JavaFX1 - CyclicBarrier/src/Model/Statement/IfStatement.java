package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStatement implements IStatement {
    private Expression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;


    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement){
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }


    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyStack<IStatement> stack = state.getExecutionStack();
        Value value = expression.evaluate(symTable, state.getHeap());

        if ( value.getType().getClass() == BoolType.class ){

            BoolValue boolValue = (BoolValue)value;
            if (boolValue.getValue() == true) {

                stack.push(thenStatement);

            }
            else
                stack.push(elseStatement);
        }
        else
            throw new Exception("Expression could not be evaluated to True or False!");
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck( typeEnv );
        if (typexp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.clone());
            elseStatement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF isn't of type bool");
    }


    @Override
    public String toString() {
        return "(if("+ expression.toString()+") then(" +thenStatement.toString() +")else("+elseStatement.toString()+"))";
    }
}
