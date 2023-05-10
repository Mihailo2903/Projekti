// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class ReturnVoid extends ReturnTypeIdent {

    private String s;

    public ReturnVoid (String s) {
        this.s=s;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReturnVoid(\n");

        buffer.append(" "+tab+s);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReturnVoid]");
        return buffer.toString();
    }
}
