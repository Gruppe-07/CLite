package org.example.astnodes;

import com.myparser.parser.CLiteParser;
import com.myparser.parser.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class BuildASTVisitor extends CLiteBaseVisitor<AstNode> {
    @Override
    public TranslationUnitNode visitCompilationUnit(CLiteParser.CompilationUnitContext ctx) {
        if (ctx.translationUnit() != null)
            return visitTranslationUnit(ctx.translationUnit());
        else
            return null;
    }

    @Override
    public TranslationUnitNode visitTranslationUnit(CLiteParser.TranslationUnitContext ctx) {
        List<ExternalDeclarationNode> externalDeclarationNodeList = new ArrayList<>();
        for (CLiteParser.ExternalDeclarationContext child : ctx.externalDeclaration()) {
            externalDeclarationNodeList.add(visitExternalDeclaration(child));
        }
        return new TranslationUnitNode(externalDeclarationNodeList);
    }

    @Override
    public ExternalDeclarationNode visitExternalDeclaration(CLiteParser.ExternalDeclarationContext ctx) {
        return new ExternalDeclarationNode(ctx.getChild(0).accept(this));
    }

    @Override
    public FunctionDefinitionNode visitFunctionDefinition(CLiteParser.FunctionDefinitionContext ctx) {
        IdentifierNode functionIdentifierNodeNode = new IdentifierNode(ctx.Identifier().getText());

        if (ctx.parameterDeclaration() == null) {
            CompoundStatementNode compoundStatementNode = visitCompoundStatement(ctx.compoundStatement());
            return new FunctionDefinitionNode(functionIdentifierNodeNode, compoundStatementNode);
        }
        else {
            ParameterDeclarationNode parameterDeclarationNode = visitParameterDeclaration(ctx.parameterDeclaration());
            CompoundStatementNode compoundStatementNode = visitCompoundStatement(ctx.compoundStatement());
            return new FunctionDefinitionNode(functionIdentifierNodeNode, parameterDeclarationNode, compoundStatementNode);
        }
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

        if (ctx.blockItemList() == null) {
            return new CompoundStatementNode(blockItemList);
        }

        for(CLiteParser.BlockItemContext blockItemContext : ctx.blockItemList().blockItem()) {
            if(blockItemContext.statement() != null) {
                BlockItemNode blockItemNode = visitStatement(blockItemContext.statement());
                blockItemList.add(blockItemNode);
            }

            else if(blockItemContext.declaration() != null) {
                BlockItemNode blockItemNode = visitDeclaration(blockItemContext.declaration());
                blockItemList.add(blockItemNode);
            }
        }
        return new CompoundStatementNode(blockItemList);
    }

    @Override
    public DeclarationNode visitDeclaration(CLiteParser.DeclarationContext ctx) {
        DeclarationNode declarationNode = new DeclarationNode();

        declarationNode.setConst(ctx.Const() != null);

        declarationNode.setTypeSpecifierNode(new TypeSpecifierNode(ctx.typeSpecifier().getText()));

        declarationNode.setIdentifierNode(new IdentifierNode(ctx.Identifier().getText()));

        declarationNode.setValue(visitExpression(ctx.expression()));

        return declarationNode;
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

        return new AssignmentExpressionNode(left, right);
    }

    @Override
    public StatementNode visitStatement(CLiteParser.StatementContext ctx) {
        return (StatementNode) ctx.getChild(0).accept(this);
    }

    @Override
    public ReturnStatementNode visitJumpStatement(CLiteParser.JumpStatementContext ctx) {
        if (ctx.expression() != null) {
            return new ReturnStatementNode(visitExpression(ctx.expression()));
        }
        return new ReturnStatementNode();
    }

    @Override
    public ExpressionStatementNode visitExpressionStatement(CLiteParser.ExpressionStatementContext ctx) {
        return new ExpressionStatementNode(visitExpression(ctx.expression()));
    }

    @Override
    public StatementNode visitIterationStatement(CLiteParser.IterationStatementContext ctx) {
        if (ctx.While() != null) {
            return visitWhileLoop(ctx);
        }
        return null;
    }


    public WhileLoopNode visitWhileLoop(CLiteParser.IterationStatementContext ctx) {
        ExpressionNode condition = visitExpression(ctx.expression());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());
        return new WhileLoopNode(condition, body);
    }

    //Expressions

    @Override public ExpressionNode visitMultiplicativeExpression(CLiteParser.MultiplicativeExpressionContext ctx) {
        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for (CLiteParser.UnaryExpressionContext unaryExpressionContext : ctx.unaryExpression()) {
                if (unaryExpressionContext != null) {
                    operands.add(visitUnaryExpression(unaryExpressionContext));
                }
            }
            for (ParseTree child: ctx.children) {
                if (child instanceof TerminalNode) { operators.add(child.getText()); }
            }

            return new MultiplicativeExpressionNode(operands, operators);
        }
        return visitUnaryExpression(ctx.unaryExpression(0));
    }

    @Override public ExpressionNode visitAdditiveExpression(CLiteParser.AdditiveExpressionContext ctx) {
        if (ctx.children.size() > 1) {
            List<ExpressionNode> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            for (CLiteParser.MultiplicativeExpressionContext multiplicativeExpressionContext : ctx.multiplicativeExpression()) {
                operands.add(visitMultiplicativeExpression(multiplicativeExpressionContext));
            }

            for (ParseTree child: ctx.children) {
                if (child instanceof TerminalNode) {
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
        else if (ctx.unaryOperator() != null && ctx.multiplicativeExpression() != null){
            return new NegationNode(visitMultiplicativeExpression(ctx.multiplicativeExpression()));
        }
        else {
            throw new RuntimeException("Unknown statement type: " + ctx.getText());
        }
    }



    @Override
    public ExpressionNode visitPostfixExpression(CLiteParser.PostfixExpressionContext ctx) {
        return (ExpressionNode) ctx.getChild(0).accept(this);
    }

    @Override
    public FunctionCallNode visitFunctionCall(CLiteParser.FunctionCallContext ctx) {
        IdentifierNode identifierNode = new IdentifierNode(ctx.Identifier().getText());
        if (ctx.expression() != null) {
            ExpressionNode callValue = visitExpression(ctx.expression());
            return new FunctionCallNode(identifierNode, callValue);

        }
        return new FunctionCallNode(identifierNode);
    }

    @Override
    public ExpressionNode visitIncrementDecrement(CLiteParser.IncrementDecrementContext ctx) {
        if (ctx.Constant() != null) {
            ConstantNode constantNode;
            String str = ctx.Constant().getText();
            try {
                int i = Integer.parseInt(str);
                constantNode = new IntegerConstantNode(i);
            } catch (NumberFormatException e1) {
                try {
                    double d = Double.parseDouble(str);
                    constantNode = new FloatConstantNode(d);
                } catch (NumberFormatException e2) {
                    constantNode = new CharacterConstantNode(str);
                }
            }
            if (ctx.PlusPlus() != null) {
                return new PostFixExpressionNode(constantNode, "++");
            } else if (ctx.MinusMinus() != null) {
                return new PostFixExpressionNode(constantNode, "--");
            } else {
                return constantNode;
            }
        } else if (ctx.Identifier() != null) {
            IdentifierNode identifierNode = new IdentifierNode(ctx.Identifier().getText());
            if (ctx.PlusPlus() != null) {
                return new PostFixExpressionNode(identifierNode, "++");
            } else if (ctx.MinusMinus() != null) {
                return new PostFixExpressionNode(identifierNode, "--");
            } else {
                return identifierNode;
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


