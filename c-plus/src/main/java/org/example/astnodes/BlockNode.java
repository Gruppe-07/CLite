package org.example.astnodes;

import java.util.List;

public class BlockNode extends StatementNode {
    private List<StatementNode> statements;

    public BlockNode(List<StatementNode> statements) {
        this.statements = statements;
    }

    public List<StatementNode> getStatements() {
        return statements;
    }
}