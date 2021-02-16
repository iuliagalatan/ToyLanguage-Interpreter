package Controller;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.MyList;
import Model.ADT.Stack.IMyStack;
import Model.Exception.MyException;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private ExecutorService executorService;
    public Controller(IRepository repository) {
        this.repository = repository;
    }


    public void addProgram(ProgramState program) {
        repository.addProgramState(program);
    }


    public Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap) {

        List<Integer> addresses = heap.values().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getHeapAdress();
                })
                .collect(Collectors.toList());
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()) || addresses.contains(e.getKey()) )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {
                    RefValue v1 = (RefValue)v;
                    return v1.getHeapAdress();
                })
                .collect(Collectors.toList());


    }


    public List<ProgramState> removeCompletedProgram(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());


    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) {
        programStates.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        //RUN concurrently one step for each of the existing PrgStates
        List<Callable<ProgramState>> callList = programStates.stream().filter(p-> p.isNotCompleted())
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        //start the execution of the callables
        // it returns the list of new created PrgStates (namely threads)
        try {
            List<ProgramState> newPrgList = executorService.invokeAll(callList). stream()
                    . map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).filter(p->p!=null).collect(Collectors.toList());

            //add the new created threads to the list of existing threads
            programStates.addAll(newPrgList);
            programStates.forEach(prg-> {
                try {
                    repository.logPrgStateExec(prg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //Save the current programs in the repository
            repository.setProgramLists(programStates);
            //here you can treat the possible                                                                                                 // exceptions thrown by statements                                                                                                 // execution, namely the green part   // from previous allStep method}                                                                                               })                                                                    .filter(p -> p!=null)                                                                    .collect(Collectors.toList())
        }catch (Exception exception) {
            exception.printStackTrace();
        }




    }

    public void setExecutorService(){
        executorService = Executors.newFixedThreadPool(2);
    }

    public void allStep() throws Exception {
       setExecutorService();

        //remove the completed programs

            List<ProgramState> programStateList = removeCompletedProgram(repository.getProgramLists());
            while (programStateList.size() > 0){
                for(ProgramState p : programStateList){
                    p.getHeap().setHeap((HashMap<Integer, Value>) safeGarbageCollector(getAddrFromSymTable(p.getSymbolsTable().values()),
                            p.getHeap().getHeap()));
                }
                oneStepForAllPrograms(programStateList);
                programStateList = removeCompletedProgram(repository.getProgramLists());
            }



        stopExecutorService();

        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository


        // update the repository state
        repository.setProgramLists(programStateList);

    }

    public void stopExecutorService() {
        executorService.shutdownNow();
    }


    public IRepository getRepository() {
        return repository;
    }
}
