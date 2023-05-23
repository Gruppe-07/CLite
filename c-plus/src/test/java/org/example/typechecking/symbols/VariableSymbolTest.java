package org.example.typechecking.symbols;

import org.example.typechecking.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableSymbolTest {

    VariableSymbol VSymbol = new VariableSymbol("test", Type.INT);

    @Test
    void testToString() {
        assertEquals("VariableSymbol{} Symbol{name='test', type=INT}" , VSymbol.toString());
    }
}