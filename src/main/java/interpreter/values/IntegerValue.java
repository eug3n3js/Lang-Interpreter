package interpreter.values;

public class IntegerValue extends RunTimeValue implements Printable {
    private Integer value;

    public IntegerValue(int val) {
        super(ValueType.INTEGER);
        this.value = val;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
