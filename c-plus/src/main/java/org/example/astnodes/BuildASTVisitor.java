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
        if(ctx.functionDefinition() == null)
            return null;

        List<FunctionDefinitionNode> functionDefinitionNodeList = new ArrayList<>();
        for (CLiteParser.FunctionDefinitionContext child : ctx.functionDefinition()) {
            functionDefinitionNodeList.add(visitFunctionDefinition(child));
        }
        return new TranslationUnitNode(functionDefinitionNodeList);
    }



    @Override
    public FunctionDefinitionNode visitFunctionDefinition(CLiteParser.FunctionDefinitionContext ctx) {
        TypeSpecifierNode functionTypeSpecifierNode = new TypeSpecifierNode(ctx.typeSpecifier().getText());
        IdentifierNode functionIdentifierNode = new IdentifierNode(ctx.Identifier().getText());

        if (ctx.parameterDeclaration() == null) {
            CompoundStatementNode compoundStatementNode = visitCompoundStatement(ctx.compoundStatement());
            return new FunctionDefinitionNode(functionTypeSpecifierNode, functionIdentifierNode, compoundStatementNode);
        }
        else {
            ParameterDeclarationNode parameterDeclarationNode = visitParameterDeclaration(ctx.parameterDeclaration());
            CompoundStatementNode compoundStatementNode = visitCompoundStatement(ctx.compoundStatement());
            return new FunctionDefinitionNode(functionTypeSpecifierNode ,functionIdentifierNode, parameterDeclarationNode, compoundStatementNode);
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
        if (ctx.For() != null)
        {
            return visitForLoop(ctx);
        }
        return null;
    }


    public WhileLoopNode visitWhileLoop(CLiteParser.IterationStatementContext ctx) {
        ExpressionNode condition = visitExpression(ctx.expression());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());
        return new WhileLoopNode(condition, body);
    }

    public ForLoopNode visitForLoop(CLiteParser.IterationStatementContext ctx) {
        DeclarationNode initialization = visitDeclaration(ctx.declaration());
        ExpressionNode condition = visitExpression(ctx.expression());
        PostFixExpressionNode update = (PostFixExpressionNode) visitIncrementDecrement(ctx.incrementDecrement());
        CompoundStatementNode body = visitCompoundStatement(ctx.compoundStatement());

        return new ForLoopNode(initialization, condition, update, body);
    }

    //Expressions

    @Override public ExpressionNode visitMultiplicativeExpression(CLiteParser.MultiplicativeExpressionContext ctx) {
        if (ctx.unaryExpression() != null) {return visitUnaryExpression(ctx.unaryExpression());}

        MultiplicativeExpressionNode node = new MultiplicativeExpressionNode();
        node.setLeft(visitMultiplicativeExpression(ctx.multiplicativeExpression(0)));
        node.setRight(visitMultiplicativeExpression(ctx.multiplicativeExpression(1)));

        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNode) {node.setOperator(child.getText());}
        }

        return node;
    }

    @Override public ExpressionNode visitAdditiveExpression(CLiteParser.AdditiveExpressionContext ctx) {
        if (ctx.multiplicativeExpression() != null) {return visitMultiplicativeExpression(ctx.multiplicativeExpression());}

        AdditiveExpressionNode node = new AdditiveExpressionNode();
        node.setLeft(visitAdditiveExpression(ctx.additiveExpression(0)));
        node.setRight(visitAdditiveExpression(ctx.additiveExpression(1)));

        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNode) {node.setOperator(child.getText());}
        }

        return node;
    }

    @Override public ExpressionNode visitRelationalExpression(CLiteParser.RelationalExpressionContext ctx) {
        if (ctx.additiveExpression() != null) {return visitAdditiveExpression(ctx.additiveExpression());}

        RelationalExpressionNode node = new RelationalExpressionNode();
        node.setLeft(visitRelationalExpression(ctx.relationalExpression(0)));
        node.setRight(visitRelationalExpression(ctx.relationalExpression(1)));

        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNode) {node.setOperator(child.getText());}
        }

        return node;
    }

    @Override public ExpressionNode visitEqualityExpression(CLiteParser.EqualityExpressionContext ctx) {
        if (ctx.relationalExpression() != null) {return visitRelationalExpression(ctx.relationalExpression());}

        EqualityExpressionNode node = new EqualityExpressionNode();
        node.setLeft(visitEqualityExpression(ctx.equalityExpression(0)));
        node.setRight(visitEqualityExpression(ctx.equalityExpression(1)));

        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNode) {node.setOperator(child.getText());}
        }

        return node;
    }

    @Override
    public ExpressionNode visitLogicalAndExpression(CLiteParser.LogicalAndExpressionContext ctx) {
        if (ctx.equalityExpression() != null) {return visitEqualityExpression(ctx.equalityExpression());}

        LogicalAndExpressionNode node = new LogicalAndExpressionNode();
        node.setLeft(visitLogicalAndExpression(ctx.logicalAndExpression(0)));
        node.setRight(visitLogicalAndExpression(ctx.logicalAndExpression(1)));

        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNode) {node.setOperator(child.getText());}
        }

        return node;
    }

    @Override
    public ExpressionNode visitLogicalOrExpression(CLiteParser.LogicalOrExpressionContext ctx) {
        if (ctx.logicalAndExpression() != null) {return visitLogicalAndExpression(ctx.logicalAndExpression());}

        LogicalOrExpressionNode node = new LogicalOrExpressionNode();
        node.setLeft(visitLogicalOrExpression(ctx.logicalOrExpression(0)));
        node.setRight(visitLogicalOrExpression(ctx.logicalOrExpression(1)));

        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNode) {node.setOperator(child.getText());}
        }

        return node;
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
            CompoundStatementNode elseBranch = visitCompoundStatement(ctx.compoundStatement(1));
            return new IfElseNode(expressionNode, ifBranch, elseBranch);
        }

        return new IfElseNode(expressionNode, ifBranch);
    }
}


