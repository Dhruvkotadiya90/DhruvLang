import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // No argument
        if (args.length == 0) {
            System.out.println("DhruvLang 0.1.0");
            System.out.println("Usage: dhruv <file.D>");
            System.out.println("Use 'dhruv --help' for more information.");
            return;
        }
    
        // Version
        if (args[0].equals("--version")) {
            System.out.println("DhruvLang 0.1.0");
            return;
        }
    
        // Help
        if (args[0].equals("--help")) {
            System.out.println("DhruvLang 0.1.0");
            System.out.println();
            System.out.println("Usage:");
            System.out.println("  dhruv <file.D>");
            System.out.println();
            System.out.println("Options:");
            System.out.println("  --version    Show DhruvLang version");
            System.out.println("  --help       Show this help message");
            return;
        }
    
        // Program file
        try {
    
            List<String> lines = Files.readAllLines(
                    Path.of(args[0])
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
    
            System.out.println(
                    "DhruvLang Error: " + e.getMessage()
            );
        }
    }
}