import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    private Map<String, Integer> variables = new HashMap<>();

    public void run(String code) {

        code = code.trim();

        if (code.isEmpty()) {
            return;
        }

        if (code.startsWith("let ")) {

            String statement = code.substring(4).trim();

            String[] parts = statement.split("=");

            String variableName = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());

            variables.put(variableName, value);

            return;
        }

        if (code.startsWith("print ")) {

            String expression = code.substring(6).trim();

            if (expression.contains("+")) {

                String[] numbers = expression.split("\\+");

                int first = getValue(numbers[0]);
                int second = getValue(numbers[1]);

                System.out.println(first + second);

            } else if (expression.contains("-")) {

                String[] numbers = expression.split("-");

                int first = getValue(numbers[0]);
                int second = getValue(numbers[1]);

                System.out.println(first - second);

            } else if (expression.contains("*")) {

                String[] numbers = expression.split("\\*");

                int first = getValue(numbers[0]);
                int second = getValue(numbers[1]);

                System.out.println(first * second);

            } else if (expression.contains("/")) {

                String[] numbers = expression.split("/");

                int first = getValue(numbers[0]);
                int second = getValue(numbers[1]);

                System.out.println(first / second);

            } else {

                if (variables.containsKey(expression)) {

                    System.out.println(variables.get(expression));

                } else {

                    System.out.println(expression);
                }

            }
        }
    }

    private int getValue(String value) {

        value = value.trim();

        if (variables.containsKey(value)) {

            return variables.get(value);
        }

        return Integer.parseInt(value);
    }

}