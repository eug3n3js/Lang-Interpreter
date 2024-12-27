package interpreter.ast;

import interpreter.values.RunTimeValue;

public class ValueNode extends ASTNode{
    private RunTimeValue value;

    public ValueNode(RunTimeValue value) {
        super(NodeType.VALUE);
        this.value = value;
    }

    public RunTimeValue getValue(){
        return value;
    }
}
