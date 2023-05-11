package org.example.typechecking.symbols;

import org.example.typechecking.Type;

public class ParameterSymbol extends Symbol{
    private Type type;

    public ParameterSymbol(String name, Type type) {
        super(name);
        this.type = type;
    }
}
