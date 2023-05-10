// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class ElseStatement extends HasElse {

    private ElseWrapper ElseWrapper;
    private Statement Statement;

    public ElseStatement (ElseWrapper ElseWrapper, Statement Statement) {
        this.ElseWrapper=ElseWrapper;
        if(ElseWrapper!=null) ElseWrapper.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ElseWrapper getElseWrapper() {
        return ElseWrapper;
    }

    public void setElseWrapper(ElseWrapper ElseWrapper) {
        this.ElseWrapper=ElseWrapper;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ElseWrapper!=null) ElseWrapper.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ElseWrapper!=null) ElseWrapper.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ElseWrapper!=null) ElseWrapper.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ElseStatement(\n");

        if(ElseWrapper!=null)
            buffer.append(ElseWrapper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ElseStatement]");
        return buffer.toString();
    }
}
