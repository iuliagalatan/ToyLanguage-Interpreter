package Model.Statement;


import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.Expression.RelationalExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;

public class ForStatement implements IStatement{
    private String var;
    private Expression e1;
    private Expression e2;
    private Expression e3;
    private IStatement statement;


    public ForStatement(String var, Expression a, Expression b, Expression e3, IStatement s1){
        this.var = var;
        this.e1 = a;
        this.e2 = b;
        this.e3 = e3;
        this.statement = s1;
    }



    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyStack<IStatement> stack = state.getExecutionStack();

        IStatement forStatement = new CompoundStatement(new VariableDeclarationStatement(var, new IntType()),
                new CompoundStatement(new AssignmentStatement(var, e1),
                        new WhileStatement(new RelationalExpression(new VariableExpression(var), e2, "<"),
                                new CompoundStatement(statement, new AssignmentStatement(var, e3)) ) ));


        stack.push(forStatement);
        //state.setStack(stack);
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        //  Type vartype = typeEnv.get(var);
        Type typexp1 = e1.typecheck(typeEnv);
        Type typexp2 = e2.typecheck(typeEnv);
        Type typexp3 = e3.typecheck(typeEnv);

        statement.typecheck(typeEnv.clone());

        if (typexp1.equals(new IntType()) && typexp2.equals(new IntType()) && typexp3.equals(new IntType()))
            return typeEnv;
        else throw new MyException("e1 or e2 or e3 does not have int type");

    }
    public String toString() {
        return "for( " + this.var + "=" + this.e1.toString() + "; " + this.var + "<" + this.e2.toString() + "; " + this.var + "=" + this.e3.toString() + " ) \n " + this.statement.toString();
    }


}