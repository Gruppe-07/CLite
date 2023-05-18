package org.example.astnodes;

import org.example.AstVisitor;

import java.util.Objects;

public class CharacterConstantNode extends ConstantNode {

    public String value;

    public CharacterConstantNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitCharacterConstantNode(this);
    }
}
