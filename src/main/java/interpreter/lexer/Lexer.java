package interpreter.lexer;
import java.util.*;
import java.util.regex.*;

public class Lexer {

    private static final Map<TokenType, Pattern> TOKEN_PATTERNS = new LinkedHashMap<>();
    static {
        TOKEN_PATTERNS.put(TokenType.SCOPE_KEYWORD, Pattern.compile("^scope"));
        TOKEN_PATTERNS.put(TokenType.SCOPE_OPEN, Pattern.compile("^\\{"));
        TOKEN_PATTERNS.put(TokenType.SCOPE_CLOSE, Pattern.compile("^\\}"));
        TOKEN_PATTERNS.put(TokenType.ASSIGN, Pattern.compile("^="));
        TOKEN_PATTERNS.put(TokenType.NUMBER, Pattern.compile("^\\d+"));
        TOKEN_PATTERNS.put(TokenType.NAME, Pattern.compile("^\\w+"));
    }

    private final List<String> lines;

    public Lexer(List<String> lines) {
        this.lines = lines;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        for (var currentLine : lines){
            currentLine = currentLine.trim();
            int currentPosition = 0;
            while (currentPosition < currentLine.length()) {
                if (Character.isWhitespace(currentLine.charAt(currentPosition))) {
                    currentPosition++;
                    continue;
                }
                boolean matched = false;
                for (Map.Entry<TokenType, Pattern> entry : TOKEN_PATTERNS.entrySet()) {
                    Matcher matcher = entry.getValue().matcher(currentLine.substring(currentPosition));
                    if (matcher.find()) {
                        String matchedText = matcher.group();
                        tokens.add(new Token(entry.getKey(), matchedText.trim()));
                        currentPosition += matchedText.length();
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    throw new RuntimeException("Unexpected character at position " + currentPosition + " in line: " + currentLine);
                }
            }
            tokens.add(new Token(TokenType.END_LINE, ""));
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}