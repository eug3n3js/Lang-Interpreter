package interpreter.parser;

import interpreter.ast.*;
import interpreter.lexer.Token;
import interpreter.lexer.TokenType;
import interpreter.values.IntegerValue;

import java.util.*;

public class Parser {

    private final List<Token> tokens;
    private int currentTokenIndex = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token currentToken() {
        return tokens.get(currentTokenIndex);
    }

    private Optional<Token> nextToken() {
        if (currentTokenIndex + 1 <tokens.size())
            return Optional.of(tokens.get(currentTokenIndex + 1));
        return Optional.empty();
    }

    private void advance() {
        if (currentTokenIndex < tokens.size() - 1) {
            currentTokenIndex++;
        }
    }

    public ScopeNode parse() {
        List<ASTNode> nodeList = new ArrayList<>();
        while (!currentToken().type.equals(TokenType.EOF)) {
            nodeList.add(statement());
        }
        return new ScopeNode(nodeList);
    }

    private ASTNode statement() {
        if (currentToken().type.equals(TokenType.NAME)) {
            if (nextToken().isPresent()){
                if (nextToken().get().type.equals(TokenType.ASSIGN)){
                    return assignment();
                } else if (nextToken().get().type.equals(TokenType.NAME) || nextToken().get().type.equals(TokenType.NUMBER)){
                    return functionCall();
                } else {
                    throw new RuntimeException("Unexpected token after variable: " + currentToken().value);
                }
            } else {
                throw new RuntimeException("Unexpected last token: " + currentToken().value);
            }
        } else if (currentToken().type.equals(TokenType.SCOPE_KEYWORD)) {
            return scope();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken().value);
        }
    }

    private ASTNode assignment() {
        String varName = currentToken().value;
        advance();
        advance();
        Token valueToken = currentToken();
        ASTNode resultNode;
        if (valueToken.type.equals(TokenType.NUMBER)) {
            resultNode = new AssignmentNode(new VariableNode(varName), new ValueNode(new IntegerValue(Integer.parseInt(valueToken.value))));
        } else if (valueToken.type.equals(TokenType.NAME)) {
            resultNode = new AssignmentNode(new VariableNode(varName), new VariableNode(valueToken.value));
        } else {
            throw new RuntimeException("Expected a value or variable after '='");
        }
        advance();
        if (!currentToken().type.equals(TokenType.END_LINE)){
            throw new RuntimeException("Expected end line after assignment");
        }
        advance();
        return resultNode;
    }

    private ASTNode functionCall() {
        String functionName = currentToken().value;
        advance();
        List<ASTNode> arguments = new ArrayList<>();
        while (currentToken().type.equals(TokenType.NAME) || currentToken().type.equals(TokenType.NUMBER)) {
            if (currentToken().type.equals(TokenType.NAME)){
                arguments.add(new VariableNode(currentToken().value));
            }
            if (currentToken().type.equals(TokenType.NUMBER)){
                arguments.add(new ValueNode(new IntegerValue(Integer.parseInt(currentToken().value))));
            }
            advance();
        }
        if (!currentToken().type.equals(TokenType.END_LINE)){
            throw new RuntimeException("Expected end line after function call");
        }
        advance();
        return new FuncCallNode(functionName, arguments);
    }

    private ASTNode scope() {
        List<ASTNode> body = new ArrayList<>();
        advance();
        if (currentToken().type.equals(TokenType.SCOPE_OPEN)) {
            advance();
            if (!currentToken().type.equals(TokenType.END_LINE)){
                throw new RuntimeException("Expected end line after '{'");
            }
            advance();
            while (!currentToken().type.equals(TokenType.SCOPE_CLOSE)) {
                body.add(statement());
            }
            advance();
            if (!currentToken().type.equals(TokenType.END_LINE)){
                throw new RuntimeException("Expected end line after '}'");
            }
            advance();
        } else {
            throw new RuntimeException("Expected '{' after 'scope'");
        }
        return new ScopeNode(body);
    }
}
