package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Type.*;
import Model.Value.RefValue;
import Model.Value.Value;

public class VariableDeclarationStatement implements  IStatement{
    private Type type;
    private String id;


    public VariableDeclarationStatement(String id,Type type) {
        this.type = type;
        this.id = id;

    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyDictionary<String, Value> symTable = state.getSymbolsTable();
        IMyStack<IStatement> stack = state.getExecutionStack();
        // stack.pop();
        if ( symTable.containsKey(id) ){
            return null;
        }
        else
        {
            if ( type.getClass() == BoolType.class) {
                BoolType boolType = (BoolType)type;
                symTable.put(id, boolType.initialValue());
            }
            else
                if (type.getClass() == IntType.class) {
                    IntType intType = (IntType)type;
                    symTable.put(id, intType.initialValue());
                }
                else
                    if ( type.getClass() == StringType.class) {
                        StringType stringType = (StringType)type;
                        symTable.put(id, stringType.initialValue());
                    }
                    else
                        if ( type.getClass() == RefType.class) {
                            RefType reftype = (RefType)type;
                            symTable.put(id, reftype.initialValue());
                        }
        }
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typecheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(id, type);

        return typeEnv;
    }


    @Override
    public String toString() {
        return type.toString() + " "+ id;
    }
}
