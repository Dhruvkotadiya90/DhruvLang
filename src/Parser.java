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
    
            Statement statement = parseStatement();
    
            if (statement != null) {
                statements.add(statement);
            }
        }
    
        return statements;
    }

    private List<Statement> parseBlock() {

        position++; // skip {
    
        List<Statement> statements = new ArrayList<>();
    
        while (position < tokens.size()
                && !tokens.get(position).getType().equals("RIGHT_BRACE")) {
    
            Statement statement = parseStatement();
    
            if (statement != null) {
                statements.add(statement);
            }
        }
    
        if (position < tokens.size()
                && tokens.get(position).getType().equals("RIGHT_BRACE")) {
    
            position++; // skip }
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
            throw new RuntimeException("Expected '{' after if condition");
        }
    
        List<Statement> body = parseBlock();
        List<Statement> elseBody = null;
    
        // Check for else
        if (position < tokens.size()
                && tokens.get(position).getType().equals("KEYWORD")
                && tokens.get(position).getValue().equals("else")) {
    
            position++; // skip else
    
            // else if
            if (position < tokens.size()
                    && tokens.get(position).getType().equals("KEYWORD")
                    && tokens.get(position).getValue().equals("if")) {
    
                Statement elseIfStatement = parseIf();
    
                elseBody = new ArrayList<>();
                elseBody.add(elseIfStatement);
    
            } else {
    
                // normal else
                if (position >= tokens.size()
                        || !tokens.get(position).getType().equals("LEFT_BRACE")) {
                    throw new RuntimeException("Expected '{' after else");
                }
    
                elseBody = parseBlock();
            }
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
    
        List<Statement> body = parseBlock();
    
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

    private Statement parseStatement() {

        Token token = tokens.get(position);
    
        if (token.getType().equals("KEYWORD")) {
    
            if (token.getValue().equals("let")) {
                return parseLet();
    
            } else if (token.getValue().equals("print")) {
                return parsePrint();
    
            } else if (token.getValue().equals("if")) {
                return parseIf();
    
            } else if (token.getValue().equals("while")) {
                return parseWhile();
            } else if (token.getValue().equals("break")) {

                position++; // skip break
            
                if (position < tokens.size()
                        && tokens.get(position).getType().equals("SEMICOLON")) {
                    position++; // skip ;
                }
            
                return new Statement(
                        "BREAK",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
            
            } else if (token.getValue().equals("continue")) {
            
                position++; // skip continue
            
                if (position < tokens.size()
                        && tokens.get(position).getType().equals("SEMICOLON")) {
                    position++; // skip ;
                }
            
                return new Statement(
                        "CONTINUE",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
            }
        }

        if (token.getType().equals("IDENTIFIER")) {
            return parseAssignment();
        }
    
        position++;
        return null;
    }

    private Statement parseAssignment() {

        String variableName = tokens.get(position).getValue();
    
        position++; // skip variable
    
        position++; // skip =
    
        String expression = readExpression();
    
        return new Statement(
                "ASSIGN",
                variableName,
                expression,
                null,
                null,
                null,
                null
        );
    }

    private String readExpression() {

        StringBuilder expression = new StringBuilder();
    
        while (position < tokens.size()) {
    
            Token token = tokens.get(position);
    
            if (token.getType().equals("KEYWORD")
                    || token.getType().equals("LEFT_BRACE")
                    || token.getType().equals("RIGHT_BRACE")
                    || token.getType().equals("SEMICOLON")) {
    
                break;
            }
    
            expression.append(token.getValue());
    
            position++;
        }
    
        if (position < tokens.size()
                && tokens.get(position).getType().equals("SEMICOLON")) {
    
            position++;
        }
    
        return expression.toString();
    }
}