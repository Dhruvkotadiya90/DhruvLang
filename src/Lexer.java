import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public List<Token> tokenize(String code) {

        List<Token> tokens = new ArrayList<>();

        int i = 0;

        while (i < code.length()) {

            char current = code.charAt(i);

            // Ignore spaces
            if (Character.isWhitespace(current)) {
                i++;
                continue;
            }

            // Number
            if (Character.isDigit(current)) {

                StringBuilder number = new StringBuilder();

                while (i < code.length()
                        && Character.isDigit(code.charAt(i))) {

                    number.append(code.charAt(i));
                    i++;
                }

                tokens.add(new Token("NUMBER", number.toString()));

                continue;
            }

            // Word
            if (Character.isLetter(current)) {

                StringBuilder word = new StringBuilder();

                while (i < code.length()
                        && Character.isLetterOrDigit(code.charAt(i))) {

                    word.append(code.charAt(i));
                    i++;
                }

                String value = word.toString();

                if (value.equals("let")
                    || value.equals("print")
                    || value.equals("if")
                    || value.equals("else")
                    || value.equals("while")
                    || value.equals("fn")
                    || value.equals("return")) {
            
                tokens.add(new Token("KEYWORD", value));
            
            } else {
            
                tokens.add(new Token("IDENTIFIER", value));
            }

                continue;
            }

            // Operators
            if (current == '+') {

                tokens.add(new Token("PLUS", "+"));

            } else if (current == '-') {

                tokens.add(new Token("MINUS", "-"));

            } else if (current == '*') {

                tokens.add(new Token("MULTIPLY", "*"));

            } else if (current == '/') {

                tokens.add(new Token("DIVIDE", "/"));

            } else if (current == '=') {

                tokens.add(new Token("EQUAL", "="));

            } else if (current == '(') {

                tokens.add(new Token("LEFT_PAREN", "("));
        
            } else if (current == ')') {
        
                tokens.add(new Token("RIGHT_PAREN", ")"));
            } else {

                tokens.add(new Token("UNKNOWN",
                        String.valueOf(current)));
            }

            i++;
        }

        return tokens;
    }
}