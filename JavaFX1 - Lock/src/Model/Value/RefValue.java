package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value {
    private int heapAdress;
    private Type locationType;

    public RefValue(int heapAdress, Type locationType) {
        this.heapAdress = heapAdress;
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public Type getLocationType() {
        return locationType;
    }

    public int getHeapAdress() {
        return heapAdress;
    }

    @Override
    public String toString() {
        return "heapAdress: " + String.valueOf(heapAdress) + " location Type:" + locationType.toString();
    }
}
