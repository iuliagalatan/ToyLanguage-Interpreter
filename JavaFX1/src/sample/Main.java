package sample;

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
import View.ExitCommand;
import View.RunCommand;
import View.TextMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Controller.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("viewGUI.fxml"));
        Parent mainWindow = mainLoader.load();


        primaryStage.setTitle("Hello World");
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        primaryStage.setScene(new Scene(mainWindow, 600, 500));


        FXMLLoader secondLoader = new FXMLLoader();
        secondLoader.setLocation(getClass().getResource("viewResults.fxml"));
        Parent secondWindow = secondLoader.load();


        //  resultsController.setController(mainController);


        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Results");
        secondaryStage.setWidth(1100);
        secondaryStage.setHeight(654);
        secondaryStage.setScene(new Scene(secondWindow, 600, 500, Color.PINK));
        secondaryStage.show();
        primaryStage.show();



        ControllerGUI mainGUI = mainLoader.getController();
        mainGUI.setViewResultscontroller(secondLoader.getController());

    }




    public static void main(String[] args) {

        launch(args);

    }


}
