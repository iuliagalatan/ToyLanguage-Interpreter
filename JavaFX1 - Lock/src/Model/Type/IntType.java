package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements  Type{

    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }
    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value initialValue() {
        return new IntValue(0);
    }
}
