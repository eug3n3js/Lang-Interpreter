package interpreter.ast;

import java.util.List;

public class ScopeNode extends ASTNode {
    private List<ASTNode> body;

    public ScopeNode(List<ASTNode> body) {
        super(NodeType.SCOPE);
        this.body = body;
    }

    public List<ASTNode> getBody(){
        return body;
    }
}
