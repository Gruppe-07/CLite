package org.example.typechecking;

import org.example.AstVisitor;
import org.example.astnodes.*;

public class TypeChecker extends AstVisitor {
    @Override
    public void visitAdditiveExpressionNode(AdditiveExpressionNode node) {

    }

    @Override
    public void visitAssignmentExpressionNode(AssignmentExpressionNode node) {

    }

    @Override
    public void visitCharacterConstantNode(CharacterConstantNode node) {

    }

    @Override
    public void visitCompoundStatementNode(CompoundStatementNode node) {

    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        String name = node.getIdentifierNode().getName();
        //node.getTypeSpecifierNode()
    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {

    }

    @Override
    public void visitExternalDeclarationNode(ExternalDeclarationNode node) {
        node.getFuncDefOrDecl().accept(this);
    }

    @Override
    public void visitFloatConstantNode(FloatConstantNode node) {

    }

    @Override
    public void visitForLoopNode(ForLoopNode node) {

    }

    @Override
    public void visitFunctionCallNode(FunctionCallNode node) {

    }

    @Override
    public void visitFunctionDefinitionNode(FunctionDefinitionNode node) {

    }

    @Override
    public void visitIdentifierNode(IdentifierNode node) {

    }

    @Override
    public void visitIfElseNode(IfElseNode node) {

    }

    @Override
    public void visitIntegerConstantNode(IntegerConstantNode node) {

    }

    @Override
    public void visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {

    }

    @Override
    public void visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {

    }

    @Override
    public void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {

    }

    @Override
    public void visitNegationNode(NegationNode node) {

    }

    @Override
    public void visitParameterDeclarationNode(ParameterDeclarationNode node) {

    }

    @Override
    public void visitParensExpressionNode(ParensExpressionNode node) {

    }

    @Override
    public void visitPostFixExpressionNode(PostFixExpressionNode node) {

    }

    @Override
    public void visitRelationalExpressionNode(RelationalExpressionNode node) {

    }

    @Override
    public void visitReturnStatementNode(ReturnStatementNode node) {

    }

    @Override
    public void visitTranslationUnitNode(TranslationUnitNode node) {
        for (ExternalDeclarationNode externalDeclarationNode : node.getExternalDeclarationNodeList()) {
            externalDeclarationNode.accept(this);
        }

    }

    @Override
    public void visitTypeSpecifierNode(TypeSpecifierNode node) {

    }

    @Override
    public void visitUnaryExpressionNode(UnaryExpressionNode node) {

    }

    @Override
    public void visitWhileLoopNode(WhileLoopNode node) {

    }

    @Override
    public void visitExpressionStatementNode(ExpressionStatementNode node) {

    }

    @Override
    public void visitExpressionNode(ExpressionNode node) {

    }

    @Override
    public void visitConstantNode(ConstantNode node) {

    }
}
