package org.example.typechecking.symbols;

import org.example.typechecking.Type;

public class VariableSymbol extends Symbol{
    public VariableSymbol(String name, Type type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "VariableSymbol{} " + super.toString();
    }
}
