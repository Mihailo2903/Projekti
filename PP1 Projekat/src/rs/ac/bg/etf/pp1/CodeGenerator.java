package rs.ac.bg.etf.pp1;


import java.io.Serializable;

import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	@SuppressWarnings("serial")
	class DesigArray implements Serializable{
		int type;
		Obj obj;
	}
	
	private int mainPc;
	Logger log = Logger.getLogger(getClass());
	public int getMainPc() {
		return mainPc;
	}
	
	Obj ProgramNode;
	Stack<DesigArray> desigArrayList = new Stack<>(); 
	
	String currentMethodName = "";
	
	public CodeGenerator() {
		Obj obj = Tab.find("len");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		obj = Tab.find("ord");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		obj = Tab.find("chr");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(ProgName pname) {
		ProgramNode = Tab.find(pname.getProgName());	
	}
	
	
	public void visit(PrintNoParamStatement print) {
		if(print.getExpr().struct==Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
		else {
			Code.loadConst(5);
			Code.put(Code.print);
		}
	}
	
	public void visit(PrintStatement print) {
		Code.loadConst(print.getS());
		if(print.getExpr().struct==Tab.charType)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}
	
	public void visit(ReadStatement read) {
		if(read.getDesignator().obj.getType().getKind() == Struct.Char)
			Code.put(Code.bread);
		else
			Code.put(Code.read);
		
		if(!(read.getDesignator() instanceof DesignatorArray))
			Code.store(read.getDesignator().obj);
		else {
			if(read.getDesignator().obj.getType().getKind() == Struct.Char)
				Code.put(Code.bastore);
			else
				Code.put(Code.astore);
		}			
	}
	
	public void visit(ReturnType retType) {
		String metName = retType.getS();
		Obj obj= null;
		for(Obj o: ProgramNode.getLocalSymbols()){
			if(o.getName().equals(metName)) {
				obj = o;
				break;
			}
		}		
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		currentMethodName = retType.getS();
	}
	
	public void visit(ReturnVoid retType) {
		String metName = retType.getS();
		Obj obj= null;
		for(Obj o: ProgramNode.getLocalSymbols()){
			if(o.getName().equals(metName)) {
				obj = o;
				break;
			}
		}	
		
		if("main".equals(metName)) {
			mainPc = Code.pc;			
		}
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		currentMethodName = retType.getS();
	}
	
	public void visit(Method_Declaration met) {
		if(met.getReturnTypeIdent() instanceof ReturnType) {
			Code.put(Code.trap);
			Code.put(1);
		} 
		else
		{
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
		currentMethodName = "";
	}
	
	public void visit(FactorsList factList) {
		if(factList.getMulop() instanceof MulOperator)
			Code.put(Code.mul);
		else if (factList.getMulop() instanceof DivisionOperator)
			Code.put(Code.div);
		else
			Code.put(Code.rem);
	}
	
	public void visit(TermsList termList) {
		if(termList.getAddop() instanceof PlusOperator)
			Code.put(Code.add);
		else
			Code.put(Code.sub);
	}
	
	public void visit(FactorNumConstant num) {
		Code.loadConst(num.getS());
	}
	
	public void visit(FactorCharConstant chr) {
		Code.loadConst(chr.getS());
	}
	
	public void visit(FactorBoolConstant boo) {
		if(boo.getS().equals("true"))
			Code.loadConst(1);
		else
			Code.loadConst(0);
	}
	
	public void visit(DesignatorIdent des) {
		if(des.getParent() instanceof DesignatorArray) { //pristup elementu niza
			Code.load(des.obj);
			//log.info("uh");
		}
	}
	
	public void visit(FactorSingleDesignator des) {
		if(!(des.getDesignator().obj.getKind() == Obj.Meth)) { 
			if(!(des.getDesignator() instanceof DesignatorArray)) { //nije pristup elementu niza
				Code.load(des.getDesignator().obj);
			} 
			else {
				//if(des.getDesignator().obj.getType().getKind() == Struct.Array) {
				if(des.getDesignator().obj.getType().getKind() == Struct.Char)
					Code.put(Code.baload);
				else
					Code.put(Code.aload);
				//}
			}
		}
		
	}
	
	public void visit(FactorNewArray newArr) {
		if(newArr.getType().struct.getKind() == Struct.Char) {
			Code.put(Code.newarray);
			Code.loadConst(0);
		}
		else {
			Code.put(Code.newarray);
			Code.loadConst(1);
		}
	}
	
	public void visit(SingleNegativeFactor fac) {
		Code.put(Code.neg);
	}
	
	public void visit(DesignStatementOperation desOp) {
		if(desOp.getDesignatorOperation() instanceof DesignatorInc) {
			if(!(desOp.getDesignator() instanceof DesignatorArray)){
				Code.load(desOp.getDesignator().obj);
				Code.loadConst(1);
				Code.put(Code.add);
				Code.store(desOp.getDesignator().obj);
			} else {
				Code.put(Code.dup2);
				Code.put(Code.aload);
				Code.loadConst(1);
				Code.put(Code.add);
				Code.put(Code.astore);			
			}
		} 
		else if(desOp.getDesignatorOperation() instanceof DesignatorDec) {
			if(!(desOp.getDesignator() instanceof DesignatorArray)) {
				Code.load(desOp.getDesignator().obj);
				Code.loadConst(1);
				Code.put(Code.sub);
				Code.store(desOp.getDesignator().obj);
			} else {
				Code.put(Code.dup2);
				Code.put(Code.aload);
				Code.loadConst(1);
				Code.put(Code.sub);
				Code.put(Code.astore);			
			}
		}
		else if(desOp.getDesignatorOperation() instanceof DesignatorFuncCall) {
			Obj obj = desOp.getDesignator().obj;
			int offset = obj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
			if(obj.getType().getKind() != Struct.None) {
				Code.put(Code.pop);
			}
		}
	}
	
	public void visit(DesignatorAssignSimple desAss) {
		if(!(desAss.getDesignator() instanceof DesignatorArray)) {
			Code.store(desAss.getDesignator().obj);
		}
		else {
			if(desAss.getDesignator().obj.getType().getKind() == Struct.Char) {
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}
		}
	}
	
	public void visit(NoSingleDesignator desig) {
		DesigArray elem = new DesigArray();
		elem.type = -1;
		elem.obj=null;
		desigArrayList.push(elem);
	}
	
	public void visit(SingleDesignator desig) {
		DesigArray elem = new DesigArray();
		if(!(desig.getDesignator() instanceof DesignatorArray)) {
			elem.type = 0;
			elem.obj = desig.getDesignator().obj;
			desigArrayList.push(elem);
		}
		else {
			elem.type = 1;
			elem.obj = desig.getDesignator().obj;
			desigArrayList.push(elem);
		}
	}
	
	public void visit(DesignatorAssignArray desigArray) {
		Obj obj = desigArray.getDesignator().obj;
		//int index = desigArrayList.size() - 1;
		int index = 0;
		int numberOfDesignators = 0;
		
		for(DesigArray elem: desigArrayList) {
			if(elem.type != -1)
				numberOfDesignators = index;
			index++;
		}
		index = index-1;
		
		Code.loadConst(numberOfDesignators);
		Code.load(obj);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.lt, 0);
		int adrJmpError = Code.pc - 2;
		
		while(!desigArrayList.empty()) {
			DesigArray elem = desigArrayList.pop();
			if(elem.type == -1) {
				index--;
				continue;
			}
			else if(elem.type == 0) {
				Code.load(obj);
				Code.loadConst(index);
				if(obj.getType().getElemType().getKind()==Struct.Char)
					Code.put(Code.baload);
				else
					Code.put(Code.aload);
				Code.store(elem.obj);
			}
			else {
				Code.load(obj);
				Code.loadConst(index);
				if(obj.getType().getElemType().getKind()==Struct.Char) {
					Code.put(Code.baload);
					Code.put(Code.bastore);
				}
				else {
					Code.put(Code.aload);
					Code.put(Code.astore);
				}
			}
			index--;
		}
		
		Code.putJump(0);
		int adrJmpOk = Code.pc-2;
		Code.fixup(adrJmpError);
		Code.put(Code.trap);
		Code.put(2);
		Code.fixup(adrJmpOk);
	}
	/*---------------------------NIVO B------------------------------------*/
	public void visit(FactorFuncCallNoParams funcCall) {
		Obj obj = funcCall.getDesignator().obj;
		int offset = obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(FactorFuncCallParams funcCall) {
		Obj obj = funcCall.getDesignator().obj;
		int offset = obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(ReturnNoParamStatement ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(ReturnStatement ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	public int jmpCode(Relop relop) {
		if(relop instanceof EqualsOperator)
			return Code.eq;
		else if (relop instanceof NotEqualOperator)
			return Code.ne;
		else if (relop instanceof GreaterOperator)
			return Code.gt;
		else if (relop instanceof GreaterEqualOperator)
			return Code.ge;
		else if (relop instanceof LessOperator)
			return Code.lt;
		else 
			return Code.le;
	}
	
	Stack<Integer> thenList = new Stack<>(); //ako je ispunjen uslov skacemo na then
	Stack<Integer> endTermList = new Stack<>(); // ako nije ispunjen neki deo and onda skacemo na kraj terma
	Stack<Integer> wrongConditionList = new Stack<>(); //skacemo na else ili kraj ifa ili whilea
	Stack<Integer> elseList = new Stack<>();
	
	public void visit(CondFactExpressionRelop condFactRelop) {
		if(condFactRelop.getParent() instanceof SingleCondFact) {
			Relop relop = condFactRelop.getRelop();
			int relopCode = jmpCode(relop);
			Code.putFalseJump(Code.inverse[relopCode], 0);
			int adr = Code.pc - 2;
			thenList.push(adr);
		} 
		else if(condFactRelop.getParent() instanceof CondFactsList) {
			Relop relop = condFactRelop.getRelop();
			int relopCode = jmpCode(relop);
			Code.putFalseJump(relopCode, 0);
			int adr = Code.pc - 2;
			endTermList.push(adr);
		}
	}
	
	public void visit(CondFactExpression condFactExpr) {
		Code.loadConst(0);
		
		if(condFactExpr.getParent() instanceof SingleCondFact) {	
			Code.putFalseJump(Code.eq, 0);
			int adr = Code.pc - 2;
			thenList.push(adr);
		} 
		else if(condFactExpr.getParent() instanceof CondFactsList) {
			Code.putFalseJump(Code.ne, 0);
			int adr = Code.pc - 2;
			endTermList.push(adr);
		}
	}
	
	public void visit(CondTerm condTerm) {
		while(!endTermList.isEmpty()) {
			Integer adr = endTermList.pop();
			Code.fixup(adr);
		}
	}
	
	public void visit(Condition condition) {
		Code.putJump(0);
		int adr = Code.pc-2;
		wrongConditionList.push(adr);
		while(!thenList.isEmpty()) {
			int adr2 = thenList.pop();
			Code.fixup(adr2);
		}
	}
	
	public void visit(ElseWrapper elseWrapper) {
		Code.putJump(0);
		int adre = Code.pc-2;
		elseList.push(adre);
		if(!wrongConditionList.isEmpty()) {
			int adr = wrongConditionList.pop();
			Code.fixup(adr);
		}
	}
	
	public void visit(ElseStatement elseStmt) {
		if(!elseList.isEmpty()) {
			int adr = elseList.pop();
			Code.fixup(adr);
		}
	}
	
	public void visit(NoElseStatement noElse) {
		if(!wrongConditionList.isEmpty()) {
			int adr = wrongConditionList.pop();
			Code.fixup(adr);
		}
	}
	
	Stack<Integer> whileList = new Stack<>();
	
	@SuppressWarnings("serial")
	class BreakClass implements Serializable{
		int adr;
		int level;
	}
	Stack<BreakClass> breakList = new Stack<>();
	
	public void visit(WhileWrapper whileWrapper) {
		int adr = Code.pc;
		whileList.push(adr);	
	}
	
	public void visit(WhileStatement whileStm) {
		if(!whileList.isEmpty()) {
			int adr = whileList.pop();
			Code.putJump(adr);
		}
		if(!wrongConditionList.empty()) {
			int adr2 = wrongConditionList.pop();
			Code.fixup(adr2);
		}
		while(!breakList.isEmpty() && breakList.peek().level == whileList.size()+1) {
			int adr3 = breakList.pop().adr;
			Code.fixup(adr3);
		}
	}
	
	public void visit(BreakStatement breakStm) {
		Code.putJump(0);
		BreakClass bk = new BreakClass();
		bk.level = whileList.size();
		bk.adr = Code.pc-2;
		breakList.push(bk);
	}
	
	public void visit(ContinueStatement contStm) {
		if(!whileList.isEmpty()) {
			int adr = whileList.peek();
			Code.putJump(adr); 
		}
	}
	/*----------------------------FOREACH------------------------------*/
	int currentForLevel = 0;
	
	Obj findIdent(String identName) {
		Obj objMet= null;
		for(Obj o: ProgramNode.getLocalSymbols()){
			if(o.getName().equals(currentMethodName)) {
				objMet = o;
				break;
			}
		} //nadji metodu
		Obj objIdent = null;
		for(Obj o: objMet.getLocalSymbols()){
			if(o.getName().equals(identName)) {
				objIdent = o;
				break;
			}
		} //dal je lokalna
		if(objIdent == null) { //ako je globalna
			for(Obj o: ProgramNode.getLocalSymbols()){
				if(o.getName().equals(identName)) {
					objIdent = o;
					break;
				}
			} 
		}
		return objIdent;
	}
	
	Obj findCounter(int forLevel) {
		String name = "?"+forLevel+"";
		for(Obj o: ProgramNode.getLocalSymbols()){
			if(o.getName().equals(name)) {
				return o;
			}
		}
		return null;
	}
	
	Stack<Integer> forList = new Stack<>();
	
	public void visit(ForBegin forBegin) {
		currentForLevel++;
		String identName = forBegin.getS();
		Obj objIdent = findIdent(identName);
		//Obj objCounter = findCounter(currentForLevel);
		Obj objArray = forBegin.getDesignator().obj;
		
		Code.loadConst(-1);
		//Code.store(objCounter);
		
		int adr = Code.pc;
		whileList.push(adr);
		
		//Code.load(objCounter);
		Code.loadConst(1);
		Code.put(Code.add);
		//Code.store(objCounter);
		//Code.load(objCounter);
		
		Code.put(Code.dup);
		Code.load(objArray);
		Code.put(Code.arraylength);
		
		Code.putFalseJump(Code.lt, 0);
		int adrFor = Code.pc-2;
		forList.push(adrFor);
		
		Code.load(objArray);
		Code.put(Code.dup2);
		Code.put(Code.pop);
		//Code.load(objCounter);
		if(objIdent.getType().getKind() == Struct.Char)
			Code.put(Code.baload);
		else
			Code.put(Code.aload);
		
		Code.store(objIdent);	
	}
	
	public void visit(ForEachStatement forEachStm) {
		currentForLevel--;
		if(!whileList.isEmpty()) {
			int adr = whileList.pop();
			Code.putJump(adr);
		}
		
		if(!forList.isEmpty()) {
			int adr2 = forList.pop();
			Code.fixup(adr2);
		}
		
		while(!breakList.isEmpty() && breakList.peek().level == whileList.size()+1) {
			int adr3 = breakList.pop().adr;
			Code.fixup(adr3);
		}
		Code.put(Code.pop);
	}	
}
