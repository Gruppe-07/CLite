package org.example.typechecking;

import org.example.typechecking.symbols.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> symbols;
    private final SymbolTable parent;
    private final List<SymbolTable> children;

    public SymbolTable(SymbolTable parent) {
        this.symbols = new HashMap<>();
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public void addSymbol(String name, Symbol symbol) {
        symbols.put(name, symbol);
    }

    public Symbol lookupSymbol(String name) {
        Symbol value = symbols.get(name);
        if (value != null) {
            return value;
        } else if (parent != null) {
            return parent.lookupSymbol(name);
        } else {
            return null;
        }
    }

    public SymbolTable enterScope() {
        SymbolTable childScope = new SymbolTable(this);
        children.add(childScope);
        return childScope;
    }

    public SymbolTable getParent() {
        return parent;
    }

    public List<SymbolTable> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "symbols=" + symbols +
                ", children=" + children +
                '}';
    }
}
