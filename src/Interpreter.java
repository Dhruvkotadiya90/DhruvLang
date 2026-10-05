import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    private Map<String, Object> variables = new HashMap<>();

    public void execute(Statement statement) {

        if (statement.type.equals("LET")) {


            Object value = evaluate(statement.value);
    
            variables.put(
                    statement.name,
                    value
            );
    
        } 
        
        else if (statement.type.equals("ASSIGN")) {

            if (!variables.containsKey(statement.name)) {
                throw new RuntimeException(
                        "Unknown variable: " + statement.name
                );
            }
        
            Object value = evaluate(statement.value);
        
            variables.put(statement.name, value);
        }

        else if (statement.type.equals("PRINT")) {
        
            Object value = evaluate(statement.value);
    
            System.out.println(value);
    
        } else if (statement.type.equals("IF")) {
    
            int condition = (Integer) evaluate(statement.value);
    
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

            while ( (Integer) evaluate(statement.value) != 0) {
        
                try {
                    for (Statement bodyStatement : statement.body) {
                        execute(bodyStatement);
                    }
                } catch (ContinueException e) {
                    continue;
                } catch (BreakException e) {
                    break;
                }
            }
        } 
        else if (statement.type.equals("BREAK")) {
            throw new BreakException();
        } else if (statement.type.equals("CONTINUE")) {
            throw new ContinueException();
        }
    }

    private Object evaluate(String expression) {

        expression = expression.trim();

        return new ExpressionEvaluator(
                expression,
                variables
        ).evaluate();
    }
}