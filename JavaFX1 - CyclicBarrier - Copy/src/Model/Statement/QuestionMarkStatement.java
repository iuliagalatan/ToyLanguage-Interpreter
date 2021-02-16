package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;

public class QuestionMarkStatement implements IStatement{

    private Expression variable;
    private Expression expression1;
    private Expression expression2;
    private Expression expression3;

    public QuestionMarkStatement(Expression variable, Expression expression1, Expression expression2, Expression expression3) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IfStatement ifstmt = new IfStatement(expression1, new AssignmentStatement(variable.toString(), expression2), new AssignmentStatement(variable.toString(), expression3));
        stack.push(ifstmt);
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typexpr = expression1.typecheck( typeEnv );
        Type typexpr2 = expression2.typecheck( typeEnv );
        Type typexpr3 = expression3.typecheck( typeEnv );

        if ( typexpr  instanceof IntType) {

            if ( typexpr2.getClass() != typexpr3.getClass())
                throw  new MyException("expression 2 and 3 don't have the same type");
            return typeEnv;
        }
        else
            throw new MyException("expression not boolType");
    }


    @Override
    public String toString() {
        return variable.toString() + "=" + expression1.toString()+"?" + expression2.toString()+":" + expression3.toString();
    }
}
