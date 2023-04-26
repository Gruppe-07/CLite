package org.example.astnodes;

public class FloatConstant extends ConstantNode {
    public double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
