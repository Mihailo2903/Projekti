// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class SingleMethodParameter extends ParameterList {

    private Parameter Parameter;

    public SingleMethodParameter (Parameter Parameter) {
        this.Parameter=Parameter;
        if(Parameter!=null) Parameter.setParent(this);
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
        if(Parameter!=null) Parameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Parameter!=null) Parameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Parameter!=null) Parameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleMethodParameter(\n");

        if(Parameter!=null)
            buffer.append(Parameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleMethodParameter]");
        return buffer.toString();
    }
}
