package org.example.astnodes;

import com.myparser.parser.CLiteParser;
import com.myparser.parser.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class BuildASTVisitor extends CLiteBaseVisitor<AstNode> {

    @Override
    public AstNode visitCompilationUnit(CLiteParser.CompilationUnitContext ctx) {
        if (ctx.translationUnit() != null)
            return visitTranslationUnit(ctx.translationUnit());
        else
            return null;
    }

    @Override
    public TranslationUnitNode visitTranslationUnit(CLiteParser.TranslationUnitContext ctx) {
        List<ExternalDeclarationNode> externalDeclarationNodeList = new ArrayList<>();
        for (ParseTree child : ctx.children) {
             externalDeclarationNodeList.add(visitExternalDeclaration((CLiteParser.ExternalDeclarationContext) child));
        }
        return new TranslationUnitNode(externalDeclarationNodeList);
    }

    @Override
    public ExternalDeclarationNode visitExternalDeclaration(CLiteParser.ExternalDeclarationContext ctx) {
        if (ctx.declaration() != null)
            return new ExternalDeclarationNode(visitDeclaration(ctx.declaration()));
        else if (ctx.functionDefinition() != null)
            return new ExternalDeclarationNode(visitFunctionDefinition(ctx.functionDefinition()));
       return null;
    }

    @Override
    public FunctionDefinitionNode visitFunctionDefinition(CLiteParser.FunctionDefinitionContext ctx) {
        IdentifierNode functionIdentifierNodeNode = new IdentifierNode(ctx.Identifier().getText());
        ParameterDeclarationNode parameterDeclarationNode = visitParameterDeclaration(ctx.parameterDeclaration());
        CompoundStatementNode compoundStatementNode = visitCompoundStatement(ctx.compoundStatement());

        return new FunctionDefinitionNode(functionIdentifierNodeNode, parameterDeclarationNode, compoundStatementNode);
    }

    @Override
    public ParameterDeclarationNode visitParameterDeclaration(CLiteParser.ParameterDeclarationContext ctx) {
        TypeSpecifierNode typeSpecifierNode = new TypeSpecifierNode(ctx.typeSpecifier().getText());
        IdentifierNode identifierNode = new IdentifierNode(ctx.Identifier().getText());

        return new ParameterDeclarationNode(typeSpecifierNode, identifierNode);
    }

    @Override
    public CompoundStatementNode visitCompoundStatement(CLiteParser.CompoundStatementContext ctx) {
        List<BlockItem> blockItemList = new ArrayList<>();

        for(ParseTree child : ctx.blockItemList().children) {
            if(child instanceof CLiteParser.StatementContext) {
                AstNode astNode = visitStatement((CLiteParser.StatementContext) child);
                blockItemList.add((BlockItem) astNode);
            }

            else if (child instanceof CLiteParser.DeclarationContext) {
                AstNode astNode = visitDeclaration((CLiteParser.DeclarationContext) child);
                blockItemList.add((BlockItem) astNode);
            }
        }
        return new CompoundStatementNode(blockItemList);
    }



    @Override
    public DeclarationNode visitDeclaration(CLiteParser.DeclarationContext ctx) {
        DeclarationNode declarationNode = new DeclarationNode();

        TypeSpecifierNode typeSpecifierNode = new TypeSpecifierNode(ctx.typeSpecifier().getText());
        IdentifierNode identifierNode = new IdentifierNode(ctx.Identifier().getText());

        if (ctx.Const() != null) { declarationNode.setConst(true); }
        declarationNode.setTypeSpecifierNode(new TypeSpecifierNode(ctx.typeSpecifier().getText()));

        if (ctx.initializer() != null) {
            visitInitializer(ctx.initializer());
        }
        return new DeclarationNode();
    }

    @Override
    public Expression visitExpression(CLiteParser.ExpressionContext ctx) {
        return visitAssignmentExpression(ctx.assignmentExpression());
    }

    @Override
    public Expression visitAssignmentExpression(CLiteParser.AssignmentExpressionContext ctx) {
        if (ctx.children.size() < 2) {
            if (ctx.logicalOrExpression() != null) {
                return visitLogicalOrExpression(ctx.logicalOrExpression());
            }
        }
    }

    @Override
    public AstNode visitStatement(CLiteParser.StatementContext ctx) {
        if (ctx.compoundStatement() != null) {
            return visitCompoundStatement(ctx.compoundStatement());
        } else if (ctx.expressionStatement() != null) {
            return visitExpressionStatement(ctx.expressionStatement());
        } else if (ctx.selectionStatement() != null) {
            return visitSelectionStatement(ctx.selectionStatement());
        } else if (ctx.iterationStatement() != null) {
            return visitIterationStatement(ctx.iterationStatement());
        } else if (ctx.jumpStatement() != null) {
            return visitJumpStatement(ctx.jumpStatement());
        } else {
            throw new RuntimeException("Unknown statement type: " + ctx.getText());
        }
    }
    @Override
    public AstNode visitIterationStatement(CLiteParser.IterationStatementContext ctx) {
        if (ctx.For() != null)
            return visitForLoop(ctx);
        else if (ctx.While() != null)
            return visitWhileLoop(ctx);
        else if (ctx.Foreach() != null)
            return visitForEachLoop(ctx);
        return null;
    }

    private ForEachLoopNode visitForEachLoop(CLiteParser.IterationStatementContext ctx) {
        TypeSpecifierNode typeSpecifierNode = new TypeSpecifierNode(ctx.typeSpecifier().getText());
        IdentifierNode elementIdentifierNodeNode = new IdentifierNode(ctx.Identifier(0).getText());
        IdentifierNode arrayIdentifierNodeNode = new IdentifierNode(ctx.Identifier(1).getText());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());

        return new ForEachLoopNode(typeSpecifierNode, elementIdentifierNodeNode, arrayIdentifierNodeNode, body);
    }

    public WhileLoopNode visitWhileLoop(CLiteParser.IterationStatementContext ctx) {
        Expression condition = (Expression) visitExpression(ctx.expression());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());
        return new WhileLoopNode(condition, body);
    }
    

    public ForLoopNode visitForLoop(CLiteParser.IterationStatementContext ctx) {
        ForLoopNode forLoopNode = new ForLoopNode();

        DeclarationNode initialization = visitDeclaration(ctx.forCondition().declaration());
        RelationalExpressionNode condition = visitRelationalExpression(ctx.forCondition().relationalExpression());
        PostFixExpression update = visitPostfixExpression(ctx.forCondition().postfixExpression());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());

        forLoopNode.setInitialization(initialization);
        forLoopNode.setCondition(condition);
        forLoopNode.setUpdate(update);
        forLoopNode.setBody(body);

        return forLoopNode;
    }

}
