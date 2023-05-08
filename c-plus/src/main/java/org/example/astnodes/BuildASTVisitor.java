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
        if (ctx.Const() != null) { declarationNode.setConst(true); }
        else {declarationNode.setConst(false);}

        declarationNode.setTypeSpecifierNode(new TypeSpecifierNode(ctx.typeSpecifier().getText()));

        List<IdentifierNode> identifierNodeList = new ArrayList<>();

        for (ParseTree child : ctx.Identifier()) {
            identifierNodeList.add(new IdentifierNode(child.getText()));
            declarationNode.setDeclaratorNodeList(identifierNodeList);
        }

        if (ctx.initializer() != null) {
            declarationNode.setInitializerNode(visitInitializer(ctx.initializer()));
        }
        return declarationNode;
    }

    @Override
    public InitializerNode visitInitializer(CLiteParser.InitializerContext ctx) {
        List<ExpressionNode> expressionNodeList = new ArrayList<>();
        if (ctx.assignmentExpression() != null) {
            for (CLiteParser.AssignmentExpressionContext child : ctx.assignmentExpression()) {
                expressionNodeList.add(visitAssignmentExpression(child));
            }
        }
        return new InitializerNode(expressionNodeList);
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
        if (ctx.expression().size() == 1) {
            return new ReturnStatementNode(visitExpression(ctx.expression(0)));
        }
        if (ctx.expression().size() > 2) {
            List<ExpressionNode> expressionNodeList = new ArrayList<>();
            for (CLiteParser.ExpressionContext child : ctx.expression()) {
                expressionNodeList.add(visitExpression(child));
            }
            TupleNode tupleNode = new TupleNode(expressionNodeList);
            return new ReturnStatementNode(tupleNode);
        }
        return null;
    }

    @Override
    public ExpressionStatementNode visitExpressionStatement(CLiteParser.ExpressionStatementContext ctx) {
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

            for (CLiteParser.UnaryExpressionContext unaryExpressionContext : ctx.unaryExpression()) {
                if (unaryExpressionContext != null) {
                    operands.add(visitUnaryExpression(unaryExpressionContext));
                }
            }

            for (ParseTree child: ctx.children) {
                if (child instanceof TerminalNode) {
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


