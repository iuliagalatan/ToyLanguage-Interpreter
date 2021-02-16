package Model.Statement;

import Model.ADT.Dictionary.IMyDictionary;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Type.Type;


public interface IStatement {

    ProgramState execute(ProgramState state) throws Exception;
    IMyDictionary<String, Type> typecheck(IMyDictionary<String,Type> typeEnv) throws MyException;
}
