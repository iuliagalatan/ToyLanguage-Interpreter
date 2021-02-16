package View;

import Controller.Controller;
import Model.ADT.List.MyList;
import Model.Statement.IStatement;

import java.util.List;
import java.util.Scanner;

public class view {

    private List<Controller> controller;

    public view(List<Controller> controller) {
        this.controller = controller;

    }

    public void Menu()
    {

        System.out.println("Option Menu: ");
        System.out.println("1. int v; v=2; Print(v)");
        System.out.println("2. int a;int b; a=2+3*5;b=a+1;Print(b");
        System.out.println("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");


    }

    private int getInteger(Scanner scanner) throws Exception{
        try {
            return Integer.parseInt((scanner.nextLine()));
        }
        catch (Exception e){
            throw new Exception("Invalid integer");
        }
    }


    public void start()
    {
        Scanner scanner = new Scanner(System.in);

        while ( true )
        {
            System.out.println("Choose an option:");
            this.Menu();

            int option = -1;
            try {
                option = getInteger(scanner);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if(option == -1) break;
            if(option < controller.size()) {
                try{
                    controller.get(option-1).allStep();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        }
        scanner.close();
    }
}
