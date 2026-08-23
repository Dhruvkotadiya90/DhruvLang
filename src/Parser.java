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
                
                } else if (token.getValue().equals("while")) {
                
                    statements.add(parseWhile());
                
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
            null,
            null
        );
    }

    private Statement parseIf() {

        position++; // skip if
    
        String condition = readExpression();
    
        if (position >= tokens.size()
                || !tokens.get(position).getType().equals("LEFT_BRACE")) {
    
            throw new RuntimeException(
                    "Expected '{' after if condition"
            );
        }
    
        position++; // skip {
    
        List<Statement> body = new ArrayList<>();
    
        while (position < tokens.size()
                && !tokens.get(position).getType().equals("RIGHT_BRACE")) {
    
            Token token = tokens.get(position);
    
            if (token.getType().equals("KEYWORD")) {
    
                if (token.getValue().equals("let")) {
    
                    body.add(parseLet());
    
                } else if (token.getValue().equals("print")) {
    
                    body.add(parsePrint());
    
                } else if (token.getValue().equals("if")) {
    
                    body.add(parseIf());
    
                }
                else if (token.getValue().equals("while")) {
    
                    body.add(parseWhile());
    
                }  
                
                else {
    
                    position++;
                }
    
            } else {
    
                position++;
            }
        }
    
        position++; // skip }
    
        // Check for else
        List<Statement> elseBody = null;
    
        if (position < tokens.size()
                && tokens.get(position).getType().equals("KEYWORD")
                && tokens.get(position).getValue().equals("else")) {
    
            position++; // skip else
    
            if (position >= tokens.size()
                    || !tokens.get(position).getType().equals("LEFT_BRACE")) {
    
                throw new RuntimeException(
                        "Expected '{' after else"
                );
            }
    
            position++; // skip {
    
            elseBody = new ArrayList<>();
    
            while (position < tokens.size()
                    && !tokens.get(position).getType().equals("RIGHT_BRACE")) {
    
                Token token = tokens.get(position);
    
                if (token.getType().equals("KEYWORD")) {
    
                    if (token.getValue().equals("let")) {
    
                        elseBody.add(parseLet());
    
                    } else if (token.getValue().equals("print")) {
    
                        elseBody.add(parsePrint());
    
                    } else {
    
                        position++;
                    }
    
                } else {
    
                    position++;
                }
            }
    
            position++; // skip }
        }
    
        return new Statement(
                "IF",
                null,
                condition,
                null,
                null,
                body,
                elseBody
        );
    }

    private Statement parseWhile() {

        position++; // skip while
    
        String condition = readExpression();
    
        if (position >= tokens.size()
                || !tokens.get(position).getType().equals("LEFT_BRACE")) {
    
            throw new RuntimeException(
                    "Expected '{' after while condition"
            );
        }
    
        position++; // skip {
    
        List<Statement> body = new ArrayList<>();
    
        while (position < tokens.size()
                && !tokens.get(position).getType().equals("RIGHT_BRACE")) {
    
            Token token = tokens.get(position);
    
            if (token.getType().equals("KEYWORD")) {
    
                if (token.getValue().equals("let")) {
    
                    body.add(parseLet());
    
                } else if (token.getValue().equals("print")) {
    
                    body.add(parsePrint());
    
                } else if (token.getValue().equals("if")) {
    
                    body.add(parseIf());
    
                } else if (token.getValue().equals("while")) {
    
                    body.add(parseWhile());
    
                } else {
    
                    position++;
                }
    
            } else {
    
                position++;
            }
        }
    
        if (position < tokens.size()
                && tokens.get(position).getType().equals("RIGHT_BRACE")) {
    
            position++;
        }
    
        return new Statement(
                "WHILE",
                null,
                condition,
                null,
                null,
                body,
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