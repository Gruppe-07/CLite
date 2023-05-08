package org.example;

import org.example.astnodes.*;

public abstract class AstVisitor {
    public abstract void visitAdditiveExpressionNode(AdditiveExpressionNode node);
    public abstract void visitArrayDeclarationNode(ArrayDeclarationNode node);
    public abstract void visitArrayIndexNode(ArrayIndexNode node);
    public abstract void visitInitializerNode(InitializerNode node);
    public abstract void visitAssignmentNode(AssignmentNode node);
    public abstract void visitCharacterConstantNode(CharacterConstantNode node);
    public abstract void visitCompoundStatementNode(CompoundStatementNode node);
    public abstract void visitDeclarationNode(DeclarationNode node);
    public abstract void visitEqualityExpressionNode(EqualityExpressionNode node);
    public abstract void visitExternalDeclarationNode(ExternalDeclarationNode node);
    public abstract void visitFloatConstantNode(FloatConstantNode node);
    public abstract void visitForEachLoopNode(ForEachLoopNode node);
    public abstract void visitForLoopNode(ForLoopNode node);
    public abstract void visitFunctionCallNode(FunctionCallNode node);
    public abstract void visitFunctionDefinitionNode(FunctionDefinitionNode node);
    public abstract void visitIdentifierNode(IdentifierNode node);
    public abstract void visitIfElseNode(IfElseNode node);
    public abstract void visitIntegerConstantNode(IntegerConstantNode node);
    public abstract void visitLogicalAndExpressionNode(LogicalAndExpressionNode node);
    public abstract void visitLogicalOrExpressionNode(LogicalOrExpressionNode node);
    public abstract void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node);
    public abstract void visitNegationNode(NegationNode node);
    public abstract void visitParameterDeclarationNode(ParameterDeclarationNode node);
    public abstract void visitParensExpressionNode(ParensExpressionNode node);
    public abstract void visitPostFixExpressionNode(PostFixExpressionNode node);
    public abstract void visitRelationalExpressionNode(RelationalExpressionNode node);
    public abstract void visitReturnStatementNode(ReturnStatementNode node);
    public abstract void visitTranslationUnitNode(TranslationUnitNode node);
    public abstract void visitTupleDeclarationNode(TupleDeclarationNode node);
    public abstract void visitTypeSpecifierNode(TypeSpecifierNode node);
    public abstract void visitTupleNode(TupleNode node);
    public abstract void visitVariableDeclarationNode(VariableDeclarationNode node);
    public abstract void visitWhileLoopNode(WhileLoopNode node);

    //Abstract Nodes
    //public abstract void visitAstNode(AstNode node);
    //public abstract void visitBlockItemNode(BlockItemNode node);
    //public abstract void visitConstantNode(ConstantNode node);
    //public abstract void visitExpressionNode(ExpressionNode node);
    //public abstract void visitStatementNode(StatementNode node);
    //public abstract void visitUnaryExpressionNode(UnaryExpressionNode node);


}



/*
public abstract class AstVisitor<T> {
    public abstract void visitAdditiveExpressionNode(AdditiveExpressionNode);
    abstract T visit(AdditiveExpressionNode node);
    abstract T visit(ArrayDeclarationNode node);
    abstract T visit(ArrayIndexNode node);
    abstract T visit(AssignmentNode node);
    //abstract T visit(AstNode node);
    //abstract T visit(BlockItemNode node);
    abstract T visit(CharacterConstantNode node);
    abstract T visit(CompoundStatementNode node);
    //abstract T visit(ConstantNode node);
    abstract T visit(DeclarationNode node);
    abstract T visit(EqualityExpressionNode node);
    //abstract T visit(ExpressionNode node);
    abstract T visit(ExternalDeclarationNode node);
    abstract T visit(FloatConstantNode node);
    abstract T visit(ForEachLoopNode node);
    abstract T visit(ForLoopNode node);
    abstract T visit(FunctionCallNode node);
    abstract T visit(FunctionDefinitionNode node);
    abstract T visit(IdentifierNode node);
    abstract T visit(IfElseNode node);
    abstract T visit(IntegerConstantNode node);
    abstract T visit(LogicalAndExpressionNode node);
    abstract T visit(LogicalOrExpressionNode node);
    abstract T visit(MultiplicativeExpressionNode node);
    abstract T visit(NegationNode node);
    abstract T visit(ParameterDeclarationNode node);
    abstract T visit(ParensExpressionNode node);
    abstract T visit(PostFixExpressionNode node);
    abstract T visit(RelationalExpressionNode node);
    abstract T visit(ReturnStatementNode node);
    //abstract T visit(StatementNode node);
    abstract T visit(TranslationUnitNode node);
    abstract T visit(TupleDeclarationNode node);
    abstract T visit(TypeSpecifierNode node);
    //abstract T visit(UnaryExpressionNode node);
    abstract T visit(VariableDeclarationNode node);
    abstract T visit(WhileLoopNode node);

    public T visit(ConstantNode node) {
        return switch (node.getClass().getSimpleName()) {
            case "IntegerConstantNode" -> visit((IntegerConstantNode) node);
            case "FloatConstantNode" -> visit((FloatConstantNode) node);
            case "CharacterConstantNode" -> visit((CharacterConstantNode) node);

            default -> throw new IllegalStateException("Unexpected value: " + node.getClass().getSimpleName());
        };
    }
    public T visit(StatementNode node) {
        return switch (node.getClass().getSimpleName()) {
            case "CompoundStatementNode" -> visit((CompoundStatementNode) node);


            default -> throw new IllegalStateException("Unexpected value: " + node.getClass().getSimpleName());
        };
    }

}*/



