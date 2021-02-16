package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.Exception.MyException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.Value;



public class PrintStatement implements IStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {

        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        //IMyList<Value> output = state.getOutputList();
        Value result = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        state.addOut(result);
        return null;

    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
