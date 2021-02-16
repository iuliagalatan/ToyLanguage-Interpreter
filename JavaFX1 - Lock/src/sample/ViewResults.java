package sample;

import Controller.Controller;
import Model.ADT.Dictionary.IMyDictionary;
import Model.ADT.Heap.IMyHeap;
import Model.ADT.List.IMyList;
import Model.ADT.Stack.IMyStack;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.Value;
import View.Command;
import View.TextMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.util.*;

import java.net.URL;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ViewResults implements Initializable {

    private Controller controller;
    @FXML private Button oneStep;
    @FXML private TableView heapTableView;
    @FXML private ListView IdListView;
    @FXML private TableColumn adressHeapColumn;
    @FXML private TableColumn valueHeapColumn;
    @FXML private TableColumn valueSymColumn;
    @FXML private TableColumn nameSymColumn;
    @FXML private TextField nrProgramStates;
    @FXML private  TableView SymTableView;
    @FXML private  ListView OutListView;
    @FXML private  ListView exeStackListView;
    @FXML private  ListView FileListView;

    private final ObservableList<DataModel> heapData =  FXCollections.observableArrayList();
    private final ObservableList<DataModel> symData =  FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adressHeapColumn.setCellValueFactory(new PropertyValueFactory<DataModel, Integer>("key"));
        valueHeapColumn.setCellValueFactory(new PropertyValueFactory<DataModel, String>("value"));
        heapTableView.setItems(heapData);


        nameSymColumn.setCellValueFactory(new PropertyValueFactory<DataModel, Integer>("key"));
        valueSymColumn.setCellValueFactory(new PropertyValueFactory<DataModel, String>("value"));
        SymTableView.setItems(symData);

    }

    public void setController(Controller controller) {

        this.controller = controller;

        updateIds();
        updatenrProgramStates();
    }

    public void updateAll()
    {
        updateIds();
        updateSymbolTable();
        updateHeapTable();
        updatefileTable();
        updateOutput();
        updateExestack();
        updatenrProgramStates();

    }

    private void updateSymbolTable() {

        HashMap<String, Value>  hm = getCurrentProgramState().getSymbolsTable().getHashMap();

        symData.clear();
        if (hm.size() == 0)
            return;
        for (Map.Entry<String, Value> entry : hm.entrySet())
            symData.add(new DataModel(entry.getKey(), entry.getValue().toString()));

    }

    private void updateHeapTable() {

        HashMap<Integer, Value>  hm = getCurrentProgramState().getHeap().getHeap();

        heapData.clear();
        if (hm.size() == 0)
            return;
        for (Map.Entry<Integer, Value> entry : hm.entrySet())
            heapData.add(new DataModel(entry.getKey().toString(), entry.getValue().toString()));
    }

    private void updateOutput() {
        ObservableList<IMyList<Value>> data = FXCollections.observableArrayList(getCurrentProgramState().getList());
        OutListView.setItems(data);
        OutListView.refresh();

    }

    private void updatefileTable() {
        IMyDictionary<StringValue, BufferedReader> fileTable = getCurrentProgramState().getFileTable();
        List<String> data = new ArrayList<>();
        data.add(fileTable.toString());

        FileListView.setItems(FXCollections.observableList(data));
        FileListView.refresh();

    }

    private void updateExestack() {
        IMyStack<IStatement> executionStack = getCurrentProgramState().getExecutionStack();

        List<String> data = new ArrayList<>();
        for(IStatement s : executionStack.getAll()){
            data.add(s.toString());
        }

        exeStackListView.setItems(FXCollections.observableList(data));
        exeStackListView.refresh();
    }

    private void updatenrProgramStates() {
        List<ProgramState> programStates = controller.getRepository().getProgramLists();
        nrProgramStates.setText("" + programStates.size());
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStates)
    {
        return programStates.stream().map(ProgramState::getId).collect(Collectors.toList());
    }

    private void updateIds() {

        List<ProgramState> programStates = controller.getRepository().getProgramLists();
        IdListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));

    }


    public ProgramState getCurrentProgramState(){
        if(IdListView.getSelectionModel().getSelectedItem() == null)
            return null;

        int currentId = (int) IdListView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramStatebyId(currentId);
    }

    private void alert(String message){
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
        errorAlert.setHeaderText(message);
        errorAlert.showAndWait();
    }
    public void oneStepButtonClicked(MouseEvent mouseEvent) {
        if ( getCurrentProgramState() == null) {
            alert("select Id from Id'list");
            return;
        }

        if ( getCurrentProgramState().getExecutionStack().isEmpty())
        {

            alert("Program finished");
            return;
        }
        if (controller.getRepository().getProgramLists().size() == 0) {
            afterProgramExecution();
            return;
        }


        List<ProgramState> programStateList = controller.removeCompletedProgram(controller.getRepository().getProgramLists());
        for (ProgramState p : programStateList) {
            p.getHeap().setHeap((HashMap<Integer, Value>) controller.safeGarbageCollector(controller.getAddrFromSymTable(p.getSymbolsTable().values()),
                    p.getHeap().getHeap()));
        }


        controller.oneStepForAllPrograms(controller.getRepository().getProgramLists());
        updateAll();


    }
    private void afterProgramExecution() {
        this.controller.stopExecutorService();
    }

    public static class DataModel {

        private final SimpleStringProperty key;
        private final SimpleStringProperty value;

        private DataModel(String key, String value) {
            this.key = new SimpleStringProperty(key);
            this.value = new SimpleStringProperty(value);
        }

        public String getKey() {
            return key.get();
        }

        public void setKey(String key) {
            this.key.set(key);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String value) {
            this.value.set(value);
        }

    }

}
