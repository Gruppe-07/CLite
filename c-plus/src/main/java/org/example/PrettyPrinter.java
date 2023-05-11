package org.example;
import org.example.astnodes.*;


public class PrettyPrinter extends AstVisitor{
    private int indentLevel = 0;

    @Override
    public void visitAdditiveExpressionNode(AdditiveExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitAssignmentExpressionNode(AssignmentExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        visitExpressionNode(node.getRight());

        dedent();
    }

    @Override
    public void visitCharacterConstantNode(CharacterConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
    }

    @Override
    public void visitCompoundStatementNode(CompoundStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

        dedent();
    }

    @Override
    public void visitExpressionStatementNode(ExpressionStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();
        visitExpressionNode(node.getExpressionNode());
        dedent();
    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": isConst " + node.getConst());

        indent();

        visitTypeSpecifierNode(node.getTypeSpecifierNode());

        visitIdentifierNode(node.getIdentifierNode());

        visitExpressionNode(node.getValue());

        dedent();

    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitExternalDeclarationNode(ExternalDeclarationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
        indent();

        node.getFuncDefOrDecl().accept(this);

        dedent();;
    }

    @Override
    public void visitFloatConstantNode(FloatConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
    }


    @Override
    public void visitForLoopNode(ForLoopNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitDeclarationNode(node.getInitialization());
        visitExpressionNode(node.getCondition());
        visitExpressionNode(node.getUpdate());
        visitCompoundStatementNode(node.getBody());

        dedent();
    }

    @Override
    public void visitFunctionCallNode(FunctionCallNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitIdentifierNode(node.getName());
        visitExpressionNode(node.getCallValue());

        dedent();
    }

    @Override
    public void visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitIdentifierNode(node.getName());
        visitParameterDeclarationNode(node.getParameter());
        visitCompoundStatementNode(node.getBody());

        dedent();
    }

    @Override
    public void visitIdentifierNode(IdentifierNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getName());
    }

    @Override
    public void visitIfElseNode(IfElseNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getCondition());
        visitCompoundStatementNode(node.getIfBranch());

        if (node.getElseBranch() != null) {
            visitCompoundStatementNode(node.getElseBranch());
        }

        dedent();
    }

    @Override
    public void visitIntegerConstantNode(IntegerConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
    }

    @Override
    public void visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitNegationNode(NegationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getInnerExpressionNode());

        dedent();
    }

    @Override
    public void visitParameterDeclarationNode(ParameterDeclarationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitTypeSpecifierNode(node.getType());
        visitIdentifierNode(node.getName());

        dedent();

    }

    @Override
    public void visitParensExpressionNode(ParensExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getInnerExpressionNode());

        dedent();
    }

    @Override
    public void visitPostFixExpressionNode(PostFixExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getIdentifierOrConstant());
        System.out.println(getIndentString() + "Operator: " + node.getOperator());

        dedent();
    }

    @Override
    public void visitRelationalExpressionNode(RelationalExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitReturnStatementNode(ReturnStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getReturnValue());

        dedent();
    }

    @Override
    public void visitTranslationUnitNode(TranslationUnitNode node) {
        System.out.println(node.getClass().getSimpleName());
        indent();

        for (ExternalDeclarationNode child : node.getExternalDeclarationNodeList()) {
            visitExternalDeclarationNode(child);
        }
        dedent();
    }


    @Override
    public void visitTypeSpecifierNode(TypeSpecifierNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getType());
    }

    @Override
    public void visitUnaryExpressionNode(UnaryExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
    }


    @Override
    public void visitWhileLoopNode(WhileLoopNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getCondition());
        visitCompoundStatementNode(node.getBody());

        dedent();
    }

    @Override
    public void visitExpressionNode(ExpressionNode node) {
        node.accept(this);
    }

    @Override
    public void visitConstantNode(ConstantNode node) {
        node.accept(this);
    }

    private void indent() {
        indentLevel++;
    }

    private void dedent() {
        indentLevel--;
    }

    private String getIndentString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}
