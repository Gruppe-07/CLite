package org.example.astnodes;

import java.util.List;

public class TupleDeclarationNode extends DeclarationNode {
    public IdentifierNode name;
    public List<ConstantNode> values;
}
