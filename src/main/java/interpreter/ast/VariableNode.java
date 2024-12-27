package interpreter.ast;

public class VariableNode extends ASTNode{
    private String varName;

    public VariableNode(String varName) {
        super(NodeType.VARIABLE);
        this.varName = varName;
    }

    public String getName(){
        return varName;
    }
}
