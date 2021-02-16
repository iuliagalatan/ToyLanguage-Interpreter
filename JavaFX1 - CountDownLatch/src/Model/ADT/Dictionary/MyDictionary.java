package Model.ADT.Dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<Key, Value> implements IMyDictionary<Key, Value> {

    private HashMap<Key, Value> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<Key, Value>();
    }

    @Override
    public String toString() {

        String retString = "";
        for ( Key key : dictionary.keySet()) {
            retString += key.toString();
            retString += " <- ";
            retString += dictionary.get(key).toString() + "\n";

        }
        return retString;
    }

    @Override
    public Value get(Key key) {
        return this.dictionary.get(key);
    }

    @Override
    public Value put(Key key, Value value) {
        return this.dictionary.put(key, value);
    }

    @Override
    public boolean containsKey(Key name) {
       return this.dictionary.containsKey(name);
    }

    @Override
    public void remove(Key id) {
        this.dictionary.remove(id);
    }

    @Override
    public int size() {
        return this.dictionary.size();
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public Collection<Value> values() {
        return this.dictionary.values();
    }

    @Override
    public boolean containsValue(Value element) {
        return this.dictionary.containsValue(element);
    }

    @Override
    public Set<Key> keySet() {
        return this.dictionary.keySet();
    }


    @Override
    public Key getKey(Value value) {
        for (Key key : this.dictionary.keySet()) {
            if (this.dictionary.get(key).equals(value))
                return key;
        }
        return null;
    }

    @Override
    public IMyDictionary<Key, Value> clone() {
        IMyDictionary<Key, Value> dic = new MyDictionary<>();
        for(Key key : this.keySet())
            dic.put(key, dictionary.get(key));
        return dic;
    }

    @Override
    public HashMap<Key, Value> getHashMap() {
        return dictionary;
    }


}
