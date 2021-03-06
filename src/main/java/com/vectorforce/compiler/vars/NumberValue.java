package main.java.com.vectorforce.compiler.vars;

public class NumberValue implements Value {
    private final double value;

    public NumberValue(double value) {
        this.value = value;
    }

    public NumberValue(boolean value){
        this.value = value ? 1 : 0;
    }

    @Override
    public double asNumber() {
        return value;
    }

    @Override
    public String asString() {
        return Double.toString(value);
    }
}
