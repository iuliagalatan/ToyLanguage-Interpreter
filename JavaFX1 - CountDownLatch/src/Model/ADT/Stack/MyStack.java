package Model.ADT.Stack;

import java.util.Stack;

public class MyStack<TElem> implements  IMyStack<TElem>{
    private Stack<TElem> stack;

    public MyStack() {

        this.stack = new Stack<TElem>();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public TElem pop() {
        return stack.pop();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public void push(TElem elem) {
        this.stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public TElem top() {
        return this.stack.peek();
    }

    @Override
    public Stack<TElem> getAll(){
        return this.stack;
    }
}
