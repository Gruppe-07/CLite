package org.example.astnodes;

public class IntegerConstantNode extends ConstantNode {

    public int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
