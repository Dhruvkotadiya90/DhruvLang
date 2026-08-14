import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            List<String> lines = Files.readAllLines(
                Path.of("Programms","hello.D")
            );

            StringBuilder code = new StringBuilder();

            for (String line : lines) {
                code.append(line).append("\n");
            }

            Lexer lexer = new Lexer();

            List<Token> tokens =
                lexer.tokenize(code.toString());

            Parser parser = new Parser(tokens);

            List<Statement> statements =
                parser.parse();

            Interpreter interpreter =
                new Interpreter();

            for (Statement statement : statements) {
                interpreter.execute(statement);
            }

        } catch (Exception e) {

            System.out.println("Error running DhruvLang.");
            e.printStackTrace();
        }
    }
}