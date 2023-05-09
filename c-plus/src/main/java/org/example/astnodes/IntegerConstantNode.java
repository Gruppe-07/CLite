package org.example.astnodes;

import org.example.AstVisitor;

public class IntegerConstantNode extends ConstantNode {

    public int value;

    public IntegerConstantNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitIntegerConstantNode(this);
    }
}

