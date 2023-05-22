package org.example;
import org.example.astnodes.*;


public class PrettyPrinter extends AstVisitor{
    private int indentLevel = 0;

    @Override
    public Object visitAdditiveExpressionNode(AdditiveExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        System.out.println(getIndentString() + node.getOperator());
        visitExpressionNode(node.getRight());

        dedent();
        return null;
    }

    @Override
    public Object visitAssignmentExpressionNode(AssignmentExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        visitExpressionNode(node.getRight());

        dedent();
        return null;
    }

    @Override
    public Object visitCharacterConstantNode(CharacterConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
        return null;
    }

    @Override
    public Object visitCompoundStatementNode(CompoundStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

        dedent();
        return null;
    }

    @Override
    public Object visitExpressionStatementNode(ExpressionStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();
        visitExpressionNode(node.getExpressionNode());
        dedent();
        return null;
    }

    @Override
    public Object visitDeclarationNode(DeclarationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": isConst " + node.getConst());

        indent();

        visitTypeSpecifierNode(node.getTypeSpecifierNode());

        visitIdentifierNode(node.getIdentifierNode());

        visitExpressionNode(node.getValue());

        dedent();

        return null;
    }

    @Override
    public Object visitEqualityExpressionNode(EqualityExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        System.out.println(getIndentString() + node.getOperator());
        visitExpressionNode(node.getRight());

        dedent();
        return null;
    }


    @Override
    public Object visitFloatConstantNode(FloatConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
        return null;
    }


    @Override
    public Object visitFunctionCallNode(FunctionCallNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitIdentifierNode(node.getIdentifierNode());

        if (node.getCallValue() != null) {
            visitExpressionNode(node.getCallValue());
        }

        dedent();
        return null;
    }

    @Override
    public Object visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitTypeSpecifierNode(node.getTypeSpecifierNode());
        visitIdentifierNode(node.getIdentifierNode());
        if (node.getParameter() != null) {visitParameterDeclarationNode(node.getParameter());}
        visitCompoundStatementNode(node.getBody());

        dedent();
        return null;
    }

    @Override
    public Object visitIdentifierNode(IdentifierNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getName());
        return null;
    }

    @Override
    public Object visitIfElseNode(IfElseNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getCondition());
        visitCompoundStatementNode(node.getIfBranch());

        if (node.getElseBranch() != null) {
            visitCompoundStatementNode(node.getElseBranch());
        }

        dedent();
        return null;
    }

    @Override
    public Object visitIntegerConstantNode(IntegerConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
        return null;
    }

    @Override
    public Object visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        System.out.println(getIndentString() + node.getOperator());
        visitExpressionNode(node.getRight());

        dedent();
        return null;
    }

    @Override
    public Object visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        System.out.println(getIndentString() + node.getOperator());
        visitExpressionNode(node.getRight());

        dedent();
        return null;
    }

    @Override
    public Object visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        System.out.println(getIndentString() + node.getOperator());
        visitExpressionNode(node.getRight());

        dedent();
        return null;
    }

    @Override
    public Object visitNegationNode(NegationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getInnerExpressionNode());

        dedent();
        return null;
    }

    @Override
    public Object visitParameterDeclarationNode(ParameterDeclarationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitTypeSpecifierNode(node.getType());
        visitIdentifierNode(node.getIdentifierNode());

        dedent();

        return null;
    }

    @Override
    public Object visitParensExpressionNode(ParensExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getInnerExpressionNode());

        dedent();
        return null;
    }

    @Override
    public Object visitPostFixExpressionNode(PostFixExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getIdentifierOrConstant());
        System.out.println(getIndentString() + "Operator: " + node.getOperator());

        dedent();
        return null;
    }

    @Override
    public Object visitRelationalExpressionNode(RelationalExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getLeft());
        System.out.println(getIndentString() + node.getOperator());
        visitExpressionNode(node.getRight());

        dedent();
        return null;
    }

    @Override
    public Object visitReturnStatementNode(ReturnStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getReturnValue());

        dedent();
        return null;
    }

    @Override
    public Object visitTranslationUnitNode(TranslationUnitNode node) {
        System.out.println(node.getClass().getSimpleName());
        indent();

        for (FunctionDefinitionNode child : node.getFunctionDefinitionNodeList()) {
            visitFunctionDefinitionNode(child);
        }
        dedent();
        return null;
    }


    @Override
    public Object visitTypeSpecifierNode(TypeSpecifierNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getType());
        return null;
    }

    @Override
    public Object visitUnaryExpressionNode(UnaryExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
        return null;
    }

    @Override
    public Object visitForLoopNode(ForLoopNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitDeclarationNode(node.getInitialization());
        visitExpressionNode(node.getCondition());
        visitPostFixExpressionNode(node.getUpdate());
        visitCompoundStatementNode(node.getBody());

        dedent();
        return null;
    }

    @Override
    public Object visitWhileLoopNode(WhileLoopNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getCondition());
        visitCompoundStatementNode(node.getBody());

        dedent();
        return null;
    }

    @Override
    public Object visitExpressionNode(ExpressionNode node) {
        node.accept(this);
        return null;
    }

    @Override
    public Object visitConstantNode(ConstantNode node) {
        node.accept(this);
        return null;
    }

    private void indent() {
        indentLevel++;
    }

    private void dedent() {
        indentLevel--;
    }

    private String getIndentString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(Math.max(0, indentLevel)));
        return sb.toString();
    }
}
