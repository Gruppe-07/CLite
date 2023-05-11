package org.example.astnodes;

import org.example.AstVisitor;

public class FloatConstantNode extends ConstantNode {
    public double value;

    public FloatConstantNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitFloatConstantNode(this);
    }
}
