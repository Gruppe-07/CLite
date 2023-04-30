package org.example.astnodes;

import com.myparser.parser.CLiteParser;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import com.myparser.parser.*;

public class BuildASTVisitor extends AbstractParseTreeVisitor {
    @Override
    public FunctionDeclarationNode VisitFunctionDeclaration(CLiteParser.FunctionDefinitionContext context) {
        return new FunctionDeclarationNode(visit(context.Identifier()), visit(context.parameterDeclaration()), visit(context.compoundStatement()));
        ;
    }
}
