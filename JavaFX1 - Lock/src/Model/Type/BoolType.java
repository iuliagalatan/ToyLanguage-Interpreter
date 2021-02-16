package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType  implements Type{
    @Override
    public boolean equals(Object another) {
        if ( another instanceof BoolType) {
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value initialValue() {
        return new BoolValue(false);
    }
}
