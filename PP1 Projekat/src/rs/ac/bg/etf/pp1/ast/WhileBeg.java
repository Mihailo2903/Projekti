// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class WhileBeg implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private WhileWrapper WhileWrapper;
    private Condition Condition;

    public WhileBeg (WhileWrapper WhileWrapper, Condition Condition) {
        this.WhileWrapper=WhileWrapper;
        if(WhileWrapper!=null) WhileWrapper.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public WhileWrapper getWhileWrapper() {
        return WhileWrapper;
    }

    public void setWhileWrapper(WhileWrapper WhileWrapper) {
        this.WhileWrapper=WhileWrapper;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WhileWrapper!=null) WhileWrapper.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileWrapper!=null) WhileWrapper.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileWrapper!=null) WhileWrapper.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WhileBeg(\n");

        if(WhileWrapper!=null)
            buffer.append(WhileWrapper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WhileBeg]");
        return buffer.toString();
    }
}
