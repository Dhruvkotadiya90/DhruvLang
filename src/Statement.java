public class Statement {

    String type;
    String name;
    String value;
    String operator;
    String secondValue;

    public Statement(
            String type,
            String name,
            String value,
            String operator,
            String secondValue) {

        this.type = type;
        this.name = name;
        this.value = value;
        this.operator = operator;
        this.secondValue = secondValue;
    }
}