package interpreter.runner;

import interpreter.ast.ScopeNode;
import interpreter.lexer.Lexer;
import interpreter.lexer.Token;
import interpreter.loader.CodeLoader;
import interpreter.parser.Parser;
import interpreter.runner.executor.StatementExecutor;
import interpreter.values.RunTimeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Interpreter {
    private List<String> rawCode;
    private List<Token> tokenizedCode;
    private ScopeNode program;
    private Stack<HashMap<String, RunTimeValue>> stack;

    private boolean loadCodeFile(String pathFile){
        try{
            rawCode = CodeLoader.getCodeLines(pathFile);
            return true;
        } catch (IllegalArgumentException e){
            System.out.println("Error occurred while loading code from file: " + e.getMessage());
            return false;
        }
    }

    private boolean compileProgram(){
        try{
            tokenizedCode = new Lexer(rawCode).tokenize();
        } catch (RuntimeException e){
            System.out.println("Error occurred while tokenizing code: " + e.getMessage());
            return false;
        }
        try{
            program = new Parser(tokenizedCode).parse();
        } catch (RuntimeException e){
            System.out.println("Error occurred while converting code to AST: " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean executeCode(){
        StatementExecutor statementExecutor = new StatementExecutor(stack);
        try {
            statementExecutor.executeASTNode(program);
        } catch (RuntimeException e){
            System.out.println("Error occurred while running code: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void run(String[] args){
        if (args.length != 1){
            System.out.println("Interpreter needs exact 1 argument to run (path to source code file)");
            return;
        }
        stack = new Stack<>();
        if (!loadCodeFile(args[0])){
            return;
        }
        if(!compileProgram()){
            return;
        }
        executeCode();
    }


}
