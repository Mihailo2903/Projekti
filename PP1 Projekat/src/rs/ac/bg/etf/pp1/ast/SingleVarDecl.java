// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDecl extends VarDeclList {

    private VarDeclPart VarDeclPart;

    public SingleVarDecl (VarDeclPart VarDeclPart) {
        this.VarDeclPart=VarDeclPart;
        if(VarDeclPart!=null) VarDeclPart.setParent(this);
    }

    public VarDeclPart getVarDeclPart() {
        return VarDeclPart;
    }

    public void setVarDeclPart(VarDeclPart VarDeclPart) {
        this.VarDeclPart=VarDeclPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclPart!=null) VarDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclPart!=null) VarDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclPart!=null) VarDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDecl(\n");

        if(VarDeclPart!=null)
            buffer.append(VarDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDecl]");
        return buffer.toString();
    }
}
