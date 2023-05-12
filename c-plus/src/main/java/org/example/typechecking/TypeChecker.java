package org.example.typechecking;

import org.example.AstVisitor;
import org.example.astnodes.*;

public class TypeChecker extends AstVisitor {
    private int scopeLevel = 0;
    private SymbolTable symbolTable;
    public TypeChecker(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void checkTypes(TranslationUnitNode node) {
        visitTranslationUnitNode(node);
    }

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
        incrementScope();

        //setSymbolTable(getSymbolTable().getChildren());
        //getSymbolTable()

        decrementScope();

    }

    private void decrementScope() {
        scopeLevel--;
    }

    private void incrementScope() {
        scopeLevel++;
    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        String typeSpecifier = node.getTypeSpecifierNode().getType();
        node.getValue().accept(this);

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
        String funcName = node.getIdentifierNode().getName();

        //symbolTable.


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

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
