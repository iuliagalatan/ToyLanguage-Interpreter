package Model.ADT.BarrierTable;

import Model.ADT.Heap.IMyHeap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class BarrierTable<Adress, Content> implements IBarrierTable<Adress, Content> {
    private HashMap<Adress,Content> heap;
    private int freeAddress;

    public BarrierTable(HashMap<Adress, Content> heap, int freeAddress) {
        this.heap = heap;
        this.freeAddress = freeAddress;
    }

    public BarrierTable() {
        freeAddress = 1;
        this.heap = new HashMap<Adress, Content>();

    }

    @Override
    public void put(Adress adr, Content o) {
        this.heap.put(adr, o);
    }

    @Override
    public Content get(Adress adr) {
        return this.heap.get(adr);
    }

    @Override
    public boolean containsAdress(Object adr) {
        return this.heap.containsKey(adr);
    }

    @Override
    public HashMap getHeap() {
        return heap;
    }

    @Override
    public Collection<Content> values() {
        return heap.values();
    }

    @Override
    public int getFreeAdress() {
        return freeAddress;
    }


    @Override
    public void setFreeAdress(int Adr) {
        this.freeAddress = Adr;
    }

    @Override
    public void update(Object adr, Object c) {
        heap.replace((Adress)adr, (Content)c);
    }


    @Override
    public void setHeap(HashMap<Adress, Content> heap) {
        this.heap = heap;
    }

    @Override
    public void setContent(Set<Map.Entry<Adress, Content>> set) {
        heap.clear();
        for (Map.Entry<Adress, Content> entry : set) {
            this.put(entry.getKey(), entry.getValue());
        }
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(Adress adress : this.heap.keySet())
        {
            string.append(adress.toString()).append("->").append(this.heap.get(adress).toString()).append("\n");
        }
        string.append("\n");
        return string.toString();
    }
}
