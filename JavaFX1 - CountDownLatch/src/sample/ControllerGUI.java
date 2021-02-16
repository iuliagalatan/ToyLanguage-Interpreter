package sample;

import Controller.Controller;
import Model.ADT.Dictionary.MyDictionary;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import View.Command;
import View.ExitCommand;
import View.RunCommand;
import View.TextMenu;
import com.sun.prism.shader.AlphaTextureDifference_Color_AlphaTest_Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerGUI implements Initializable {

    private ViewResults viewResultscontroller;

   @FXML private TableView tableView;
   @FXML private Button runButton;
   @FXML private TableColumn idColumn;
   @FXML private TableColumn exerciseColumn;


    public void setViewResultscontroller(ViewResults viewResultscontroller) {
        this.viewResultscontroller = viewResultscontroller;
    }


    public void clickedRunButton(MouseEvent mouseEvent) {

        RunCommand command = (RunCommand) tableView.getSelectionModel().getSelectedItem();
      //  command.execute();

        Controller cntrl = command.getController();
        cntrl.setExecutorService();
        viewResultscontroller.setController(cntrl);



    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        TextMenu menu = intialiseTableView();

        ObservableList<Command> data = FXCollections.observableArrayList(menu.getCommands());


        idColumn.setCellValueFactory(new PropertyValueFactory<TextMenu, String>("key"));
        exerciseColumn.setCellValueFactory(new PropertyValueFactory<TextMenu, String>("description"));

        tableView.setItems(data);


    }

    private static boolean TypeChecker(IStatement ex1) {
        try {
            ex1.typecheck(new MyDictionary<String, Type>());
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    private  TextMenu intialiseTableView() {
        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ConstantExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        if (TypeChecker(ex1)) return null;
        List<ProgramState> prg1 = new ArrayList<ProgramState>();
        prg1.add(new ProgramState(ex1));
        IRepository repo1 = new Repository(prg1, "log.txt");
        Controller ctrl1 = new Controller(repo1);
        //  MyIHeap<Integer,Integer> heap1 = new MyHeap<Integer,Integer>();



        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticalExpression('+',new ConstantExpression(new IntValue(2)),
                                        new ArithmeticalExpression('*',new ConstantExpression(new IntValue(3)),
                                                new ConstantExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticalExpression('+',new VariableExpression("a"),
                                                new ConstantExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));


        if (TypeChecker(ex2)) return null;
        List<ProgramState> prg2 = new ArrayList<ProgramState>();
        prg2.add(new ProgramState(ex2));
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);



        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v",new ConstantExpression(new IntValue(2))), new AssignmentStatement("v", new ConstantExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        if (TypeChecker(ex3)) return null;
        List<ProgramState> prg3 = new ArrayList<ProgramState>();
        prg3.add(new ProgramState(ex3));
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);




        String varf;
        varf="test.in";
        Value value = new StringValue(varf);
        Expression expr = new ConstantExpression(value);

        // Expression e = new VariableExpression("var");

        IStatement ex4 =new CompoundStatement(new VariableDeclarationStatement("var", new IntType()),
                new CompoundStatement(
                        new CompoundStatement(
                                new OpenRFile(expr),
                                new CompoundStatement(new ReadRFile(expr, "var"),new PrintStatement(new VariableExpression("var")))
                        ), new CloseRFile(expr)));
        if (TypeChecker(ex4)) return null;
        List<ProgramState> prg4 = new ArrayList<ProgramState>();
        prg4.add(new ProgramState(ex4));
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctrl4 = new Controller(repo4);




        IStatement ex5 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticalExpression('+',new ConstantExpression(new IntValue(2)),
                                        new ArithmeticalExpression('*',new ConstantExpression(new IntValue(3)),
                                                new ConstantExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticalExpression('+',new VariableExpression("a"),
                                                new ConstantExpression(new IntValue(1)))),
                                        new PrintStatement(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), "<="))))));

        if (TypeChecker(ex5)) return null;
        List<ProgramState> prg5 = new ArrayList<ProgramState>();
        prg5.add(new ProgramState(ex5));
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller ctrl5 = new Controller(repo5);





        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ConstantExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticalExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                        new ConstantExpression(new IntValue(5)), '+')))))));
        if (TypeChecker(ex6)) return null;
        List<ProgramState> prg6 = new ArrayList<ProgramState>();
        prg6.add(new ProgramState(ex6));
        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller ctrl6 = new Controller(repo6);





        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ConstantExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new WriteHeapStatement("v", new ConstantExpression(new IntValue(30))),
                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                        new PrintStatement((new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))))));

        if (TypeChecker(ex7)) return null;
        List<ProgramState> prg7 = new ArrayList<ProgramState>();
        prg7.add(new ProgramState(ex7));
        IRepository repo7 = new Repository(prg7, "log7.txt");
        Controller ctrl7 = new Controller(repo7);


        IStatement ex8= new CompoundStatement(new VariableDeclarationStatement("v", new IntType()) , new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(new IntValue(6))),
                new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ConstantExpression(new IntValue(3)), ">"),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",
                                new ArithmeticalExpression(new VariableExpression("v"), new ConstantExpression(new IntValue(1)), '-')))), new PrintStatement(new VariableExpression("v")))));

        if (TypeChecker(ex8)) return null;
        List<ProgramState> prg8 = new ArrayList<>();
        prg8.add(new ProgramState(ex8));
        IRepository repo8 = new Repository(prg8, "log8.txt");
        Controller ctrl8 = new Controller(repo8);



        IStatement ex9 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ConstantExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ConstantExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ConstantExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ConstantExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )

                )
        );

        if (TypeChecker(ex9)) return null;
        List<ProgramState> prg9 = new ArrayList<>();
        prg9.add(new ProgramState(ex9));
        IRepository repo9 = new Repository(prg9, "log9.txt");
        Controller ctrl9 = new Controller(repo9);


        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new ForkStatement(
                new CompoundStatement(new VariableDeclarationStatement("a" ,new IntType()), new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(new IntValue(10))), new PrintStatement(new VariableExpression("a"))))
        ));

        if (TypeChecker(ex10)) return null;
        List<ProgramState> prg10 = new ArrayList<>();
        prg10.add(new ProgramState(ex10));
        IRepository repo10 = new Repository(prg10, "log10.txt");
        Controller ctrl10 = new Controller(repo10);


        IStatement exswitch= new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),new CompoundStatement(new AssignmentStatement("c", new ConstantExpression(new IntValue(5))), new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(new IntValue(1))),
                new CompoundStatement(new AssignmentStatement("b", new ConstantExpression( new IntValue(2))),new CompoundStatement(
                new SwitchStatement(new ArithmeticalExpression(new VariableExpression("a"), new ConstantExpression(new IntValue(10)), '*')
                        , new ArithmeticalExpression(new VariableExpression("c"), new VariableExpression("b"), '*'),
                        new ConstantExpression(new IntValue(10)), new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                        new CompoundStatement(new PrintStatement(new ConstantExpression(new IntValue(100))), new PrintStatement(new ConstantExpression(new IntValue(200)))),
                        new PrintStatement(new ConstantExpression(new IntValue(300)))),  new PrintStatement(new ConstantExpression(new IntValue(300))))))))));

        if (TypeChecker(exswitch)) {System.out.println("nu meerge typecheck"); return null;}
        List<ProgramState> prg11 = new ArrayList<>();
        prg11.add(new ProgramState(exswitch));
        IRepository repo11 = new Repository(prg11, "log11.txt");
        Controller ctrl11 = new Controller(repo11);


        IStatement exQuestion = new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())), new CompoundStatement(
                new VariableDeclarationStatement("b", new RefType(new IntType())), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new NewStatement("a", new ConstantExpression(new IntValue(0))), new CompoundStatement(
                        new NewStatement("b", new ConstantExpression(new IntValue(0))), new CompoundStatement(
                                new WriteHeapStatement("a", new ConstantExpression(new IntValue(1))), new CompoundStatement(
                        new WriteHeapStatement("b", new ConstantExpression(new IntValue(2))), new CompoundStatement(new QuestionMarkStatement(new VariableExpression("v"),
                        new RelationalExpression(new ReadHeapExpression(new VariableExpression("a")),
                                new ReadHeapExpression(new VariableExpression("b")), "<")
                        , new ConstantExpression(new IntValue(100)), new ConstantExpression(new IntValue(200))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new QuestionMarkStatement(new VariableExpression("v"), new RelationalExpression(new ArithmeticalExpression(new ReadHeapExpression(new VariableExpression("b")), new ConstantExpression(new IntValue(2)), '-'),
                                new ReadHeapExpression(new VariableExpression("a")), ">"), new ConstantExpression(new IntValue(100)), new ConstantExpression(new IntValue(200))), new PrintStatement(new VariableExpression("v"))))))))))));




        if (TypeChecker(exQuestion)) {System.out.println("nu merge typecheck quest"); return null;}
        List<ProgramState> prg12 = new ArrayList<>();
        prg12.add(new ProgramState(exQuestion));
        IRepository repo12 = new Repository(prg12, "log12.txt");
        Controller ctrl12 = new Controller(repo12);



        IStatement exRepeat = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(new IntValue(0))),
                new CompoundStatement(new RepeatUntilStatement(new CompoundStatement( new ForkStatement(new CompoundStatement( new PrintStatement(new VariableExpression("v")),new AssignmentStatement("v", new ArithmeticalExpression(new VariableExpression("v"),
                        new ConstantExpression(new IntValue(1)), '-')) )),
                        new AssignmentStatement("v", new ArithmeticalExpression(new VariableExpression("v"),
                        new ConstantExpression(new IntValue(1)), '+')) ), new RelationalExpression(new VariableExpression("v"), new ConstantExpression(new IntValue(3)), "==")), new PrintStatement(new ArithmeticalExpression(new VariableExpression("v"), new ConstantExpression(new IntValue(10)), '*')))));



        if (TypeChecker(exRepeat)) {System.out.println("nu merge typecheck repeatt"); return null;}
        List<ProgramState> prg13 = new ArrayList<>();
        prg13.add(new ProgramState(exRepeat));
        IRepository repo13 = new Repository(prg13, "log13.txt");
        Controller ctrl13 = new Controller(repo13);



        IStatement exSleep = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(new IntValue(10))),
                new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignmentStatement("v", new ArithmeticalExpression( new VariableExpression("v"), new ConstantExpression(new IntValue(1)), '-')), new CompoundStatement(
                        new AssignmentStatement("v", new ArithmeticalExpression( new VariableExpression("v"), new ConstantExpression(new IntValue(1)), '-')), new PrintStatement(new VariableExpression("v"))
                ))), new CompoundStatement(new SleepStatement(new IntValue(10)), new PrintStatement(new ArithmeticalExpression(new VariableExpression("v"), new ConstantExpression(new IntValue(10)), '*'))))));



        if (TypeChecker(exSleep)) {System.out.println("nu merge typecheck sleep"); return null;}
        List<ProgramState> prg14 = new ArrayList<>();
        prg14.add(new ProgramState(exSleep));
        IRepository repo14 = new Repository(prg14, "log14.txt");
        Controller ctrl14 = new Controller(repo14);


        IStatement exfor = new CompoundStatement(
                new VariableDeclarationStatement("a", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("a", new ConstantExpression(new IntValue(20))),
                        new CompoundStatement( new CompoundStatement(new VariableDeclarationStatement("v", new IntType()) ,new ForStatement("v", new ConstantExpression(new IntValue(0)),
                                new ConstantExpression(new IntValue(3)), new ArithmeticalExpression(new VariableExpression("v"),new ConstantExpression(new IntValue(1)), '+'),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")
                                ),new AssignmentStatement("v", new ArithmeticalExpression(new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a")), '+' )))))),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));


        IStatement exfor2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("a", new ConstantExpression(new IntValue(20))),
                        new CompoundStatement( new ForStatement("v", new ConstantExpression(new IntValue(0)),
                                new ConstantExpression(new IntValue(3)), new ArithmeticalExpression(new VariableExpression("v"),new ConstantExpression(new IntValue(1)), '+'),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")
                                ),new AssignmentStatement("v", new ArithmeticalExpression(new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a")), '+' ))))),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));





        if (TypeChecker(exfor)) {System.out.println("nu merge typecheck for"); return null;}
        List<ProgramState> prg15 = new ArrayList<>();
        prg15.add(new ProgramState(exfor));
        IRepository repo15 = new Repository(prg15, "log15.txt");
        Controller ctrl15 = new Controller(repo15);

        //Ref int v1; Ref int v2; Ref int v3; int cnt;
        //new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));
        //fork(wh(v1,rh(v1)*10));print(rh(v1));countDown(cnt);
        //fork(wh(v2,rh(v2)*10));print(rh(v2));countDown(cnt);
        //fork(wh(v3,rh(v3)*10));print(rh(v3));countDown(cnt))));
        //await(cnt);
        //print(100);
        //countDown(cnt);
        //
        //print(100)
        IStatement exlatch = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())),new CompoundStatement(
                        new VariableDeclarationStatement("v3", new RefType(new IntType())), new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(new NewStatement("v1", new ConstantExpression(new IntValue(2))), new CompoundStatement(
                                new NewStatement("v3", new ConstantExpression(new IntValue(4))), new CompoundStatement( new NewStatement("v2", new ConstantExpression(new IntValue(3))) , new CompoundStatement(
                                        new NewLatch("cnt", new ReadHeapExpression(new VariableExpression("v2"))), new CompoundStatement(
                                                new ForkStatement(new WriteHeapStatement("v1", new ArithmeticalExpression( new ReadHeapExpression(new VariableExpression("v1")), new ConstantExpression(new IntValue(10)), '*')) ), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new CompoundStatement(new CountDownStatement("cnt"), new CompoundStatement(
                                new ForkStatement(new WriteHeapStatement("v2", new ArithmeticalExpression( new ReadHeapExpression(new VariableExpression("v2")), new ConstantExpression(new IntValue(10)), '*'))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))), new CompoundStatement(new CountDownStatement("cnt"), new CompoundStatement(
                                new ForkStatement(new WriteHeapStatement("v3", new ArithmeticalExpression( new ReadHeapExpression(new VariableExpression("v3")), new ConstantExpression(new IntValue(10)), '*'))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))), new CompoundStatement(new CountDownStatement("cnt"), new CompoundStatement(
                                        new await("cnt"), new CompoundStatement(new PrintStatement(new ConstantExpression(new IntValue(100))), new CompoundStatement(
                                                new CountDownStatement("cnt"), new PrintStatement(new ConstantExpression(new IntValue(100)))
                        ))

                        ) )
                        )
                        )
                        )))
                )))))))))));

        PrintWriter pw = null;
        try {
            pw = new PrintWriter("log16.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.close();

        if (TypeChecker(exlatch)) {System.out.println("nu merge typecheck latch"); return null;}
        List<ProgramState> prg16 = new ArrayList<>();
        prg16.add(new ProgramState(exlatch));
        IRepository repo16 = new Repository(prg16, "log16.txt");
        Controller ctrl16 = new Controller(repo16);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunCommand("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunCommand("5", ex5.toString(), ctrl5));
        menu.addCommand(new RunCommand("6", ex6.toString(), ctrl6));
        menu.addCommand(new RunCommand("7", ex7.toString(), ctrl7));
        menu.addCommand(new RunCommand("8", ex8.toString(), ctrl8));
        menu.addCommand(new RunCommand("9", ex9.toString(), ctrl9));
        menu.addCommand(new RunCommand("10", ex10.toString(), ctrl10));
        menu.addCommand(new RunCommand("11", exswitch.toString(), ctrl11));
        menu.addCommand(new RunCommand("12", exQuestion.toString(), ctrl12));
        menu.addCommand(new RunCommand("13", exRepeat.toString(), ctrl13));
        menu.addCommand(new RunCommand("14", exSleep.toString(), ctrl14));
        menu.addCommand(new RunCommand("15", exfor.toString(), ctrl15));
        menu.addCommand(new RunCommand("16", exlatch.toString(), ctrl16));

        return menu;
    }



}
