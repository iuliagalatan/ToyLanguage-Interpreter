package Model.ADT.Stack;
import  Model.Statement.IStatement;

import java.util.Stack;

public interface IMyStack<T> {

    T pop();
    int size();
    void push(T element);
    boolean isEmpty();
    T top();
    String toString();
    public Stack<T> getAll();

}
