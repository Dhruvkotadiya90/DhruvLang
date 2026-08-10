import java.util.Map;

public class ExpressionEvaluator {

    private String expression;
    private int position = 0;

    private Map<String, Integer> variables;

    public ExpressionEvaluator(
            String expression,
            Map<String, Integer> variables) {

        this.expression = expression;
        this.variables = variables;
    }

    public int evaluate() {

        int result = parseComparison();
    
        skipSpaces();
    
        if (position < expression.length()) {
    
            throw new RuntimeException(
                    "Unexpected character: "
                    + expression.charAt(position)
            );
        }
    
        return result;
    }

    private int parseComparison() {

        int left = parseExpression();
    
        skipSpaces();
    
        if (matchString(">=")) {
            int right = parseExpression();
            return left >= right ? 1 : 0;
    
        } else if (matchString("<=")) {
            int right = parseExpression();
            return left <= right ? 1 : 0;
    
        } else if (matchString("==")) {
            int right = parseExpression();
            return left == right ? 1 : 0;
    
        } else if (matchString("!=")) {
            int right = parseExpression();
            return left != right ? 1 : 0;
    
        } else if (match('>')) {
            int right = parseExpression();
            return left > right ? 1 : 0;
    
        } else if (match('<')) {
            int right = parseExpression();
            return left < right ? 1 : 0;
        }
    
        return left;
    }

    // Handles + and -
    private int parseExpression() {

        int result = parseTerm();

        while (true) {

            skipSpaces();

            if (match('+')) {

                result += parseTerm();

            } else if (match('-')) {

                result -= parseTerm();

            } else {

                break;
            }
        }

        return result;
    }

    // Handles * and /
    private int parseTerm() {

        int result = parseFactor();

        while (true) {

            skipSpaces();

            if (match('*')) {

                result *= parseFactor();

            } else if (match('/')) {

                result /= parseFactor();

            } else {

                break;
            }
        }

        return result;
    }

    // Handles numbers, variables and parentheses
    private int parseFactor() {

        skipSpaces();

        // Parentheses
        if (match('(')) {

            int result = parseExpression();

            skipSpaces();

            if (!match(')')) {

                throw new RuntimeException(
                        "Missing closing parenthesis"
                );
            }

            return result;
        }

        // Number
        if (position < expression.length()
                && Character.isDigit(
                        expression.charAt(position))) {

            return parseNumber();
        }

        // Variable
        if (position < expression.length()
                && Character.isLetter(
                        expression.charAt(position))) {

            String name = parseVariable();

            if (!variables.containsKey(name)) {

                throw new RuntimeException(
                        "Unknown variable: " + name
                );
            }

            return variables.get(name);
        }

        throw new RuntimeException(
                "Unexpected character: "
                + expression.charAt(position)
        );
    }

    private int parseNumber() {

        int start = position;

        while (position < expression.length()
                && Character.isDigit(
                        expression.charAt(position))) {

            position++;
        }

        return Integer.parseInt(
                expression.substring(start, position)
        );
    }

    private String parseVariable() {

        int start = position;

        while (position < expression.length()
                && Character.isLetterOrDigit(
                        expression.charAt(position))) {

            position++;
        }

        return expression.substring(start, position);
    }

    private boolean match(char expected) {

        if (position < expression.length()
                && expression.charAt(position) == expected) {

            position++;

            return true;
        }

        return false;
    }

    private boolean matchString(String expected) {

        if (expression.startsWith(expected, position)) {
    
            position += expected.length();
    
            return true;
        }
    
        return false;
    }

    private void skipSpaces() {

        while (position < expression.length()
                && Character.isWhitespace(
                        expression.charAt(position))) {

            position++;
        }
    }
}