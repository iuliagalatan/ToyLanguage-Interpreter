package Model.Value;

import Model.Type.BoolType;
import Model.Type.StringType;
import Model.Type.Type;

public class BoolValue implements  Value {
    private boolean value;

    public BoolValue() {
        value = false;
    }

    public BoolValue( boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public boolean isTrue() {
        return value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof BoolType)
        {
            BoolValue object = (BoolValue) obj;
            if ( object.getValue() == value)
                return true;
            return false;
        };
        return false;
    }


}
