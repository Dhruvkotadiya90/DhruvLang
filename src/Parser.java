import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens) {

        this.tokens = tokens;
    }

    public void parse() {

        while (position < tokens.size()) {

            Token token = tokens.get(position);

            if (token.getType().equals("KEYWORD")) {

                if (token.getValue().equals("let")) {

                    parseLet();

                } else if (token.getValue().equals("print")) {

                    parsePrint();

                } else {

                    System.out.println(
                        "Unknown keyword: " + token.getValue()
                    );

                    position++;
                }

            } else {

                position++;
            }
        }
    }

    private void parseLet() {

        position++;

        Token variable = tokens.get(position);
        position++;

        Token equals = tokens.get(position);
        position++;

        Token value = tokens.get(position);
        position++;

        System.out.println(
            "Variable: " + variable.getValue()
        );

        System.out.println(
            "Value: " + value.getValue()
        );
    }

    private void parsePrint() {

        position++;

        Token value = tokens.get(position);
        position++;

        System.out.println(
            "Print: " + value.getValue()
        );
    }
}