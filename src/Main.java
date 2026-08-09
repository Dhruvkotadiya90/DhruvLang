import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            List<String> lines = Files.readAllLines(
                Path.of("src", "hello.D")
            );

            Interpreter interpreter = new Interpreter();

            for (String line : lines) {

                interpreter.run(line);
            }

        } catch (Exception e) {

            System.out.println("Error running DhruvLang.");
            e.printStackTrace();
        }
    }
}