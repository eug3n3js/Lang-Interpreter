package interpreter.ast;

public class AssignmentNode extends ASTNode{
    private VariableNode var;
    private ASTNode value;

    public AssignmentNode(VariableNode var, ASTNode value) {
        super(NodeType.ASSIGNMENT);
        this.var = var;
        this.value = value;
    }

    public VariableNode getName(){
        return var;
    }

    public ASTNode getValue(){
        return value;
    }
}
