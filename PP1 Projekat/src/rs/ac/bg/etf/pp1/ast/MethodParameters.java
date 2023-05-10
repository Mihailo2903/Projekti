// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class MethodParameters extends ParameterList {

    private ParameterList ParameterList;
    private Parameter Parameter;

    public MethodParameters (ParameterList ParameterList, Parameter Parameter) {
        this.ParameterList=ParameterList;
        if(ParameterList!=null) ParameterList.setParent(this);
        this.Parameter=Parameter;
        if(Parameter!=null) Parameter.setParent(this);
    }

    public ParameterList getParameterList() {
        return ParameterList;
    }

    public void setParameterList(ParameterList ParameterList) {
        this.ParameterList=ParameterList;
    }

    public Parameter getParameter() {
        return Parameter;
    }

    public void setParameter(Parameter Parameter) {
        this.Parameter=Parameter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ParameterList!=null) ParameterList.accept(visitor);
        if(Parameter!=null) Parameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParameterList!=null) ParameterList.traverseTopDown(visitor);
        if(Parameter!=null) Parameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParameterList!=null) ParameterList.traverseBottomUp(visitor);
        if(Parameter!=null) Parameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodParameters(\n");

        if(ParameterList!=null)
            buffer.append(ParameterList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Parameter!=null)
            buffer.append(Parameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodParameters]");
        return buffer.toString();
    }
}
