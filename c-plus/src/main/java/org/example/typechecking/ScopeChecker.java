package org.example.typechecking;

import org.example.AstVisitor;
import org.example.astnodes.*;
import org.example.typechecking.symbols.FunctionDefinitionSymbol;
import org.example.typechecking.symbols.Symbol;
import org.example.typechecking.symbols.VariableSymbol;

import java.util.List;
import java.util.Stack;

public class ScopeChecker extends AstVisitor {
    private SymbolTable currentScope;
    private Stack<SymbolTable> tableStack;

    public ScopeChecker() {
        this.currentScope = new SymbolTable(null);
        this.tableStack = new Stack<>();
    }

    public void checkScope(TranslationUnitNode node) {
        //Add global scope to stack
        tableStack.push(getCurrentScope());
        visitTranslationUnitNode(node);
        System.out.println(getCurrentScope().toString());
    }


    @Override
    public void visitAdditiveExpressionNode(AdditiveExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitAssignmentExpressionNode(AssignmentExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visitCharacterConstantNode(CharacterConstantNode node) {

    }

    @Override
    public void visitCompoundStatementNode(CompoundStatementNode node) {
        //Create and set new scope and add scope to stack
        setCurrentScope(getCurrentScope().enterScope());
        tableStack.push(getCurrentScope());

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

        //Exit scope
        setCurrentScope(getCurrentScope().getParent());
    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        String name = node.getIdentifierNode().getName();
        Type type = Type.valueOf(node.getTypeSpecifierNode().getType().toUpperCase());

        if (getCurrentScope().lookupSymbol(name) == null) {
            VariableSymbol variableSymbol = new VariableSymbol(name, type);
            getCurrentScope().addSymbol(name, variableSymbol);
        }
        else {throw new RuntimeException("Variable already defined");}

        node.getValue().accept(this);
    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
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

        node.getInitialization().accept(this);
        node.getCondition().accept(this);
        node.getUpdate().accept(this);
        node.getBody().accept(this);

    }

    @Override
    public void visitFunctionCallNode(FunctionCallNode node) {

        String name = node.getIdentifierNode().getName();

        if (getCurrentScope().lookupSymbol(name) == null) {
            throw new RuntimeException(name + " is not a defined function.");
        }
        else {
            Symbol symbol = getCurrentScope().lookupSymbol(name);
            if (symbol.getType() != Type.FUNCTION) {
                throw new RuntimeException(name + " is not a defined function.");
            }}

        if (node.getCallValue() != null) {
            node.getCallValue().accept(this);
        }
    }

    @Override
    public void visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        String functionName = node.getIdentifierNode().getName();

        //Throw an error if function name has already been declared
        if (getCurrentScope().lookupSymbol(functionName) != null) {
            throw new RuntimeException("Variable: " + functionName + " already declared");
        }

        //Create parameter symbol if function has parameter
        VariableSymbol paramSymbol = null;
        if (node.getParameter() != null) {
            String paramName = node.getParameter().getName().getName();
            Type parmType = Type.valueOf(node.getParameter().getType().getType().toUpperCase());

            paramSymbol = new VariableSymbol(paramName, parmType);

            if (getCurrentScope().lookupSymbol(paramName) != null) {
                throw new RuntimeException("Variable: " + paramName + " already declared");
            }
        }

        FunctionDefinitionSymbol funcSymbol = new FunctionDefinitionSymbol(functionName, Type.FUNCTION, paramSymbol);
        getCurrentScope().addSymbol(functionName, funcSymbol);

        visitFunctionBody(node.getBody(), paramSymbol);

    }

    public void visitFunctionBody(CompoundStatementNode node, VariableSymbol paramSymbol) {
        setCurrentScope(getCurrentScope().enterScope());
        tableStack.push(getCurrentScope());

        if (paramSymbol != null) {
            getCurrentScope().addSymbol(paramSymbol.getName(), paramSymbol);
        }

        getCurrentScope().addSymbol(paramSymbol.getName(), paramSymbol);

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

        setCurrentScope(getCurrentScope().getParent());
    }


    @Override
    public void visitIdentifierNode(IdentifierNode node) {
        String name = node.getName();

        if (getCurrentScope().lookupSymbol(name) == null) {
            throw new RuntimeException("Variable \"" + name + "\" is undefined.");
        }
    }

    @Override
    public void visitIfElseNode(IfElseNode node) {
        node.getCondition().accept(this);

        node.getIfBranch().accept(this);

        if (node.getElseBranch() != null) {
            node.getElseBranch().accept(this);
        }
    }

    @Override
    public void visitIntegerConstantNode(IntegerConstantNode node) {

    }

    @Override
    public void visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitNegationNode(NegationNode node) {
        node.getInnerExpressionNode().accept(this);
    }

    @Override
    public void visitParameterDeclarationNode(ParameterDeclarationNode node) {
        Type type = Type.valueOf(node.getType().getType().toUpperCase());
        String name = node.getName().getName();
    }

    @Override
    public void visitParensExpressionNode(ParensExpressionNode node) {
        node.getInnerExpressionNode().accept(this);
    }

    @Override
    public void visitPostFixExpressionNode(PostFixExpressionNode node) {
        node.getIdentifierOrConstant().accept(this);
    }

    @Override
    public void visitRelationalExpressionNode(RelationalExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitReturnStatementNode(ReturnStatementNode node) {
        if (node.getReturnValue() != null) {
            node.getReturnValue().accept(this);
        }
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
        node.getCondition().accept(this);

        node.getBody().accept(this);
    }

    @Override
    public void visitExpressionStatementNode(ExpressionStatementNode node) {
        node.getExpressionNode().accept(this);
    }

    @Override
    public void visitExpressionNode(ExpressionNode node) {
        node.accept(this);
    }

    @Override
    public void visitConstantNode(ConstantNode node) {

    }

    public SymbolTable getCurrentScope() {
        return currentScope;
    }

    public void setCurrentScope(SymbolTable currentScope) {
        this.currentScope = currentScope;
    }
}
