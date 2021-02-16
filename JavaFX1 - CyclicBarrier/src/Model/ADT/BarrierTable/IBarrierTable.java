package Model.ADT.BarrierTable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IBarrierTable<Adress, Content> {



        void put( Adress adr, Content content);
        public Content get(Adress adr);
        public boolean containsAdress(Adress adr);
        public int getFreeAdress();
        public void setFreeAdress(int Adr);
        public void update(Adress adr, Content content);
        HashMap<Adress, Content> getHeap();
        Collection<Content> values();
        void setHeap(HashMap<Adress, Content> hashMap);
        public void setContent(Set<Map.Entry<Adress, Content>> set);




}
