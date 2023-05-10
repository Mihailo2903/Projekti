// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class ArrayMethodParameter extends Parameter {

    private Type Type;
    private String s;

    public ArrayMethodParameter (Type Type, String s) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.s=s;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s=s;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayMethodParameter(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+s);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayMethodParameter]");
        return buffer.toString();
    }
}
