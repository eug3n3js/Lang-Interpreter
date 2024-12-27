package interpreter.ast;

import java.util.List;

public class FuncCallNode extends ASTNode{
    private String name;
    private List<ASTNode> args;
    public FuncCallNode(String funcName, List<ASTNode> args) {
        super(NodeType.FUNCCALL);
        this.name = funcName;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public List<ASTNode> getArgs(){
        return args;
    }
}
