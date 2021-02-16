package Model.ADT.List;

import java.util.ArrayList;
import java.util.List;

public class MyList<TElem> implements IMyList<TElem> {
    private ArrayList<TElem> list;

    public MyList() {
        list = new ArrayList<TElem>();
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(TElem e) {
        return list.add(e);
    }

    @Override
    public void clear() {
         list.clear();
    }

    @Override
    public boolean isEmpty() {
        if( list.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public TElem get(int index) {
        return list.get(index);
    }
}
