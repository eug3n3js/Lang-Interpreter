package interpreter.values;

abstract public class RunTimeValue {
    private ValueType type;

    RunTimeValue(ValueType type){
        this.type = type;
    }

    public ValueType getType(){
        return type;
    }
}
