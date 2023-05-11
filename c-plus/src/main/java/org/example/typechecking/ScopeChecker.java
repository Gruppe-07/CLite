package org.example.typechecking;

import org.example.AstVisitor;
import org.example.astnodes.*;
import org.example.typechecking.symbols.FunctionSymbol;
import org.example.typechecking.symbols.ParameterSymbol;
import org.example.typechecking.symbols.Symbol;

public class ScopeChecker extends AstVisitor {
    private SymbolTable currentSymbolTable;
    private Scope currentScope;
    public ScopeChecker(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
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

    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        Type type = Type.valueOf(node.getTypeSpecifierNode().getType().toUpperCase());

        String name = node.getIdentifierNode().getName();

    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {

    }

    @Override
    public void visitExternalDeclarationNode(ExternalDeclarationNode node) {

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

        String parameterType = node.getParameter().getType().getType();

        Type type = Type.valueOf(parameterType.toUpperCase());

        String identifier = node.getParameter().getIdentifierNode().getName();

        ParameterSymbol parameterSymbol = new ParameterSymbol(identifier, type);

        FunctionSymbol functionSymbol = new FunctionSymbol(node.getName().getName(), parameterSymbol);

        this.getSymbolTable()
        Scope functionScope = new Scope(this.getCurrentScope(), this.symbolTable);
        this.setCurrentScope(functionScope);


        this.getCurrentScope().addSymbol(identifier, parameterSymbol);

        this.setCurrentScope(this.getCurrentScope().getParentScope());

        node.getBody().accept(this);
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
        this.setCurrentScope(new Scope(null));

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

    public Scope getCurrentScope() {
        return currentScope;
    }

    public void setCurrentScope(Scope currentScope) {
        this.currentScope = currentScope;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
