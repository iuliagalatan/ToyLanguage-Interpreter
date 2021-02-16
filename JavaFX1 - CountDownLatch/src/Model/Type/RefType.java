package Model.Type;

import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements  Type{
    private Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInnner() {
        return inner;
    }

    @Override
    public String toString() {
        return "Ref(" + this.inner.toString() +")";
    }

    @Override
    public boolean equals(Object another) {
        if( another instanceof RefType)
            return inner.equals(((RefType) another).inner);
        return false;
    }

    @Override
    public Value initialValue() {
       return new RefValue(0, inner);

    }
}
