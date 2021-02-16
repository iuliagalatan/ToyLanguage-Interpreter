package Model.ADT.BarrierTable;

import Model.ADT.Heap.IMyHeap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BarrierTable<Adress, Content> implements IBarrierTable<Adress, Content> {
    private HashMap<Adress,Content> heap;
    private int freeAddress;
    private static Lock lock = new ReentrantLock();

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
        lock.lock();
        this.heap.put(adr, o);
        lock.unlock();
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
        lock.lock();
        heap.clear();
        for (Map.Entry<Adress, Content> entry : set) {
            this.put(entry.getKey(), entry.getValue());
        }
        lock.unlock();
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
