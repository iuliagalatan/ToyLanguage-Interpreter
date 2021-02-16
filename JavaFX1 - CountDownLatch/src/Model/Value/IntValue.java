package Model.Value;

import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;

public class IntValue implements  Value{
    private int value;

    public IntValue() {
        value = 0;
    }

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Type getType() {

        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof IntType)
        {
            IntValue object = (IntValue) obj;
            if ( object.getValue() == value)
                return true;
            return false;
        };
        return false;
    }
}
