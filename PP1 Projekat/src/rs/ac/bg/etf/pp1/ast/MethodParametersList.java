// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class MethodParametersList extends FormPars {

    private ParameterList ParameterList;

    public MethodParametersList (ParameterList ParameterList) {
        this.ParameterList=ParameterList;
        if(ParameterList!=null) ParameterList.setParent(this);
    }

    public ParameterList getParameterList() {
        return ParameterList;
    }

    public void setParameterList(ParameterList ParameterList) {
        this.ParameterList=ParameterList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ParameterList!=null) ParameterList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParameterList!=null) ParameterList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParameterList!=null) ParameterList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodParametersList(\n");

        if(ParameterList!=null)
            buffer.append(ParameterList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodParametersList]");
        return buffer.toString();
    }
}
