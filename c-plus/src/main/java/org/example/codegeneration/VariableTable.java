package org.example.codegeneration;

import java.util.HashMap;
import java.util.Map;

public class VariableTable {
    private final Map<String, String> variables;
    private final VariableTable parent;

    public VariableTable(VariableTable parent) {
        this.variables = new HashMap<>();
        this.parent = parent;
    }

    public void addVariable(String name, String address) {
        variables.put(name, address);
    }

    public int lookupVariable(String name) {
        String address = variables.get(name);
        if (address != null) {
            return Integer.parseInt(address);
        } else if (parent != null) {
            return parent.lookupVariable(name);
        } else {
            return 0;
        }
    }


    public int getVariableCount () {
        if (parent != null) {
            return variables.size() + parent.getVariableCount();
        } else {
            return variables.size();
        }
    }

    public VariableTable enterScope() {
        return new VariableTable(this);
    }

    public VariableTable getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "variables=" + variables +
                ", parent=" + parent +
                '}';
    }
}

