package org.example.astnodes;

public class IntegerConstant extends ConstantNode {

    public int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
