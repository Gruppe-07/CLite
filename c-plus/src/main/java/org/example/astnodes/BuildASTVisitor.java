package org.example.astnodes;

import com.myparser.parser.CLiteParser;
import com.myparser.parser.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class BuildASTVisitor extends CLiteBaseVisitor<AstNode> {

    @Override
    public AstNode visitFunctionDefinition(CLiteParser.FunctionDefinitionContext context) {
        IdentifierNode functionIdentifierNode = new IdentifierNode(context.Identifier().getText());

        TypeSpecifierNode parameterTypeSpecifier = new TypeSpecifierNode(context.parameterDeclaration().typeSpecifier().getText());
        IdentifierNode parameterIdentifierNode = new IdentifierNode(context.parameterDeclaration().Identifier().getText());
        ParameterNode parameterNode = new ParameterNode(parameterTypeSpecifier, parameterIdentifierNode);
        int length = context.compoundStatement().blockItemList().getChildCount();

        List<BlockItemNode> blockItemNodeList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            context.compoundStatement().blockItemList().blockItem(i);

        }

        CompoundStatementNode compoundStatementNode = new CompoundStatementNode();


        return new FunctionDefinitionNode(functionIdentifierNode, parameterNode, compoundStatementNode);

    }

    @Override
    public AstNode visitCompoundStatement(CLiteParser.CompoundStatementContext ctx) {
        List<BlockItemNode> blockItemNodeList = new ArrayList<>();

        for(ParseTree child : ctx.children) {
            if(child instanceof CLiteParser.StatementContext) {
                AstNode astNode = visitStatement((CLiteParser.StatementContext) child);
            }

            else {

            }
        }


        return new CompoundStatementNode();
    }

    @Override
    public AstNode visitIterationStatement(CLiteParser.IterationStatementContext ctx) {
        if(ctx.children.get(0) == ctx.For()) {


            ctx.()
            return forLoopNode;
        }
    }

    @Override
    public AstNode visitForLoop(CLiteParser.ForLoopContext ctx) {
        ForLoopNode forLoopNode = new ForLoopNode();

        AstNode initialization = visitExpression(ctx.forCondition().forDeclaration());
        AstNode condition = visitRelationalExpression(ctx.forCondition().relationalExpression());


    }

    @Override
    public AstNode visitDeclaration(CLiteParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public AstNode visitStatement(CLiteParser.StatementContext ctx) {
        if(ctx.children.get(0) instanceof CLiteParser.CompoundStatementContext) {
            AstNode astNode = visitCompoundStatement((CLiteParser.CompoundStatementContext) ctx.children.get(0));
        }
        if(ctx.children.get(0) instanceof CLiteParser.IterationStatementContext) {

        }




    }
}
