package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStatement implements IStatement {
    private Expression expression;
    private IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if ( value.getType().equals(new BoolType()) )
        {
            BoolValue boolval = (BoolValue)value;
            if ( boolval.isTrue())
            {
                IStatement w = new WhileStatement(expression,statement);
                stack.push(w);
                stack.push(statement);
            }
        }
        else
            throw new MyException("expression did not evaluate to boolType");
        return null;

    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typecheck(typeEnv);
        if ( expressionType.equals(new BoolType())) {
            statement.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new MyException("expression type wasn't  Booltype");
    }

    @Override
    public String toString() {
        return "while ( " + expression.toString() + " )\n {\n " + statement.toString() + "}";
    }
}
