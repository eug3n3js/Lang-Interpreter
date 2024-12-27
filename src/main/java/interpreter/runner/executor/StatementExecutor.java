package interpreter.runner.executor;

import interpreter.ast.*;
import interpreter.values.NullValue;
import interpreter.values.RunTimeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class StatementExecutor {
    private Stack<HashMap<String, RunTimeValue>> stack;

    public StatementExecutor(Stack<HashMap<String, RunTimeValue>> stack){
        this.stack = stack;
    }

    public void executeASTNode(ASTNode node){
        switch (node.getType()){
            case ASSIGNMENT -> executeAssignment((AssignmentNode) node);
            case FUNCCALL -> executeFuncCall((FuncCallNode) node);
            case SCOPE -> executeScopeNode((ScopeNode) node);
            default -> throw new RuntimeException("Unsupported node for executing of type { " + node.getType() + " }");
        }
    }

    private void executeAssignment(AssignmentNode node){
        var nameNode = node.getName();
        var valueNode = node.getValue();
        if (valueNode.getType().equals(NodeType.VALUE)) {
            stack.peek().put(nameNode.getName(), ((ValueNode) valueNode).getValue());
        } else if (valueNode.getType().equals(NodeType.VARIABLE)) {
            stack.peek().put(nameNode.getName(), stackSearch((VariableNode) valueNode));
        } else {
            throw new RuntimeException("Unsupported type { " + valueNode.getType() + " } in assignment statement");
        }
    }

    private void executeScopeNode(ScopeNode node){
        stack.push(new HashMap<>());
        for(var bodyNode : node.getBody()){
            executeASTNode(bodyNode);
        }
        stack.pop();
    }

    private void executeFuncCall(FuncCallNode node){
        List<RunTimeValue> valArgs = new ArrayList<>();
        for (var arg : node.getArgs()){
            if (arg.getType().equals(NodeType.VALUE)){
                valArgs.add(((ValueNode) arg).getValue());
            } else if(arg.getType().equals(NodeType.VARIABLE)){
                valArgs.add(stackSearch((VariableNode) arg));
            } else {
                throw new RuntimeException("Function call { " + node.getName() + "} with unsupported argument type { " + arg.getType() + " }");
            }
        }
        new FunctionExecutor().executeFuncCall(node.getName(), valArgs);
    }

    private RunTimeValue stackSearch(VariableNode node){
        for (int i = stack.size() - 1; i >= 0; i--){
            if (stack.get(i).containsKey(node.getName())){
                return stack.get(i).get(node.getName());
            }
        }
        return new NullValue();
    }
}
