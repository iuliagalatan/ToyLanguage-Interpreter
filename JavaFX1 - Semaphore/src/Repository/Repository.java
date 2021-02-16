package Repository;

import Model.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> programStates;
    private String logFile;

    public Repository() {
        programStates = new ArrayList<ProgramState>();
        logFile = "";
    }

    public Repository(List<ProgramState> programStates, String File) {
        this.programStates = programStates;
        logFile = File;
    }
    public Repository(List<ProgramState> programStates) {

        this.programStates = programStates;
        logFile ="";
    }

    @Override
    public ProgramState getCurrentProgram() throws Exception {
        if ( programStates.size() > 0)
            return programStates.get(0);
        throw  new Exception("no more ProgramStates");
    }

    @Override
    public List<ProgramState> getProgramLists() {
        return programStates;
    }


    @Override
    public void addProgramState(ProgramState programState) {
        programStates.add(programState);
    }

    @Override
    public void setProgramLists(List<ProgramState> ProgramList) {
        this.programStates = ProgramList;
    }


    @Override
    public void logPrgStateExec( ProgramState programState ) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFile,
                true)));
        logFile.write(programState.toString());
        logFile.close();

    }

    @Override
    public ProgramState getProgramStatebyId(int currentId) {
        return programStates.stream().filter(pr->pr.getId().equals(currentId)).findAny().orElse(null);
    }
}
