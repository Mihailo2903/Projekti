// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDecl extends ConstDeclList {

    private ConstDeclPart ConstDeclPart;

    public SingleConstDecl (ConstDeclPart ConstDeclPart) {
        this.ConstDeclPart=ConstDeclPart;
        if(ConstDeclPart!=null) ConstDeclPart.setParent(this);
    }

    public ConstDeclPart getConstDeclPart() {
        return ConstDeclPart;
    }

    public void setConstDeclPart(ConstDeclPart ConstDeclPart) {
        this.ConstDeclPart=ConstDeclPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclPart!=null) ConstDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclPart!=null) ConstDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclPart!=null) ConstDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDecl(\n");

        if(ConstDeclPart!=null)
            buffer.append(ConstDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDecl]");
        return buffer.toString();
    }
}
