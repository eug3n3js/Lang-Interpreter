package interpreter.runner.executor;

import interpreter.values.Printable;
import interpreter.values.RunTimeValue;

import java.util.List;

public class FunctionExecutor {

    public void executeFuncCall(String funcName, List<RunTimeValue> args){
        switch (funcName){
            case "print" -> printFn(args);
            default -> throw new RuntimeException("Call of unknown function { " + funcName + " }");
        }
    }

    private void printFn(List<RunTimeValue> args){
        if (args.size() != 1){
            throw new RuntimeException("Function call print requires exact 1 argument as input");
        }
        if (args.get(0) instanceof Printable){
            System.out.println(args.get(0));
        } else {
            throw new RuntimeException("Cannot perform print function call with argument of type { " + args.get(0).getType() + " }");
        }
    }
}
