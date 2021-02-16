package Model;

import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.IMyList;
import Model.ADT.List.MyList;
import Model.ADT.Stack.IMyStack;
import Model.ADT.Stack.MyStack;
import Model.Exception.MyException;
import Model.Statement.CompoundStatement;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.Value;

import javax.print.DocFlavor;
import java.io.BufferedReader;

public class ProgramState {
    private IMyStack<IStatement> executionStack;
    private IMyDictionary<String, Value> symbolsTable;
    private IMyList<Value> outputList;
    private IStatement originalProgram;
    private IMyDictionary<StringValue, BufferedReader> fileTable;
    private IMyHeap<Integer, Value> heapTable;
    private Integer id;
    static Integer count = 2;
    private IMyDictionary<Integer, Integer> lockTable;
    private Integer location = 0;

    public Integer getLocation()
    {
        location++;
        return location;
    }

    public void setLockTable(IMyDictionary<Integer, Integer> lockTable) {
        this.lockTable = lockTable;
    }

    public ProgramState(IMyStack<IStatement> stack, IMyDictionary<String, Value> symtable, IMyList<Value> list, IStatement orgPrg, IMyDictionary<StringValue,  BufferedReader> ft, IMyHeap<Integer, Value>heap, Integer id,IMyDictionary<Integer, Integer> lockTable) throws CloneNotSupportedException {
        this.executionStack = stack;
        this.symbolsTable = symtable;
        this.outputList = list;
        //this.originalProgram = ((CompoundStatement)orgPrg).clone();
        this.fileTable = ft;
       // this.originalProgram = deepcopy(orgPrg);
        this.executionStack.push(orgPrg);
        this.id = count;
        this.heapTable = heap;
        this.lockTable = lockTable;
        count++;



    }

    public ProgramState(IStatement originalProgram) {
        this.originalProgram = originalProgram;
        this.executionStack = new MyStack<>();
        this.outputList = new MyList<>();
        this.symbolsTable = new MyDictionary<>();
        this.fileTable = new MyDictionary<>();
        this.heapTable = new MyHeap<>();
        this.lockTable = new MyDictionary<>();
        this.setId(1);
        executionStack.push(originalProgram);



    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ProgramState oneStep() throws Exception {
        if (executionStack.isEmpty())
            throw  new MyException("programState execution stack is empty");
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public IMyStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public IMyDictionary<String, Value> getSymbolsTable() {
        return this.symbolsTable;
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IMyHeap<Integer, Value> getHeap(){ return this.heapTable;}

    public IMyList<Value> getList() {
        return this.outputList;
    }


    public void setSymbolsTable(IMyDictionary<String, Value> symbolsTable) {
        this.symbolsTable = symbolsTable;
    }

    public void setExecutionStack(IMyStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setOutputList(IMyList<Value> outputList) {
        this.outputList = outputList;
    }

    public void setFileTable(IMyDictionary<StringValue, BufferedReader> ft) { this.fileTable = ft;}

    public void addOut(Value value) {
        this.outputList.add(value);
    }

    @Override
    public String toString() {
        return "\n\n\n" +
                "---id---\n" + String.valueOf(id)+"\n"+
                "---OutputList--\n"+outputList.toString()+"\n"+
                "---SymbolTable--\n"+symbolsTable.toString()+"\n"+
                "--ExecutionStack--\n" + executionStack.toString()+"\n"+
                "--FileTable---\n" + fileTable.toString()+"\n"+
                "--HeapTable---\n" + heapTable.toString();
    }


    public IMyDictionary<Integer, Integer> getLockTable() {
        return this.lockTable;
    }
}
