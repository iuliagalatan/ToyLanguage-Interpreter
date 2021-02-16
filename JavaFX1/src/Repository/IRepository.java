package Repository;

import Model.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    public ProgramState getCurrentProgram() throws Exception;
    List<ProgramState> getProgramLists();
    void addProgramState(ProgramState prg);
    void setProgramLists(List<ProgramState> ProgramList);
    public void logPrgStateExec( ProgramState programState ) throws IOException;
    ProgramState getProgramStatebyId(int currentId);
}
