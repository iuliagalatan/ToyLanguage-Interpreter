import Controller.Controller;
import Model.Expression.ArithmeticalExpression;
import Model.Expression.ConstantExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Repository.IRepository;
import Repository.Repository;
import View.ExitCommand;
import View.RunCommand;
import View.TextMenu;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {
    public static void main(String[] args) {
        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ConstantExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        List<ProgramState> prg1 = new ArrayList<ProgramState>();
        prg1.add(new ProgramState(ex1));
        IRepository repo1 = new Repository(prg1, "log.txt");
        Controller ctrl1 = new Controller(repo1);






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

        List<ProgramState> prg3 = new ArrayList<ProgramState>();
        prg3.add(new ProgramState(ex3));
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);










        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));

    }
}
