package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.Expression.NotExpression;
import Model.ProgramState;
import Model.Type.Type;

public class RepeatUntilStatement implements IStatement{

    private IStatement statement;
    private Expression expression;

    public RepeatUntilStatement(IStatement statement, Expression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();
        CompoundStatement cs =  new CompoundStatement( statement, new WhileStatement(new NotExpression(expression), statement));
        stack.push(cs);
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "repeat ( " + statement.toString() + ") until "+ expression.toString();
    }
}
