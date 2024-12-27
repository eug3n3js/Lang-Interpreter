package interpreter.ast;

public abstract class ASTNode {
    private NodeType type;

    public ASTNode(NodeType type){
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }
}
