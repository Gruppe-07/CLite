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
        List<BlockItemNode> blockItemList = new ArrayList<>();

        for(ParseTree child : ctx.blockItemList().children) {
            if(child instanceof CLiteParser.StatementContext) {
                BlockItemNode blockItemNode = visitStatement((CLiteParser.StatementContext) child);
                blockItemList.add(blockItemNode);
            }

            else if (child instanceof CLiteParser.DeclarationContext) {
                BlockItemNode blockItemNode = visitDeclaration((CLiteParser.DeclarationContext) child);
                blockItemList.add(blockItemNode);
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
    public ExpressionNode visitExpression(CLiteParser.ExpressionContext ctx) {
        return visitAssignmentExpression(ctx.assignmentExpression());

    }

    @Override
    public ExpressionNode visitAssignmentExpression(CLiteParser.AssignmentExpressionContext ctx) {
        if (ctx.children.size() < 2) {
            if (ctx.logicalOrExpression() != null) {
                return visitLogicalOrExpression(ctx.logicalOrExpression());
            }
        }
        ExpressionNode left = visitUnaryExpression(ctx.unaryExpression());
        ExpressionNode right = visitAssignmentExpression(ctx.assignmentExpression());

        return new AssignmentNode(left, right);
    }

    @Override
    public StatementNode visitStatement(CLiteParser.StatementContext ctx) {
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
    public ReturnStatementNode visitJumpStatement(CLiteParser.JumpStatementContext ctx) {
        if (ctx.expression() != null) {
            return new ReturnStatementNode(visitExpression(ctx.expression()));
        }
        return new ReturnStatementNode();
    }

    @Override
    public StatementNode visitExpressionStatement(CLiteParser.ExpressionStatementContext ctx) {
        return new ExpressionStatementNode(visitExpression(ctx.expression()));
    }

    @Override
    public StatementNode visitIterationStatement(CLiteParser.IterationStatementContext ctx) {
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
        ExpressionNode condition = visitExpression(ctx.expression());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());
        return new WhileLoopNode(condition, body);
    }


    public ForLoopNode visitForLoop(CLiteParser.IterationStatementContext ctx) {
        ForLoopNode forLoopNode = new ForLoopNode();

        DeclarationNode initialization = visitDeclaration(ctx.forCondition().declaration());
        ExpressionNode condition = visitRelationalExpression(ctx.forCondition().relationalExpression());
        ExpressionNode update = visitPostfixExpression(ctx.forCondition().postfixExpression());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());

        forLoopNode.setInitialization(initialization);
        forLoopNode.setCondition(condition);
        forLoopNode.setUpdate(update);
        forLoopNode.setBody(body);

        return forLoopNode;
    }



    //Expressions

    @Override public ExpressionNode visitMultiplicativeExpression(CLiteParser.MultiplicativeExpressionContext ctx) {
        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for(ParseTree child: ctx.children){
                if (child instanceof CLiteParser.UnaryExpressionContext){
                    operands.add(visitUnaryExpression((CLiteParser.UnaryExpressionContext) child));
                }
                else {
                    operators.add(child.getText());
                }
            }
            return new MultiplicativeExpressionNode(operands, operators);
        }
        return visitUnaryExpression(ctx.unaryExpression(0));
    }

    @Override public ExpressionNode visitAdditiveExpression(CLiteParser.AdditiveExpressionContext ctx) {

        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for(ParseTree child: ctx.children){
                if (child instanceof CLiteParser.MultiplicativeExpressionContext){
                    operands.add(visitMultiplicativeExpression((CLiteParser.MultiplicativeExpressionContext) child));

                }
                else {
                    operators.add(child.getText());
                }
            }
            return new AdditiveExpressionNode(operands, operators);
        }
        return visitMultiplicativeExpression(ctx.multiplicativeExpression(0));
    }

    @Override public ExpressionNode visitRelationalExpression(CLiteParser.RelationalExpressionContext ctx) {
        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for(ParseTree child: ctx.children){
                if (child instanceof CLiteParser.AdditiveExpressionContext){
                    operands.add(visitAdditiveExpression((CLiteParser.AdditiveExpressionContext) child));

                }
                else {
                    operators.add(child.getText());
                }
            }
            return new RelationalExpressionNode(operands, operators);
        }
        return visitAdditiveExpression(ctx.additiveExpression(0));
    }

    @Override public ExpressionNode visitEqualityExpression(CLiteParser.EqualityExpressionContext ctx) {
        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for(ParseTree child: ctx.children){
                if (child instanceof CLiteParser.RelationalExpressionContext){
                    operands.add(visitRelationalExpression((CLiteParser.RelationalExpressionContext) child));

                }
                else {
                    operators.add(child.getText());
                }
            }
            return new EqualityExpressionNode(operands, operators);
        }
        return visitRelationalExpression(ctx.relationalExpression(0));
    }

    @Override
    public ExpressionNode visitLogicalAndExpression(CLiteParser.LogicalAndExpressionContext ctx) {

        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for(ParseTree child: ctx.children){
                if (child instanceof CLiteParser.EqualityExpressionContext){
                    operands.add(visitEqualityExpression((CLiteParser.EqualityExpressionContext) child));

                }
                else {
                    operators.add(child.getText());
                }
            }
            return new LogicalAndExpressionNode(operands, operators);
        }
        return visitEqualityExpression(ctx.equalityExpression(0));
    }

    @Override
    public ExpressionNode visitLogicalOrExpression(CLiteParser.LogicalOrExpressionContext ctx) {

        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for(ParseTree child: ctx.children){
                if (child instanceof CLiteParser.LogicalAndExpressionContext){
                    operands.add(visitLogicalAndExpression((CLiteParser.LogicalAndExpressionContext) child));

                }
                else {
                    operators.add(child.getText());
                }
            }
            return new LogicalOrExpressionNode(operands, operators);
        }
        return visitLogicalAndExpression(ctx.logicalAndExpression(0));
    }


    //Unary Expressions
    @Override
    public ExpressionNode visitUnaryExpression(CLiteParser.UnaryExpressionContext ctx) {
        if (ctx.postfixExpression() != null) {
            return visitPostfixExpression(ctx.postfixExpression());
        }
        if (ctx.unaryOperator() != null && ctx.multiplicativeExpression() != null){
            return new NegationNode(visitMultiplicativeExpression(ctx.multiplicativeExpression()));
        }
        else {
            throw new RuntimeException("Unknown statement type: " + ctx.getText());
        }
    }

    @Override
    public ExpressionNode visitPostfixExpression(CLiteParser.PostfixExpressionContext ctx) {
        if (ctx.parensExpression() != null) {
            return visitParensExpression(ctx.parensExpression());
        }
        if (ctx.arrayIndex() != null) {
            return visitArrayIndex(ctx.arrayIndex());
        }
        if (ctx.functionCall() != null) {
            return visitFunctionCall(ctx.functionCall());
        }
        if (ctx.incrementDecrement() != null) {
            return visitIncrementDecrement(ctx.incrementDecrement());
        }
        else {
            throw new RuntimeException("Unknown statement type: " + ctx.getText());
        }
    }

    @Override
    public FunctionCallNode visitFunctionCall(CLiteParser.FunctionCallContext ctx) {
        IdentifierNode identifierNode = new IdentifierNode(ctx.Identifier().getText());
        if (ctx.assignmentExpression() != null) {
            ExpressionNode callValue = visitAssignmentExpression(ctx.assignmentExpression());
            return new FunctionCallNode(identifierNode, callValue);

        }
        return new FunctionCallNode(identifierNode);
    }

    @Override
    public PostFixExpressionNode visitIncrementDecrement(CLiteParser.IncrementDecrementContext ctx) {
        if (ctx.Constant() != null) {
            String str = ctx.Constant().getText();

            try {
                double d = Double.parseDouble(str);
                return new PostFixExpressionNode(new FloatConstantNode(d), ctx.children.get(1).getText());
            } catch (NumberFormatException e1) {
                try {
                    int i = Integer.parseInt(str);
                    return new PostFixExpressionNode(new IntegerConstantNode(i), ctx.children.get(1).getText());
                } catch (NumberFormatException e2) {
                    return new PostFixExpressionNode(new CharacterConstantNode(str), ctx.children.get(1).getText());
                }
            }
        }
        return null;
    }

    @Override
    public ParensExpressionNode visitParensExpression(CLiteParser.ParensExpressionContext ctx) {
        if (ctx.expression() != null) {
            return new ParensExpressionNode(visitExpression(ctx.expression()));
        }
        else {
            throw new RuntimeException("Unknown statement type: " + ctx.getText());
        }
    }

    @Override
    public ArrayIndexNode visitArrayIndex(CLiteParser.ArrayIndexContext ctx) {
        IdentifierNode identifierNode = new IdentifierNode(ctx.Identifier().getText());
        ExpressionNode expressionNode = visitExpression(ctx.expression());

        return new ArrayIndexNode(identifierNode, expressionNode);
    }


    //Statements
    @Override public IfElseNode visitSelectionStatement(CLiteParser.SelectionStatementContext ctx) {

        ExpressionNode expressionNode = visitExpression(ctx.expression());

        CompoundStatementNode ifBranch = visitCompoundStatement(ctx.compoundStatement(0));

        if (ctx.compoundStatement(1) != null) {
            CompoundStatementNode elseBranch = visitCompoundStatement(ctx.compoundStatement(0));
            return new IfElseNode(expressionNode, ifBranch, elseBranch);
        }

        return new IfElseNode(expressionNode, ifBranch);
    }


}


