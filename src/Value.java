public class Value {

    String type;
    Object value;

    public Value(String type, Object value) {

        this.type = type;
        this.value = value;

    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

}