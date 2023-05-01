package org.example.astnodes;

public class FloatConstantNode extends Constant {
    public double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
