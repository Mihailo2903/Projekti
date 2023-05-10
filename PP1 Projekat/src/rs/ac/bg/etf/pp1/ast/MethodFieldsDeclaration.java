// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class MethodFieldsDeclaration extends MethodFieldList {

    private MethodFieldList MethodFieldList;
    private VarDecl VarDecl;

    public MethodFieldsDeclaration (MethodFieldList MethodFieldList, VarDecl VarDecl) {
        this.MethodFieldList=MethodFieldList;
        if(MethodFieldList!=null) MethodFieldList.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public MethodFieldList getMethodFieldList() {
        return MethodFieldList;
    }

    public void setMethodFieldList(MethodFieldList MethodFieldList) {
        this.MethodFieldList=MethodFieldList;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodFieldList!=null) MethodFieldList.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodFieldList!=null) MethodFieldList.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodFieldList!=null) MethodFieldList.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodFieldsDeclaration(\n");

        if(MethodFieldList!=null)
            buffer.append(MethodFieldList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodFieldsDeclaration]");
        return buffer.toString();
    }
}
