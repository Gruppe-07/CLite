package org.example.typechecking.symbols;

import org.example.typechecking.Type;

public class VariableSymbol extends Symbol{
    Type type;

    public VariableSymbol(String name, Type type) {
        super(name);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
