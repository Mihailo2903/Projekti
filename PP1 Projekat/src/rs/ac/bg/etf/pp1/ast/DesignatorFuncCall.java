// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFuncCall extends DesignatorOperation {

    private FuncParams FuncParams;

    public DesignatorFuncCall (FuncParams FuncParams) {
        this.FuncParams=FuncParams;
        if(FuncParams!=null) FuncParams.setParent(this);
    }

    public FuncParams getFuncParams() {
        return FuncParams;
    }

    public void setFuncParams(FuncParams FuncParams) {
        this.FuncParams=FuncParams;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncParams!=null) FuncParams.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncParams!=null) FuncParams.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncParams!=null) FuncParams.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFuncCall(\n");

        if(FuncParams!=null)
            buffer.append(FuncParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFuncCall]");
        return buffer.toString();
    }
}
