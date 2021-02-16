package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements  Type{
    @Override
    public Value initialValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }
}
