package org.example.astnodes;

public class ConstantNode extends ExpressionNode {
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double value;

}
