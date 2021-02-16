package Model.ADT.List;

public interface IMyList<TElem> {
    int size();
    String toString();
    boolean add(TElem e);
    void clear();
    boolean isEmpty();
    TElem get(int index);

}

