package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements  Value{
    private String value;

    public StringValue() {
        value ="";
    }

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof StringValue) {
            return true;
        }
        return false;
    }
}
