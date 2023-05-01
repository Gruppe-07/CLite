package org.example.astnodes;

public class ArrayIndexNode extends PostFixExpression {
    public IdentifierNode name;
    public IntegerConstantNode index;

    public IdentifierNode getName() {
        return name;
    }

    public void setName(IdentifierNode name) {
        this.name = name;
    }

    public IntegerConstantNode getIndex() {
        return index;
    }

    public void setIndex(IntegerConstantNode index) {
        this.index = index;
    }
}
