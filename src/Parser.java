import java.util.ArrayList;
import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Statement> parse() {

        List<Statement> statements = new ArrayList<>();

        while (position < tokens.size()) {

            Token token = tokens.get(position);

            if (token.getType().equals("KEYWORD")) {

                if (token.getValue().equals("let")) {

                    statements.add(parseLet());
                
                } else if (token.getValue().equals("print")) {
                
                    statements.add(parsePrint());
                
                } else if (token.getValue().equals("if")) {
                
                    statements.add(parseIf());
                
                } else {
                
                    position++;
                }

            } else {
                position++;
            }
        }

        return statements;
    }

    private Statement parseLet() {

        position++; // let

        Token variable = tokens.get(position);
        position++; // variable

        position++; // =

        String expression = readExpression();

        return new Statement(
            "LET",
            variable.getValue(),
            expression,
            null,
            null,
            null
    );
    }

    private Statement parsePrint() {

        position++; // print

        String expression = readExpression();

        return new Statement(
            "PRINT",
            null,
            expression,
            null,
            null,
            null
    );
    }

    private Statement parseIf() {

        position++; // skip "if"
    
        String condition = readExpression();
    
        return new Statement(
                "IF",
                null,
                condition,
                null,
                null,
                null
        );
    }

    private String readExpression() {

        StringBuilder expression = new StringBuilder();
    
        while (position < tokens.size()) {
    
            Token token = tokens.get(position);
    
            // Stop when another statement begins
            if (token.getType().equals("KEYWORD")
                || token.getType().equals("LEFT_BRACE")
                || token.getType().equals("RIGHT_BRACE")) {
        
            break;
        }
    
            expression.append(token.getValue());
    
            position++;
        }
    
        return expression.toString();
    }
}