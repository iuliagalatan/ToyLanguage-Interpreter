package Model.ADT.Dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public interface IMyDictionary<Key, Value> {
    Value get(Key key);
    Value put(Key key, Value value);
    String toString();
    boolean containsKey(Key name);
    void remove(Key id);
    int size();
    public boolean isEmpty();
    Collection<Value> values();
    boolean containsValue(Value element);
    Set<Key> keySet();
    Key getKey(Value value);
    IMyDictionary<Key, Value> clone();
    public HashMap<Key, Value> getHashMap();
   // void update(Key key, Value value);


}

