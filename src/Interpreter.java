import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    private Map<String, Integer> variables = new HashMap<>();

    public void execute(Statement statement) {

        if (statement.type.equals("LET")) {


            int value = evaluate(statement.value);
    
            variables.put(
                    statement.name,
                    value
            );
    
        } else if (statement.type.equals("PRINT")) {
        
            int value = evaluate(statement.value);
    
            System.out.println(value);
    
        } else if (statement.type.equals("IF")) {
    
            int condition = evaluate(statement.value);
    
            if (condition != 0) {
    
                for (Statement bodyStatement : statement.body) {
                    execute(bodyStatement);
                }
    
            } else if (statement.elseBody != null) {
    
                for (Statement bodyStatement : statement.elseBody) {
                    execute(bodyStatement);
                }
            }
    
        } else if (statement.type.equals("WHILE")) {
    
            while (evaluate(statement.value) != 0) {
    
                for (Statement bodyStatement : statement.body) {
                    execute(bodyStatement);
                }
            }
        }
    }

    private int evaluate(String expression) {

        expression = expression.trim();

        return new ExpressionEvaluator(
                expression,
                variables
        ).evaluate();
    }
}