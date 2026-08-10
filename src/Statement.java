import java.util.List;

public class Statement {

    String type;
    String name;
    String value;
    String operator;
    String secondValue;

    List<Statement> body;

    public Statement(
        String type,
        String name,
        String value,
        String operator,
        String secondValue,
        List<Statement> body) {

    this.type = type;
    this.name = name;
    this.value = value;
    this.operator = operator;
    this.secondValue = secondValue;
    this.body = body;
}
}