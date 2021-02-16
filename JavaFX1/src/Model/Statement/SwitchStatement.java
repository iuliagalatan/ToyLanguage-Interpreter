package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.Expression.RelationalExpression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;

public class SwitchStatement implements  IStatement{
    private Expression expression;
    private Expression expression1;
    private Expression expression2;
    private IStatement statement1;
    private IStatement statement2;
    private IStatement statement3;



    public SwitchStatement(Expression expression, Expression expression1, Expression expression2, IStatement statement1, IStatement statement2, IStatement statement3) {

        this.expression = expression;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.statement3 = statement3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyStack<IStatement> stack = state.getExecutionStack();

        IfStatement ifStatement = new IfStatement(new RelationalExpression(expression, expression1, "=="),statement1, new IfStatement(new RelationalExpression(expression, expression2, "=="), statement2, statement3) );
        stack.push(ifStatement);
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typexpr = expression.typecheck( typeEnv );
        Type typexpr2 = expression2.typecheck( typeEnv );
        Type typexpr3 = expression1.typecheck( typeEnv );

        statement1.typecheck(typeEnv.clone());
        statement3.typecheck(typeEnv.clone());
        statement2.typecheck(typeEnv.clone());

        if (typexpr.getClass() == typexpr2.getClass() && typexpr.getClass() == typexpr3.getClass())
            return typeEnv;
        else
            throw  new MyException("expression1,2,3 do not have the same types");
       /* if (typexpr instanceof IntType) {
            if ( typexpr2 instanceof  IntType && typexpr3 instanceof IntType){
                return typeEnv;
            }
            else {
                throw  new MyException("expression1,2,3 do not have the same types");
            }
        }*/






    }


    @Override
    public String toString() {
        return "switch( " + expression.toString() + ") " + "(case "+ expression1.toString() + ") " + statement1.toString() + '\n' +
                "(case "+ expression2.toString() + ") " + statement2.toString() + '\n' +
                "(default " + statement3.toString() +")";
    }
}
