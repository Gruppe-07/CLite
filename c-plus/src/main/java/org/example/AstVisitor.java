package org.example;

import org.example.astnodes.*;

public abstract class AstVisitor {
    public abstract Object visitAdditiveExpressionNode(AdditiveExpressionNode node);
    public abstract Object visitAssignmentExpressionNode(AssignmentExpressionNode node);
    public abstract Object visitCharacterConstantNode(CharacterConstantNode node);
    public abstract Object visitCompoundStatementNode(CompoundStatementNode node);
    public abstract Object visitDeclarationNode(DeclarationNode node);
    public abstract Object visitEqualityExpressionNode(EqualityExpressionNode node);
    public abstract Object visitFloatConstantNode(FloatConstantNode node);
    public abstract Object visitForLoopNode(ForLoopNode node);
    public abstract Object visitFunctionCallNode(FunctionCallNode node);
    public abstract Object visitFunctionDefinitionNode(FunctionDefinitionNode node);
    public abstract Object visitIdentifierNode(IdentifierNode node);
    public abstract Object visitIfElseNode(IfElseNode node);
    public abstract Object visitIntegerConstantNode(IntegerConstantNode node);
    public abstract Object visitLogicalAndExpressionNode(LogicalAndExpressionNode node);
    public abstract Object visitLogicalOrExpressionNode(LogicalOrExpressionNode node);
    public abstract Object visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node);
    public abstract Object visitNegationNode(NegationNode node);
    public abstract Object visitParameterDeclarationNode(ParameterDeclarationNode node);
    public abstract Object visitParensExpressionNode(ParensExpressionNode node);
    public abstract Object visitPostFixExpressionNode(PostFixExpressionNode node);
    public abstract Object visitRelationalExpressionNode(RelationalExpressionNode node);
    public abstract Object visitReturnStatementNode(ReturnStatementNode node);
    public abstract Object visitTranslationUnitNode(TranslationUnitNode node);
    public abstract Object visitTypeSpecifierNode(TypeSpecifierNode node);
    public abstract Object visitUnaryExpressionNode(UnaryExpressionNode node);
    public abstract Object visitWhileLoopNode(WhileLoopNode node);
    public abstract Object visitExpressionStatementNode(ExpressionStatementNode node);
    public abstract Object visitExpressionNode(ExpressionNode node);
    public abstract Object visitConstantNode(ConstantNode node);


    //Abstract Nodes
    //public abstract Object visitAstNode(AstNode node);
    //public abstract Object visitBlockItemNode(BlockItemNode node);
    //public abstract Object visitConstantNode(ConstantNode node);
    //public abstract Object visitStatementNode(StatementNode node);


}




