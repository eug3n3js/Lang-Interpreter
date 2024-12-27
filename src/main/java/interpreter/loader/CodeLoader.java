package interpreter.loader;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CodeLoader {

    public static List<String> getCodeLines(String fileName) throws IllegalArgumentException{
        if (!fileName.endsWith(".txt")){
            throw new IllegalArgumentException("Source code file must be .txt");
        }
        Path of = Path.of(fileName);
        if (!Files.exists(of)){
            throw new IllegalArgumentException("Source code file does not exists");
        }
        try{
            return Files.readAllLines(of);
        } catch (IOException ignored){
            throw new IllegalArgumentException("Cannot read source code from file");
        }
    }
}
