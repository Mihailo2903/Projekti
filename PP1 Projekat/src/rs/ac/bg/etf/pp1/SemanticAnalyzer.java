package rs.ac.bg.etf.pp1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	@SuppressWarnings("serial")
	class MethodData implements Serializable{
		String methodName = "";
		int numberOfParameters = 0;
		int checkedParameters = 0;
		boolean ok = true;
	}
	
	@SuppressWarnings("serial")
	class DesigData implements Serializable{
		Struct s;
	}

	Logger log = Logger.getLogger(getClass());
	boolean errorDetected = false;
	String currentConstantType = "";
	String currentVarType = "";
	//int currentScopeDeclaredVars = 0;
	String currentClassName = "";
	boolean classRedeclare = false;
	boolean methodDeclFalse = false;
	String currentmethodName = "";
	
	List<MethodData> methodList;
	List<DesigData> leftDesignators;
	List<String> forIdentList;
	
	int loopCounter = 0;
	boolean returnFound = false;
	boolean mainFound = false;
	
	Struct desigClass = null;
	
	int nVars;
	
	public String typeName(Struct s) {
		if(s == Tab.intType)
			return "int";
		else if(s == Tab.charType)
			return "char";
		else if (s == Tab.find("bool").getType())
			return "bool";
		else
			return "greska";
	}
	
	public Struct nameToType(String name) {
		if(name.equals("int"))
			return Tab.intType;
		else if(name.equals("char"))
			return Tab.charType;
		else if(name.equals("bool"))
			return Tab.find("bool").getType();
		
		Obj obj = Tab.find(name);
		if(obj == Tab.noObj || obj.getKind()!=Obj.Type)
			return Tab.noType;
		
		return obj.getType();
	}
	
	public boolean passed(){
    	return !errorDetected;
    }
	
	public String printNode(Obj node) {
		if(node == null)
			return "null";
		
		String str = "";
		int kind = node.getKind();
		switch(kind) {
			case 0: str += "Con, "; break;
			case 1: str += "Var, ";break;
			case 2: str += "Type, ";break;
			case 3: str += "Meth, ";break;
			case 4: str += "Fld, ";break;
			case 5: str += "Elem, ";break;
			default: str += "Prog, ";break;
		}
		
		Struct s = node.getType();
		while(s != null) {
			switch(s.getKind()) {
				case 1: str += "int"; break;
				case 2: str += "char"; break;
				case 3: str += "arr of "; break;
				case 4: str += "class"; break;
				case 5: str += "bool"; break;
				default: str += "notype"; break;		
			}
			s = s.getElemType();
		}
		
		str += ", ";
		
		str += node.getAdr() + ", " + node.getLevel();
		
		return str;	
	}
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		//log.error(msg.toString());
		System.err.println(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		//log.info(msg.toString());
		System.out.println(msg.toString());
	}
	
	public SemanticAnalyzer() {
		Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool));
		methodList = new ArrayList<>();
		leftDesignators = new ArrayList<>();
		forIdentList = new ArrayList<>();
		
		/*Tab.find("ord").setFpPos(1);
		Tab.find("chr").setFpPos(1);
		Tab.find("len").setFpPos(1);*/
		Tab.find("ord").setLevel(1);
		Tab.find("chr").setLevel(1);
		Tab.find("len").setLevel(1);
	}
	
	@Override
	public void visit(ProgName progName) {
		Obj newObj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		progName.obj = newObj;
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		Obj obj = Tab.find(program.getProgName().getProgName());	
		/*String forName="";
		for(int i=0; i<maxForLevel; i++) {
		   forName = "?" + (i+1) + "";
		   Tab.insert(Obj.Var, forName, Tab.intType);
		}*/
		
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(obj);
		Tab.closeScope();
		if(mainFound == false)
			report_error("GRESKA: Nije ispravno definisana main funkcija", program);
		//log.info(nVars);
	}
	
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if(typeNode == Tab.noObj) {
			report_error("GRESKA: Nije pronadjen tip " + type.getTypeName() + ", greska" , type);
			type.struct = Tab.noType;
		}
		else {
			if(typeNode.getKind() != Obj.Type) {
				report_error("GRESKA: Identifikator " + type.getTypeName() + " nije tip, greska", type);
				type.struct = Tab.noType;
			}
			else {
				type.struct = typeNode.getType();
			}
		}
	}
	
	public void visit(IntegerConstant constant) {
		Obj newObj = new Obj(Obj.Con,"konstanta",Tab.intType);
		newObj.setAdr(constant.getValue());
		constant.obj = newObj;
	}
	
	public void visit(CharConstant constant) {
		Obj newObj = new Obj(Obj.Con,"konstanta",Tab.charType);
		newObj.setAdr(constant.getValue());
		constant.obj = newObj;
	}
	
	public void visit(BoolConstant constant) {
		Obj boolObj = Tab.find("bool");
		if(boolObj == Tab.noObj) {
			report_error("GRESKA: Nije ubacen tip bool, greska", constant);
			constant.obj = Tab.noObj;
		}
		Obj newObj = new Obj(Obj.Con,"konstanta",boolObj.getType());
		if(constant.getValue().equals("true"))
			newObj.setAdr(1);
		else
			newObj.setAdr(0);
		
		constant.obj = newObj;
	}
	
	public void visit(ConstantDecl constDecl) {
		Obj node = constDecl.getConstant().obj;
		String name = constDecl.getConstName();
		String tip = typeName(node.getType());
		
		Scope top = Tab.currentScope;
		Obj check = top.findSymbol(name);
		
		if(check != null) {
			report_error("GRESKA: Konstanta " + name + " vec deklarisana, greska", constDecl);
			constDecl.obj = Tab.noObj;
			return;
		}
		
		if(currentConstantType.equals(tip)) {
			Tab.insert(Obj.Con, name, node.getType());
			Obj again = Tab.find(name);
			again.setAdr(node.getAdr());
			constDecl.obj = again;
			//report_info("Deklarisana konstanta " + name + " vrednosti " + node.getAdr() , constDecl);
		}
		else {
			report_error("GRESKA: Los tip konstane " + name + " , greska" , constDecl);
			constDecl.obj = Tab.noObj;
		}
	}
	
	public void visit(TypeConst typeConst) {
		String name = typeConst.getType().getTypeName();
		currentConstantType = name;
	}
	
	public void visit(ConstDecl constDecl) {
		currentConstantType = " ";
	}
	
	public void visit(TypeVar typeVar) {
		String name = typeVar.getType().getTypeName();
		currentVarType = name;
	}
	
	public void visit(VariableDecl varDecl) {
		if(methodDeclFalse || classRedeclare)
			return;
		
		String name = varDecl.getS();
		Scope top = Tab.currentScope;
		Obj check = top.findSymbol(name);
		
		if(check != null) {
			report_error("GRESKA: Promenljiva " + name + " vec deklarisana, greska", varDecl);
			varDecl.obj = Tab.noObj;
			return;
		}
		
		if(nameToType(currentVarType)==Tab.noType) {
			report_error("GRESKA: Nepostojeci tip promenljive " + currentVarType + " , greska", varDecl);
			varDecl.obj = Tab.noObj;
			return;
		}
		
		if(currentClassName.equals("") && classRedeclare==false)	
			Tab.insert(Obj.Var, name, nameToType(currentVarType));
		else if(classRedeclare == false && currentmethodName.equals(""))
			Tab.insert(Obj.Fld, name, nameToType(currentVarType));
		else if(classRedeclare == false)
			Tab.insert(Obj.Var, name, nameToType(currentVarType));
		//Obj again = Tab.find(name);
		//again.setAdr(currentScopeDeclaredVars);
		//currentScopeDeclaredVars ++;
		/*if(currentClassName.equals("") && currentmethodName.equals(""))	
			report_info("Deklarisana promenljiva " + name + " tipa " + currentVarType , varDecl);
		else if(currentmethodName.equals(""))
			report_info("Deklarisano polje " + name + " tipa " + currentVarType + " u klasi " + currentClassName , varDecl);
		else
			report_info("Deklarisana promenljiva " + name + " tipa " + currentVarType + " u metodi " + currentmethodName , varDecl);*/
	}
	
	public void visit(ArrayVariableDecl arrDecl) {
		if(methodDeclFalse || classRedeclare)
			return;
		
		String name = arrDecl.getS();
		Scope top = Tab.currentScope;
		Obj check = top.findSymbol(name);
		
		if(check != null) {
			report_error("GRESKA: Promenljiva " + name + " vec deklarisana, greska", arrDecl);
			arrDecl.obj = Tab.noObj;
			return;
		}
		
		if(nameToType(currentVarType)==Tab.noType) {
			report_error("GRESKA: Nepostojeci tip promenljive " + currentVarType + ", greska", arrDecl);
			arrDecl.obj = Tab.noObj;
			return;
		}
		
		Struct s = new Struct(Struct.Array);
		Struct tip = Tab.find(currentVarType).getType();
		s.setElementType(tip);
		
		if(currentClassName.equals("") && classRedeclare==false)	
			Tab.insert(Obj.Var, name, s);
		else if(classRedeclare == false && currentmethodName.equals(""))
			Tab.insert(Obj.Fld, name, s);
		else if(classRedeclare == false)
			Tab.insert(Obj.Var, name, s);
		//Obj again = Tab.find(name);
		//again.setAdr(currentScopeDeclaredVars);
		//currentScopeDeclaredVars ++;
		/*if(currentClassName.equals("") && currentmethodName.equals(""))	
			report_info("Deklarisana promenljiva " + name + " tipa " + currentVarType + "[]" , arrDecl);
		else if(currentmethodName.equals(""))
			report_info("Deklarisano polje " + name + " tipa " + currentVarType + "[] u klasi " + currentClassName , arrDecl);
		else
			report_info("Deklarisana promenljiva " + name + " tipa " + currentVarType + "[] u metodi " + currentmethodName , arrDecl);*/
	}
	
	public void visit(TypeClass typeClass) {
		String className = typeClass.getS();
		currentClassName = className;
		
		Obj ob = Tab.find(className);
		if(ob != Tab.noObj) {
			report_error("GRESKA: Klasa pod nazivom " + className + " je vec deklarisana, greska", typeClass);
			currentClassName = "";
			classRedeclare = true;
			return;
		}
		
		Struct str = new Struct(Struct.Class);
		Obj newObj = Tab.insert(Obj.Type, className, str);
		Tab.openScope();
		
	}
	
	public void visit(Class_Declaration classDecl) {
		if(classRedeclare) {			
			classRedeclare = false;
			return;
		}
		
		Obj newObj = Tab.find(currentClassName);
		Struct s = newObj.getType();
		Tab.chainLocalSymbols(s);
		
		Tab.closeScope();		
		currentClassName = "";			
	}
	
	public void visit(ReturnType returnType) {
		String methodName = returnType.getS();
		String typeName = returnType.getType().getTypeName();
		
		Obj obj = Tab.find(typeName);
		if(obj == Tab.noObj) {
			report_error("GRESKA: Nepostojeci povratni tip metoda, greska", returnType);
			methodDeclFalse = true;
			return;
		}
		
		Obj obj1 = Tab.find(methodName);
		if(obj1 != Tab.noObj) {
			report_error("GRESKA: Vec deklarisan metod " + methodName + ", greska", returnType);
			methodDeclFalse = true;
			return;
		}
		
		//report_info("Deklaracija metoda " + methodName, returnType);
		currentmethodName = methodName;
		Tab.insert(Obj.Meth, methodName, Tab.find(typeName).getType());
		Tab.openScope();
		
		if(!currentClassName.equals("")) {
			Obj o = Tab.find(currentClassName);
			Tab.insert(Obj.Var, "this",o.getType());
			//o.setFpPos(o.getFpPos()+1);
			o.setLevel(o.getLevel()+1);
		}
		
	}
	
	public void visit(ReturnVoid returnVoid) {
		String methodName = returnVoid.getS();
		
		Obj obj1 = Tab.find(methodName);
		if(obj1 != Tab.noObj) {
			report_error("GRESKA: Vec deklarisan metod " + methodName + ", greska", returnVoid);
			methodDeclFalse = true;
			return;
		}
		
		//report_info("Deklaracija metoda " + methodName, returnVoid);
		currentmethodName = methodName;
		Tab.insert(Obj.Meth, methodName, Tab.noType);
		Tab.openScope();
		
		if(!currentClassName.equals("")) {
			Obj o = Tab.find(currentClassName);
			Tab.insert(Obj.Var, "this",o.getType());
			//o.setFpPos(o.getFpPos()+1);
			o.setLevel(o.getLevel()+1);
		}
	}
	
	public void visit(Method_Declaration methDecl) {
		if(methodDeclFalse) {
			methodDeclFalse = false;
			return;
		}
		
		Obj newObj = Tab.find(currentmethodName);
		Tab.chainLocalSymbols(newObj);
		
		Tab.closeScope();	
		if(currentmethodName.equals("main") && currentClassName.equals("")) {
			if(newObj.getType().getKind() == Struct.None && newObj.getLevel()==0)// newObj.getFpPos()==0
				mainFound = true;
		}
				
		Obj met = Tab.find(currentmethodName);
	    if(met.getType().getKind() != Struct.None && returnFound == false )
	    	report_error("GRESKA: Fali return u metodi " + currentmethodName, methDecl);
	    
	   /* log.info(currentmethodName);
	    log.info(Tab.find(currentmethodName).getLevel());
	    log.info(Tab.find(currentmethodName).getLocalSymbols().size());*/
		returnFound = false;
		currentmethodName = "";	
		
	}
	
	public void visit(VarMethodParameter varM) {
		if(methodDeclFalse || classRedeclare)
			return;
		
		String name = varM.getS();
		String typeName = varM.getType().getTypeName();
		Scope top = Tab.currentScope;
		Obj check = top.findSymbol(name);
			
		if(check != null) {
			report_error("GRESKA: Parametar " + name + " vec deklarisan u metodi " + currentmethodName + ", greska", varM);
			return;
		}
		
		if(nameToType(typeName)==Tab.noType) {
			report_error("GRESKA: Nepostojeci tip parametra " + typeName + " , greska", varM);
			return;
		}
		

		Obj met = Tab.find(currentmethodName);
		//met.setFpPos(met.getFpPos()+1);
		met.setLevel(met.getLevel()+1);
			
		Tab.insert(Obj.Var, name, nameToType(typeName));
		//Obj again = Tab.find(name);
		//again.setAdr(currentScopeDeclaredVars);
		//currentScopeDeclaredVars ++;
		
		//report_info("Deklarisan parametar " + name + " tipa " + typeName + " metode " + currentmethodName , varM);		
	}
	
	public void visit(ArrayMethodParameter varM) {
		if(methodDeclFalse || classRedeclare)
			return;
		
		String name = varM.getS();
		String typeName = varM.getType().getTypeName();
		Scope top = Tab.currentScope;
		Obj check = top.findSymbol(name);
		
		if(check != null) {
			report_error("GRESKA: Promenljiva " + name + " vec deklarisana, greska", varM);
			return;
		}
		
		if(nameToType(typeName)==Tab.noType) {
			report_error("GRESKA: Nepostojeci tip parametra " + typeName + ", greska", varM);
			return;
		}
		
		Struct s = new Struct(Struct.Array);
		Struct tip = Tab.find(typeName).getType();
		s.setElementType(tip);
		

		Obj met = Tab.find(currentmethodName);
		//met.setFpPos(met.getFpPos()+1);
		met.setLevel(met.getLevel()+1);
		
		Tab.insert(Obj.Var, name, s);
		//Obj again = Tab.find(name);
		//again.setAdr(currentScopeDeclaredVars);
		//currentScopeDeclaredVars ++;
		//report_info("Deklarisan parametar " + name + " tipa " + typeName + "[] metode " + currentmethodName , varM);		
	}
	
	
	public void visit(DesignatorIdent designInit) {
		Obj obj = Tab.find(designInit.getS());
		
		if(obj == Tab.noObj) {
			report_error("GRESKA: Upotreba nedeklarisane promenljive " + designInit.getS(), designInit);
			designInit.obj = Tab.noObj;
			return;
		}
		
		designInit.obj = obj;
		
		if(obj.getKind() == Obj.Var) {
			if(!currentmethodName.equals("")) {
				Obj curm = Tab.find(currentmethodName);
				//report_info("EVO U ," + currentmethodName + " " + curm.getFpPos(), designInit);
				Collection<Obj> loc = Tab.currentScope().values();
				if(loc != null && loc.size()>0){
					//int numOfPar = curm.getFpPos();
					int numOfPar = curm.getLevel();
					int i=0;
					Iterator iter = loc.iterator();
					Obj par; boolean found = false;
					do {
						 par = (Obj) iter.next();
						 i++;
						 if(par.getName().equals(designInit.getS()))
							 found = true;
					} while(iter.hasNext() && !found);
					
					if(found) {
						if (i<=numOfPar)
							report_info("Upotreba formalnog argumenta " + designInit.getS() + " metode " + 
						     currentmethodName + ", " + printNode(Tab.find(designInit.getS())), designInit);
						else
							report_info("Upotreba lokalne promenljive " + designInit.getS() + " metode " + 
						     currentmethodName + ", " + printNode(Tab.find(designInit.getS())), designInit);
					}
					else
						report_info("Upotreba promenljive " + designInit.getS() + ", " + printNode(Tab.find(designInit.getS())), designInit);
				}
				else
					report_info("Upotreba promenljive " + designInit.getS() + ", " + printNode(Tab.find(designInit.getS())), designInit);
			}
			else
				report_info("Upotreba promenljive " + designInit.getS() + ", " + printNode(Tab.find(designInit.getS())), designInit);
		}			
		else if(obj.getKind() == Obj.Meth) {
			report_info("Poziv metode " + designInit.getS() + ", " + printNode(Tab.find(designInit.getS())), designInit);
			String name = obj.getName();
			if(obj.getLevel() > 0) { //obj-getFpPos
				MethodData data = new MethodData();
				data.methodName = name;						
				data.numberOfParameters = obj.getLevel(); //getfppos
				data.checkedParameters = 0;
				data.ok = true;
				methodList.add(data);
				//report_info("Ubacena metoda " + designInit.getS() + " u listu sa " + obj.getFpPos() + " param", designInit);
			}		
		}
		else
			report_info("Upotreba konstante " + designInit.getS() + ", " + printNode(Tab.find(designInit.getS())), designInit);
	}
	
	public void visit(DesignatorArray designArray) {
		Obj DesignType = designArray.getDesignator().obj;
		Struct ExprType = designArray.getExpr().struct;
		
		if(DesignType == Tab.noObj || ExprType == Tab.noType) {
			designArray.obj = Tab.noObj;
			return;
		}
		
		if(DesignType.getType().getKind() != Struct.Array) {
			report_error("GRESKA: Pokusano indeksiranje promenljive koja nije niz ", designArray);
			designArray.obj = Tab.noObj;
			return;
		}
		
		if(ExprType.getKind() != Struct.Int) {
			report_error("GRESKA: Indeks niza nije tipa int", designArray);
			designArray.obj = Tab.noObj;
			return;
		}
		
		designArray.obj = new Obj(Obj.Var,"promenljiva",DesignType.getType().getElemType());
		report_info("Pristup elementu niza " + DesignType.getName(), designArray);
		
	}
	
	public void visit(DesignatorWord designWord) {
		Obj DesignType = designWord.getDesignator().obj;
		String identName = designWord.getS();
		
		if(DesignType == Tab.noObj) {
			designWord.obj = Tab.noObj;
			return;
		}
			
		if(DesignType.getType().getKind() != Struct.Class) {
			report_error("GRESKA: Pokusan pristup polju promenljive koja nije klasa ", designWord);
			designWord.obj = Tab.noObj;
			return;
		}
		
		desigClass = DesignType.getType();
		Collection<Obj> members = DesignType.getType().getMembers();
		for(Obj member: members) {
			if(member.getName().equals(identName)) {
				report_info("Upotreba polja " + identName + " promenljive " + DesignType.getName(), designWord);
				designWord.obj = new Obj(member.getKind(), member.getName(), member.getType());
				return;
			}
		}
		
		report_error("GRESKA: Nepostojece polje u klasi", designWord);
		designWord.obj=Tab.noObj;
	}
	
	
	public void visit(FactorNewObjNoParams factor) {
		String typeName = factor.getType().getTypeName();
		Obj obj = Tab.find(typeName);
		
		if(obj == Tab.noObj || obj.getKind()!= Obj.Type || obj.getType().getKind() != Struct.Class) {
			report_error("GRESKA: Nepostojeci tip klase", factor);
			factor.struct = Tab.noType;
			return;
		}
			
		//report_info("Napravljen objekat klase " + typeName, factor);
		factor.struct = obj.getType();
		return;
	}
	
	public void visit(FactorNewObjParams factor) {
		String typeName = factor.getType().getTypeName();
		Obj obj = Tab.find(typeName);
		
		if(obj == Tab.noObj || obj.getKind()!= Obj.Type || obj.getType().getKind() != Struct.Class) {
			report_error("GRESKA: Nepostojeci tip klase", factor);
			factor.struct = Tab.noType;
			return;
		}
		
		//report_info("Napravljen objekat klase " + typeName, factor);
		factor.struct = obj.getType();
		return;
	}
	
	public void visit(FactorNewArray factor) {
		String typeName = factor.getType().getTypeName();
		//report_info("Tip new " + typeName, factor);
		Struct exprType = factor.getExpr().struct;
		Obj obj = Tab.find(typeName);
		
		if(obj == Tab.noObj || obj.getKind()!= Obj.Type) {
			report_error("GRESKA: Nepostojeci tip", factor);
			factor.struct = Tab.noType;
			return;
		}
		
		if(exprType.getKind() != Struct.Int) {
			report_error("GRESKA: Indeks niza nije tipa int", factor);
			factor.struct = Tab.noType;
			return;
		}
		
		//report_info("Napravljen niz objekata tipa " + typeName, factor);
		
		Struct s = new Struct(Struct.Array);
		s.setElementType(obj.getType());
		factor.struct = s;
		return;
	}
	
	public void visit(FactorSingleExpression factor) {
		Struct exprType = factor.getExpr().struct;
		
		if(exprType == Tab.noType) {
			factor.struct = Tab.noType;
			return;
		}
		
		factor.struct=exprType;
	}
	
	public void visit(FactorCharConstant factor) {
		factor.struct=Tab.charType;
	}
	
	public void visit(FactorBoolConstant factor) {
		factor.struct = Tab.find("bool").getType();
	}
	
	public void visit(FactorNumConstant factor){
		factor.struct = Tab.intType;
	}
	
	public void visit(FactorSingleDesignator factor) {
		if(factor.getDesignator().obj == Tab.noObj) {
			factor.struct = Tab.noType;
			return;
		}
		
		factor.struct = factor.getDesignator().obj.getType();
	}
	
	public void visit(FactorFuncCallNoParams factor) {
		
		if(methodList.size()>0) {
			MethodData mt = methodList.get(methodList.size()-1);
			if (mt.methodName.equals(factor.getDesignator().obj.getName())){
				methodList.remove(methodList.size()-1);
			}
		}
		
		if(factor.getDesignator().obj == Tab.noObj) {
			factor.struct = Tab.noType;
			return;
		}
		
		String funcName = factor.getDesignator().obj.getName();
		
		Obj obj = Tab.find(funcName);
		
		if(obj == Tab.noObj || obj.getKind() != Obj.Meth) {
			report_error("GRESKA: Poziv nepostojece funkcije", factor);
			factor.struct = Tab.noType;
			return;
		}
		
		//Collection<Obj> loc = obj.getLocalSymbols();
		
		if(obj.getLevel()==0) { //getfppos
			factor.struct = obj.getType();
			return;
		}
		
		report_error("GRESKA: Los broj argumenata u metodi " + funcName, factor);
		factor.struct=Tab.noType;	
	}
	
	public void visit(FactorFuncCallParams factor) {	
		if(factor.getDesignator().obj == Tab.noObj) {
			factor.struct = Tab.noType;
			return;
		}
		
		String funcName = factor.getDesignator().obj.getName();
		//report_info("poziv " + funcName + " " + desigClass, factor);
		
		/*Obj obj = null;
		if(desigClass!=null) {
			Collection<Obj> col = desigClass.getMembers();
			desigClass= null;		
			for(Obj f: col) {
				if(f.getName().equals(funcName)) {
					obj = f;
					break;
				}
			}
			if(obj == null){
				report_error("GRESKA: Poziv nepostojece funkcije", factor);
				factor.struct = Tab.noType;
				return;
			}
		}
		else*/
		Obj obj = Tab.find(funcName);
		
		if(obj == Tab.noObj || obj.getKind() != Obj.Meth) {
			report_error("GRESKA: Poziv nepostojece funkcije", factor);
			factor.struct = Tab.noType;
			return;
		}
		
		if(methodList.size()<=0) {
			report_error("GRESKA: Losi parametri funkcije " + obj.getName(), factor);
			factor.struct = Tab.noType;
			return;
			
		}
		
		MethodData met = methodList.remove(methodList.size()-1);
		
		if(met.ok == false || met.numberOfParameters != met.checkedParameters) {
			report_error("GRESKA: Losi parametri funkcije " + obj.getName(), factor);
			factor.struct = Tab.noType;
			return;
		}
			
		factor.struct = obj.getType();
	}
	
	public void visit(SingleFactor factor) {
		
		if(factor.getFactor().struct == Tab.noType) {
			factor.struct = Tab.noType;
			return;
		}
		
		factor.struct = factor.getFactor().struct;
	}
	
	public void visit(SingleNegativeFactor factor) {
		if(factor.getFactor().struct != Tab.intType) {
			report_error("GRESKA: Negiranje vrednosti koja nije tipa int", factor);
			factor.struct = Tab.noType;
			return;
		}		
		factor.struct = factor.getFactor().struct;
	}
	
	public void visit(FactorsList factor) {
		if(factor.getFactor().struct != Tab.intType || factor.getFactorList().struct != Tab.intType ) {
			report_error("GRESKA: Mnozenje vrednosti koja nije tipa int", factor);
			factor.struct = Tab.noType;
			return;
		}
		factor.struct = factor.getFactor().struct;
	}
	
	public void visit(Term factor) {
		if(factor.getFactorList().struct == Tab.noType) {
			factor.struct = Tab.noType;
			return;
		}
		factor.struct = factor.getFactorList().struct;
	}
	
	public void visit(SingleTerm factor) {
		if(factor.getTerm().struct == Tab.noType) {
			factor.struct = Tab.noType;
			return;
		}
		factor.struct = factor.getTerm().struct;
	}
	
	public void visit(TermsList factor) {
		if(factor.getTerm().struct != Tab.intType || factor.getTermList().struct != Tab.intType ) {
			report_error("GRESKA: Sabiranje vrednosti koja nije tipa int", factor);
			factor.struct = Tab.noType;
			return;
		}
		factor.struct = factor.getTerm().struct;
	}
	
	public void visit(Expr expr) {
		if(expr.getTermList().struct == Tab.noType) {
			expr.struct=Tab.noType;
			return;
		}
		
		expr.struct=expr.getTermList().struct;
	}
	
	public void visit (CondFactExpression condFact) {
		if(condFact.getExpr().struct == Tab.noType) {
			condFact.struct=Tab.noType;
			return;
		}
		
		condFact.struct = condFact.getExpr().struct;	
	}
	
	public void visit (CondFactExpressionRelop condFact) {
		Struct e1 = condFact.getExpr().struct;
		Struct e2 = condFact.getExpr1().struct;
		Relop r = condFact.getRelop();
			
		if(e1 == Tab.noType && e2 == Tab.noType) {
			condFact.struct=Tab.find("bool").getType();
			return;
		}
		
		if(!e1.compatibleWith(e2)) {
			report_error("GRESKA: Nekompatibilni tipovi", condFact);
			condFact.struct=Tab.noType;
			return;
		}
		
		if(e1.isRefType() || e2.isRefType()) {
			if(r instanceof EqualsOperator || r instanceof NotEqualOperator) {			
					condFact.struct = Tab.find("bool").getType();
					return;
			}
			else {
				report_error("GRESKA: Los Relop operator", condFact);
				condFact.struct=Tab.noType;
				return;
			}
		}
		
		condFact.struct = Tab.find("bool").getType();	
	}
	
	public void visit(SingleCondFact cond) {
		if(cond.getCondFact().struct == Tab.noType) {
			cond.struct=Tab.noType;
			return;
		}
		
		cond.struct = cond.getCondFact().struct;
		//log.info("CondFact");
		/*SyntaxNode n = cond;
		while(!(n instanceof Condition)) {
			if(n.getParent() instanceof SingleCondTerm) {
				log.info("Uspeh");
				break;
			}
			n = n.getParent();			
		}	*/
	}
	
	public void visit(CondFactsList cond) {
		if(cond.getCondFact().struct == Tab.noType || cond.getCondFact().struct == Tab.noType) {
			report_error("GRESKA: Losi parametri AND izraza", cond);
			cond.struct=Tab.noType;
			return;
		}
		
		cond.struct = cond.getCondFact().struct;
		//log.info("CondFactList");
	}
	
	public void visit(CondTerm cond) {
		if(cond.getCondFactList().struct == Tab.noType) {
			cond.struct=Tab.noType;
			return;
		}
		
		cond.struct = cond.getCondFactList().struct;
	}
	
	public void visit(SingleCondTerm cond) {
		if(cond.getCondTerm().struct == Tab.noType) {
			cond.struct=Tab.noType;
			return;
		}
		
		cond.struct = cond.getCondTerm().struct;
	}
	
	public void visit(CondTermsList cond) {
		if(cond.getCondTermList().struct == Tab.noType || cond.getCondTerm().struct == Tab.noType) {
			report_error("GRESKA: Losi parametri OR izraza", cond);
			cond.struct=Tab.noType;
			return;
		}
		
		cond.struct = cond.getCondTerm().struct;
	}
	
	public void visit(Condition cond) {
		if(cond.getCondTermList().struct == Tab.noType) {
			cond.struct=Tab.noType;
			return;
		}
		
		cond.struct = cond.getCondTermList().struct;
	}
	
	public void visit(SingleExpression expr) {
		try {	
			if(methodList.size()<=0) {
				expr.struct = Tab.noType;
				return;
			}
			MethodData currData = methodList.get(methodList.size()-1);
			//report_info("AA " + currData.checkedParameters + " - " + currData.numberOfParameters, expr);
			if(currData.checkedParameters >= currData.numberOfParameters) {
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}		
			Obj obj = Tab.find(currData.methodName);
			if(obj == Tab.noObj || obj.getKind() != Obj.Meth) {
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}
			
			Collection<Obj> loc = obj.getLocalSymbols();
			Iterator<Obj> iterator = loc.iterator();
			
			/*boolean thisFound = false;
			for(Obj th: loc) {
				if(th.getName().equals("this")) {
					thisFound=true;
					break;
				}
			}
			
			if(thisFound && currData.checkedParameters == 0) {
				currData.checkedParameters = 1;
			}*/
			int i=0;
			while(i < currData.checkedParameters && iterator.hasNext()) {
				i++;
				iterator.next();
			}
						
			if(!iterator.hasNext())
			{
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}
			
			Obj ob = iterator.next();
			
			if(expr.getExpr().struct.assignableTo(ob.getType())) {
				currData.checkedParameters ++;
				expr.struct = ob.getType();
			}
			
			else {
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}
			
		} catch(Exception e){}
		
	}
	
	public void visit(ExpressionList expr) {
		try {		
			MethodData currData = methodList.get(methodList.size()-1);
			//report_info("AA " + currData.checkedParameters + " - " + currData.numberOfParameters, expr);
			if(currData.checkedParameters >= currData.numberOfParameters) {
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}		
			Obj obj = Tab.find(currData.methodName);
			if(obj == Tab.noObj || obj.getKind() != Obj.Meth) {
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}
			
			Collection<Obj> loc = obj.getLocalSymbols();
			Iterator<Obj> iterator = loc.iterator();
			
			/*boolean thisFound = false;
			for(Obj th: loc) {
				if(th.getName().equals("this")) {
					thisFound=true;
					break;
				}
			}
			
			if(thisFound && currData.checkedParameters == 0) {
				currData.checkedParameters = 1;
			}*/
			
			int i=0;
			while(i < currData.checkedParameters && iterator.hasNext()) {
				i++;
				iterator.next();
			}
			
			
			if(!iterator.hasNext())
			{
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}
			
			Obj ob = iterator.next();
			
			if(expr.getExpr().struct.assignableTo(ob.getType())) {
				currData.checkedParameters ++;
				expr.struct = ob.getType();
			}
			
			else {
				expr.struct = Tab.noType;
				currData.ok = false;
				return;
			}
			
		} catch(Exception e){}
		
	}
	
	public void visit(ActPars factor) {
		//report_info("BB ",factor);
	}
	
	public void visit(DesignatorAssignSimple stm) {
		Obj des = stm.getDesignator().obj;
		Struct exp = stm.getExpr().struct;
		
		int kind = des.getKind();
		String name = des.getName();
		
		if(des == Tab.noObj || (kind !=Obj.Var && kind!=Obj.Fld)) {
			report_error("GRESKA: Los tip designatora", stm);
			return;
		}
		
		Struct desSt = des.getType();
		
		if(!exp.assignableTo(desSt))
			report_error("GRESKA: Nekompatibilni tipovi pri dodeli", stm);	
		
		if(forIdentList.size()>0) {
			for(String s: forIdentList)
			if(s.equals(name))
				report_error("GRESKA: Dodela promenljive ident od foreach", stm);
		}
	}
	
	public void visit (DesignStatementOperation stm) {
		Obj des = stm.getDesignator().obj;
		DesignatorOperation desOp = stm.getDesignatorOperation();
		int kind = des.getKind();
		String name = des.getName();
		
		if(desOp instanceof DesignatorInc || desOp instanceof DesignatorDec) {	
			if(forIdentList.size()>0) {
				for(String s: forIdentList)
					if(s.equals(name))
						report_error("GRESKA: Dodela promenljive ident od foreach", stm);
			}
			
			if(des == Tab.noObj || (kind !=Obj.Var && kind!=Obj.Fld)) {
				report_error("GRESKA: Los tip designatora", stm);
				return;
			}			
			if(des.getType().getKind() != Struct.Int) {
				report_error("GRESKA: Inkrement/dekrement promenljive koja nije tipa int", stm);
				return;
			}
		}
		else if(desOp instanceof DesignatorFuncCall) {
			if(des == Tab.noObj || kind != Obj.Meth) 
				report_error("GRESKA: Poziv nepostojece funckije sa desne strane", desOp);
			if(((DesignatorFuncCall) desOp).getFuncParams() instanceof FunctionNoParameters) {
				if(methodList.size()>0) {
					if(methodList.get(methodList.size()-1).methodName.equals(des.getName())) {
						methodList.remove(methodList.size()-1);
					}
				}
			}	
		}	
	}
	
	public void visit(FunctionParameters params) {	
		if(methodList.size()<=0) {
			report_error("GRESKA: Losi parametri funkcije ", params);
			return;
			
		}	
		MethodData met = methodList.remove(methodList.size()-1);
		String name = met.methodName;
		
		if(met.ok == false || met.numberOfParameters != met.checkedParameters) {
			report_error("GRESKA: Losi parametri funkcije " + name, params);
			return;
		}			
	}
	
	public void visit(SingleDesignator desig) {
		Obj obj = desig.getDesignator().obj;
		String name = obj.getName();
		
		if(forIdentList.size()>0) {
			for(String s: forIdentList)
				if(s.equals(name))
					report_error("GRESKA: Dodela promenljive ident od foreach", desig);
		}
		
		if(obj == Tab.noObj || (obj.getKind() != Obj.Var && obj.getKind() != Obj.Fld)) {
			report_error("GRESKA: Los levi designator", desig);
			return;
		}
		DesigData d = new DesigData();
		d.s = obj.getType();
		
		leftDesignators.add(d);
	}	
	
	public void visit(DesignatorAssignArray desig) {
		Obj designator = desig.getDesignator().obj;
		
		if(designator == Tab.noObj || designator.getType().getKind() != Struct.Array) {
			report_error("GRESKA: Los designator sa desne strane", desig);
			return;
		}
		
		while(leftDesignators.size()>0) {
			Struct dest = leftDesignators.remove(leftDesignators.size()-1).s;
			if(!designator.getType().getElemType().assignableTo(dest)) {
				report_error("GRESKA: Nekompatibilan tip levog designatora", desig);
			}
		}
	}
	
	int forLoopCounter = 0;
	int maxForLevel = 0;
	
	public void visit(ForBegin forBeg) {
		Obj des = forBeg.getDesignator().obj;
		if( des == Tab.noObj || des.getType().getKind() != Struct.Array) {
			report_error("GRESKA: Designator u foreach nije tipa niza", forBeg);
		}
		
		String name = forBeg.getS();
		Obj ident = Tab.find(name);
		
		//report_info("EEE " + des.getType().getKind() + " - " + des.getType().getElemType().getKind() + " - " + ident.getType().getKind(), forBeg);
		
		if(ident == Tab.noObj || !ident.getType().equals(des.getType().getElemType())) {
			report_error("GRESKA: Nekompatibilan tip ident u foreach ", forBeg);
		}
		
		loopCounter++; forLoopCounter++;
		if(forLoopCounter > maxForLevel) maxForLevel = forLoopCounter;
		forIdentList.add(name);		
	}
	
	public void visit(ForEachStatement forStm) {
		loopCounter--;
		forLoopCounter--;
		forIdentList.remove(forIdentList.size()-1);
	}
	
	public void visit(WhileBeg whileBeg) {
		Struct con = whileBeg.getCondition().struct;
		if(con.getKind() != Struct.Bool) {
			report_error("GRESKA: Uslov while petlje nije tipa bool", whileBeg);
		}
		loopCounter++;
	}
	
	public void visit(WhileStatement whileStm) {
		loopCounter--;
	}
	
	public void visit(RegularConditionIf ifCon) {
		Struct con = ifCon.getCondition().struct;
		if(con.getKind() != Struct.Bool) {
			report_error("GRESKA: Uslov if naredbe nije tipa bool", ifCon);
		}
	}
	
	public void visit(ReturnStatement re) {	
		Struct r = re.getExpr().struct;
		if(currentmethodName.equals("")) {
			report_error("GRESKA: Return iskaz van definicije funkcije ", re);
		} else {
			returnFound = true;
		}	
		Struct retTyp = Tab.find(currentmethodName).getType();
		if(!retTyp.equals(r)) {
			report_error("GRESKA: Retrun iskaz loseg tipa", re);
		}
	}
	
	public void visit(ReturnNoParamStatement re) {
		if(currentmethodName.equals("")) {
			report_error("GRESKA: Return iskaz van definicije funkcije ", re);
		} else {
			returnFound = true;
		}
				
		Struct retTyp = Tab.find(currentmethodName).getType();
		if(retTyp.getKind() != Struct.None) {
			report_error("GRESKA: Retrun iskaz loseg tipa", re);
		}
	}
	
	public void visit(PrintStatement print) {
		Struct s = print.getExpr().struct;
		
		if(s.getKind() == Struct.None || (s.getKind()!=Struct.Int && s.getKind()!= Struct.Bool && s.getKind()!=Struct.Char))
			report_error("GRESKA: Los tip u print iskazu", print);
	}
	
	public void visit(PrintNoParamStatement print) {
		Struct s = print.getExpr().struct;
		
		if(s.getKind() == Struct.None || (s.getKind()!=Struct.Int && s.getKind()!= Struct.Bool && s.getKind()!=Struct.Char))
			report_error("GRESKA: Los tip u print iskazu", print);
	}
	
	public void visit(ReadStatement read) {
		Obj des = read.getDesignator().obj;
		int kind = des.getKind();
				
		if(des == Tab.noObj || (kind !=Obj.Var && kind!=Obj.Fld)) {
			report_error("GRESKA: Los tip designatora u read iskazu", read);
		}
		
		int tip = des.getType().getKind();
		if(tip != Struct.Int && tip != Struct.Bool && tip!= Struct.Char)
			report_error("GRESKA: Los tip designatora u read iskazu", read);
	}
	
	public void visit(BreakStatement br) {
		if (loopCounter == 0)
			report_error("GRESKA: Poziv break van petlje", br);
	}
	
	public void visit(ContinueStatement br) {
		if (loopCounter == 0)
			report_error("GRESKA: Poziv continue van petlje", br);
	}	
}
