// generated with ast extension for cup
// version 0.8
// 17/0/2023 17:47:54


package rs.ac.bg.etf.pp1.ast;

public class Method_Declaration extends MethodDecl {

    private ReturnTypeIdent ReturnTypeIdent;
    private FormPars FormPars;
    private MethodFieldList MethodFieldList;
    private StmtList StmtList;

    public Method_Declaration (ReturnTypeIdent ReturnTypeIdent, FormPars FormPars, MethodFieldList MethodFieldList, StmtList StmtList) {
        this.ReturnTypeIdent=ReturnTypeIdent;
        if(ReturnTypeIdent!=null) ReturnTypeIdent.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.MethodFieldList=MethodFieldList;
        if(MethodFieldList!=null) MethodFieldList.setParent(this);
        this.StmtList=StmtList;
        if(StmtList!=null) StmtList.setParent(this);
    }

    public ReturnTypeIdent getReturnTypeIdent() {
        return ReturnTypeIdent;
    }

    public void setReturnTypeIdent(ReturnTypeIdent ReturnTypeIdent) {
        this.ReturnTypeIdent=ReturnTypeIdent;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public MethodFieldList getMethodFieldList() {
        return MethodFieldList;
    }

    public void setMethodFieldList(MethodFieldList MethodFieldList) {
        this.MethodFieldList=MethodFieldList;
    }

    public StmtList getStmtList() {
        return StmtList;
    }

    public void setStmtList(StmtList StmtList) {
        this.StmtList=StmtList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnTypeIdent!=null) ReturnTypeIdent.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
        if(MethodFieldList!=null) MethodFieldList.accept(visitor);
        if(StmtList!=null) StmtList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnTypeIdent!=null) ReturnTypeIdent.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(MethodFieldList!=null) MethodFieldList.traverseTopDown(visitor);
        if(StmtList!=null) StmtList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnTypeIdent!=null) ReturnTypeIdent.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(MethodFieldList!=null) MethodFieldList.traverseBottomUp(visitor);
        if(StmtList!=null) StmtList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Method_Declaration(\n");

        if(ReturnTypeIdent!=null)
            buffer.append(ReturnTypeIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodFieldList!=null)
            buffer.append(MethodFieldList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StmtList!=null)
            buffer.append(StmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Method_Declaration]");
        return buffer.toString();
    }
}
