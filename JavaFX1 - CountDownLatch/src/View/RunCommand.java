package View;

import Controller.Controller;
import Model.Exception.MyException;

import java.io.IOException;

public class RunCommand extends Command{
    private Controller controller;

    public RunCommand(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }


    public Controller getController() {
        return controller;
    }

    @Override
    public void execute() {
        try{
            controller.allStep();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }
}