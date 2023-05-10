// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class TypeClass implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String s;
    private IsExtended IsExtended;

    public TypeClass (String s, IsExtended IsExtended) {
        this.s=s;
        this.IsExtended=IsExtended;
        if(IsExtended!=null) IsExtended.setParent(this);
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s=s;
    }

    public IsExtended getIsExtended() {
        return IsExtended;
    }

    public void setIsExtended(IsExtended IsExtended) {
        this.IsExtended=IsExtended;
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
        if(IsExtended!=null) IsExtended.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IsExtended!=null) IsExtended.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IsExtended!=null) IsExtended.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypeClass(\n");

        buffer.append(" "+tab+s);
        buffer.append("\n");

        if(IsExtended!=null)
            buffer.append(IsExtended.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeClass]");
        return buffer.toString();
    }
}
