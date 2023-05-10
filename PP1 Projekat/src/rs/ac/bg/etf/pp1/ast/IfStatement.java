// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class IfStatement extends Statement {

    private ConditionIf ConditionIf;
    private Statement Statement;
    private HasElse HasElse;

    public IfStatement (ConditionIf ConditionIf, Statement Statement, HasElse HasElse) {
        this.ConditionIf=ConditionIf;
        if(ConditionIf!=null) ConditionIf.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.HasElse=HasElse;
        if(HasElse!=null) HasElse.setParent(this);
    }

    public ConditionIf getConditionIf() {
        return ConditionIf;
    }

    public void setConditionIf(ConditionIf ConditionIf) {
        this.ConditionIf=ConditionIf;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public HasElse getHasElse() {
        return HasElse;
    }

    public void setHasElse(HasElse HasElse) {
        this.HasElse=HasElse;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionIf!=null) ConditionIf.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(HasElse!=null) HasElse.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionIf!=null) ConditionIf.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(HasElse!=null) HasElse.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionIf!=null) ConditionIf.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(HasElse!=null) HasElse.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStatement(\n");

        if(ConditionIf!=null)
            buffer.append(ConditionIf.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(HasElse!=null)
            buffer.append(HasElse.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStatement]");
        return buffer.toString();
    }
}
