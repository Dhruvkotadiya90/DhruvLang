import java.util.List;

public class Main {

    public static void main(String[] args) {

        String code = "if x > 10";

        Lexer lexer = new Lexer();

        List<Token> tokens = lexer.tokenize(code);

        for (Token token : tokens) {

            System.out.println(token);
        }
    }
}