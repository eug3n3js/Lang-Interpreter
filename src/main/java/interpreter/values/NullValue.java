package interpreter.values;

public class NullValue extends RunTimeValue implements Printable{
    public NullValue() {
        super(ValueType.NULL);
    }

    @Override
    public String toString() {
        return "NULL";
    }
}
