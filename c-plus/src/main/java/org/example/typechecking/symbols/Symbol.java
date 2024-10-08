package org.example.typechecking.symbols;

import org.example.typechecking.Type;

public abstract class Symbol {
    private String name;
    private Type type;

    public Symbol(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

