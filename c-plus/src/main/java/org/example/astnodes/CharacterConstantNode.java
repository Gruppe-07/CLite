package org.example.astnodes;

public class CharacterConstantNode extends ConstantNode {

    public String value;
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
