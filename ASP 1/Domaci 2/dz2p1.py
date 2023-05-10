# u stablu je sin oznaceno sa s, cos sa c a ln sa l zbog jednostavnijeg ispisa
# ? oznacava unarni minus sve do trenutka kada ispisujemo stablo
# trigonometrijkse funkcije se racunaju u stepenima
import math
class Zaglavlje:
    def __init__(self):
        self.broj=0
        self.sledeci=None

class Cvor:
    def __init__(self,info=None):
        self.info=info
        self.levi=None
        self.desni=None
        self.posecen=1
        self.nivo=0 #maksimalna duzina puta do nekog lista, treba za visinu stabla

def ipr(s):
    if s=='+' or s=='-':
        return 2
    elif s=='*' or s=='/':
        return 3
    elif s=='^':
        return 5
    elif s=='?':
        return 7
    elif s=="sin" or s=="cos" or s=="ln":
        return 9
    elif s=='(':
        return 10
    else:
        return 1

def spr(s):
    if s=='+' or s=='-':
        return 2
    elif s=='*' or s=='/':
        return 3
    elif s=='^':
        return 4
    elif s=='?':
        return 6
    elif s=="sin" or s=="cos" or s=="ln":
        return 8
    else:
        return 0

def formiraj(izraz): #infiks u postfiks
    output=[]
    stack=[]
    i=0
    duzina=0
    while i<len(izraz):
        if izraz[i].isupper():
            output.append(izraz[i])
        else:
            if izraz[i] in {"+","-","*","/","^"}:
                while duzina > 0 and spr(stack[duzina-1])>=ipr(izraz[i]):
                    a=stack.pop()
                    output.append(a)
                    duzina=duzina-1
                stack.append(izraz[i])
                duzina+=1
            elif izraz[i]=='(':
                if izraz[i+1]=='-':
                    i=i+2
                    while duzina > 0 and spr(stack[duzina - 1]) >= ipr("?"): # ? ide na stek umesto unarnog minusa
                        a=stack.pop()
                        output.append(a)
                        duzina=duzina-1
                    stack.append("?")
                    duzina += 1
                else:
                    stack.append("(")
                    duzina += 1
            elif izraz[i]==')':
                while duzina > 0 and stack[duzina-1]!="(":
                    a = stack.pop()
                    output.append(a)
                    duzina = duzina - 1
                stack.pop()
                duzina = duzina - 1
            elif izraz[i]=="s":
                while duzina > 0 and spr(stack[duzina - 1]) >= ipr("sin"):
                    a = stack.pop()
                    output.append(a)
                    duzina = duzina - 1
                stack.append("sin")
                duzina += 1
                i=i+2
            elif izraz[i] == "c":
                while duzina > 0 and spr(stack[duzina - 1]) >= ipr("cos"):
                    a = stack.pop()
                    output.append(a)
                    duzina = duzina - 1
                stack.append("cos")
                duzina += 1
                i = i + 2
            else:
                while duzina > 0 and spr(stack[duzina - 1]) >= ipr("ln"):
                    a = stack.pop()
                    output.append(a)
                    duzina = duzina - 1
                stack.append("ln")
                duzina += 1
                i = i + 1
        i=i+1
    while duzina>0:
        a=stack.pop()
        output.append(a)
        duzina=duzina-1

    return output

def stablo(postfiks): #formira se stablo sa zaglavljem koje sadrzi broj cvorova
    cvorovi=[]
    i=0
    while i<len(postfiks):
        if postfiks[i].isupper():
            cvor=Cvor()
            cvor.info=postfiks[i]
            cvorovi.append(cvor)
        elif postfiks[i]=='?':
            sin=cvorovi.pop()
            cvor=Cvor()
            cvor.info="?"
            cvor.levi=sin
            cvor.nivo=sin.nivo+1
            cvorovi.append(cvor)
        elif postfiks[i] in {"sin","cos","ln"}:
            sin = cvorovi.pop()
            cvor = Cvor()
            cvor.info = postfiks[i]
            cvor.levi = sin
            cvor.nivo = sin.nivo + 1
            cvorovi.append(cvor)
        else:
            sin1=cvorovi.pop()
            sin2=cvorovi.pop()
            cvor=Cvor()
            cvor.info = postfiks[i]
            cvor.desni=sin1
            cvor.levi=sin2
            if sin1.nivo>sin2.nivo:
                cvor.nivo=sin1.nivo+1
            else:
                cvor.nivo=sin2.nivo+1
            cvorovi.append(cvor)

        i=i+1
    Glava= Zaglavlje()
    Glava.sledeci=cvorovi.pop()
    Glava.broj=len(postfiks)
    return Glava


def ispis(header): #ispis stabla
    koren=header.sledeci
    brcvorova=header.broj
    visina=koren.nivo
    linije=[]
    ind=[]
    queue=[]
    next=koren
    queue.append(next)
    while brcvorova>0:
        next=queue.pop()
        if next==None:
            ind.append(" ")
            queue.insert(0, None)
            queue.insert(0, None)
        else:
            if next.info=="sin": #u stablu je sin oznaceno sa s, kosinus sa c a ln sa l zbog jednostavnijeg ispisa
                ind.append("s")
            elif next.info=="cos":
                ind.append("c")
            elif next.info=="ln":
                ind.append("l")
            elif next.info==("?"):
                ind.append("-")
            else:
                ind.append(next.info)
            queue.insert(0,next.levi)
            queue.insert(0,next.desni)
            brcvorova-=1

    kon=len(queue)-1
    while kon>=0:
        ind.append(" ")
        kon-=1
    i=0
    index=0
    while i <= visina:
        s = ""
        broj = 2 ** i
        for j in range(0, 2 ** (visina - i) - 1):
            s = s + " "
        while broj > 0:
            s = s + ind[index]
            index += 1
            broj -= 1
            if broj > 0:
                for t in range(0, 2 ** (visina - i + 1) - 1):
                    s = s + " "
        for j in range(0, 2 ** (visina - i) - 1):
            s = s + " "
        i = i + 1
        linije.append(s)

    for i in range(0, len(linije)):
        print(linije[i])

def ispis_prefiks(Zaglavlje):
    root=Zaglavlje.sledeci
    stack=[]
    prefiks=""
    stack.append(root)
    d=1
    while d>0:
        next=stack.pop()
        d=d-1
        while next!=None:
            if next.info!="?":
                prefiks=prefiks+next.info+" "
            else:
                prefiks=prefiks+"(-) "
            if next.desni!=None:
                stack.append(next.desni)
                d=d+1
            next=next.levi

    print(prefiks)


def izracunaj(stablo,recnik):
    koren=stablo.sledeci
    stack=[]
    pomoc=[]
    next=koren
    duz=0
    while next!=None:
        stack.append(next)
        next=next.levi
        duz+=1
    while duz>0:
        next=stack[duz-1]
        if next.posecen>0:
            next.posecen=-1
            next=next.desni
            while next != None:
                stack.append(next)
                next = next.levi
                duz += 1
        else:
            next.posecen=1
            if next.info.isupper():
                if next.info not in recnik:
                    print("Unesi vrednost za {}".format(next.info))
                    recnik[next.info] = float(input())
                pomoc.append(recnik[next.info])
            elif next.info[0].isdigit():
                pomoc.append(float(next.info))
            elif next.info=="+":
                x2=pomoc.pop()
                x1=pomoc.pop()
                pomoc.append(x1+x2)
            elif next.info=="-":
                x2=pomoc.pop()
                x1=pomoc.pop()
                pomoc.append(x1-x2)
            elif next.info=="*":
                x2=pomoc.pop()
                x1=pomoc.pop()
                pomoc.append(x1*x2)
            elif next.info=="/":
                x2=pomoc.pop()
                x1=pomoc.pop()
                if x2==0:
                    print("Deljenje sa 0")
                    return None
                else:
                    pomoc.append(x1/x2)
            elif next.info=="sin":
                x1=pomoc.pop()
                pomoc.append(math.sin(x1/180*math.pi))
            elif next.info == "cos":
                x1 = pomoc.pop()
                pomoc.append(math.cos(x1/180*math.pi))
            elif next.info == "ln":
                x1 = pomoc.pop()
                if x1<=0:
                    print("Logaritmovanje negativnog broja")
                    return None
                else:
                    pomoc.append(math.log(x1))
            elif next.info == "^":
                x2 = pomoc.pop()
                x1 = pomoc.pop()
                if x1 <= 0:
                    print("Osnova stepenovanja je negativan broj (nije dozvoljeno)")
                    return None
                else:
                    pomoc.append(x1**x2)
            else:
                x1=pomoc.pop()
                pomoc.append(x1*(-1))
            stack.pop()
            duz=duz-1

    if len(pomoc)==1:
        return pomoc.pop()
    else:
        return None

def izvod(stablo,slovo):
    koren=stablo.sledeci
    funkcije=[]
    izvodi=[]
    stack=[]
    next=koren
    duz = 0
    while next != None:
        stack.append(next)
        next = next.levi
        duz += 1
    while duz>0:
        next=stack[duz-1]
        if next.posecen>0:
            next.posecen=-1
            next=next.desni
            while next != None:
                stack.append(next)
                next = next.levi
                duz += 1
        else:
            next.posecen=1
            if next.info.isupper():
                if next.info==slovo:
                    cvor=Cvor("1")
                    izvodi.append(cvor)
                    funkcije.append(next)
                else:
                    cvor=Cvor("0")
                    izvodi.append(cvor)
                    funkcije.append(next)
            elif next.info[0].isdigit():
                cvor = Cvor("0")
                izvodi.append(cvor)
                funkcije.append(next)
            elif next.info=="+":
                i2=izvodi.pop()
                i1=izvodi.pop()
                f2=funkcije.pop()
                f1=funkcije.pop()
                cvorf=Cvor("+")
                cvorf.levi=f1
                cvorf.desni=f2
                funkcije.append(cvorf)
                if i1.info=="0" and i2.info=="0":
                    cvor = Cvor("0")
                    izvodi.append(cvor)
                elif i1.info=="0":
                    izvodi.append(i2)
                elif i2.info=="0":
                    izvodi.append(i1)
                elif i1.info[0].isdigit() and i2.info[0].isdigit():
                    f=float(i1.info) + float(i2.info)
                    s=str(f)
                    cvor = Cvor(s)
                    izvodi.append(cvor)
                elif i1.info[0].isdigit() and i2.info=="?" and i2.levi.info[0].isdigit():
                    f = float(i1.info) - float(i2.levi.info)
                    if f==0:
                        cvor = Cvor("0")
                        izvodi.append(cvor)
                    elif f>0:
                        s = str(f)
                        cvor = Cvor(s)
                        izvodi.append(cvor)
                    else:
                        f=f*(-1)
                        s = str(f)
                        cvor = Cvor(s)
                        cvor1=Cvor("?")
                        cvor1.levi=cvor
                        izvodi.append(cvor1)
                elif i2.info[0].isdigit() and i1.info=="?" and i1.levi.info[0].isdigit():
                    f = float(i2.info) - float(i1.levi.info)
                    if f==0:
                        cvor = Cvor("0")
                        izvodi.append(cvor)
                    elif f>0:
                        s = str(f)
                        cvor = Cvor(s)
                        izvodi.append(cvor)
                    else:
                        f=f*(-1)
                        s = str(f)
                        cvor = Cvor(s)
                        cvor1=Cvor("?")
                        cvor1.levi=cvor
                        izvodi.append(cvor1)
                elif i1.info=="?" and i1.levi.info[0].isdigit() and i2.info=="?" and i2.levi.info[0].isdigit():
                    f = (float(i2.levi.info) + float(i1.levi.info)) * (-1)
                    if f==0:
                        cvor = Cvor("0")
                        izvodi.append(cvor)
                    else:
                        s = str(f)
                        cvor = Cvor(s)
                        cvor1 = Cvor("?")
                        cvor1.levi = cvor
                        izvodi.append(cvor1)
                else:
                    cvor=Cvor("+")
                    cvor.levi=i1
                    cvor.desni=i2
                    izvodi.append(cvor)
            elif next.info=="-":
                i2 = izvodi.pop()
                i1 = izvodi.pop()
                f2 = funkcije.pop()
                f1 = funkcije.pop()
                cvorf = Cvor("-")
                cvorf.levi = f1
                cvorf.desni = f2
                funkcije.append(cvorf)
                if i1.info == "0" and i2.info == "0":
                    cvor = Cvor("0")
                    izvodi.append(cvor)
                elif i1.info == "0":
                    cvor=Cvor("?")
                    if i2.info=="?":
                        izvodi.append(i2.levi)
                    else:
                        cvor.levi=i2
                        izvodi.append(cvor)
                elif i2.info == "0":
                    izvodi.append(i1)
                elif i1.info[0].isdigit() and i2.info[0].isdigit():
                    f = float(i1.info)-float( i2.info)
                    if f==0:
                        cvor = Cvor("0")
                        izvodi.append(cvor)
                    elif f>0:
                        s = str(f)
                        cvor = Cvor(s)
                        izvodi.append(cvor)
                    else:
                        f = f * (-1)
                        s = str(f)
                        cvor = Cvor(s)
                        cvor1 = Cvor("?")
                        cvor1.levi = cvor
                        izvodi.append(cvor1)
                elif i1.info[0].isdigit() and i2.info == "?" and i2.levi.info[0].isdigit():
                    f = float(i1.info) + float(i2.levi.info)
                    if f == 0:
                        cvor = Cvor("0")
                        izvodi.append(cvor)
                    else:
                        s = str(f)
                        cvor = Cvor(s)
                        izvodi.append(cvor)
                elif i2.info[0].isdigit() and i1.info == "?" and i1.levi.info[0].isdigit():
                    f = float(i2.info) + float(i1.levi.info)
                    if f == 0:
                        cvor = Cvor("0")
                        izvodi.append(cvor)
                    else:
                        s = str(f)
                        cvor = Cvor(s)
                        cvor1 = Cvor("?")
                        cvor1.levi = cvor
                        izvodi.append(cvor1)
                elif i1.info == "?" and i1.levi.info[0].isdigit() and i2.info == "?" and i2.levi.info[0].isdigit():
                    f = (float(i2.levi.info) - float(i1.levi.info))
                    if f == 0:
                        cvor = Cvor("0")
                        izvodi.append(cvor)
                    elif f>0:
                        s = str(f)
                        cvor = Cvor(s)
                        izvodi.append(cvor)
                    else:
                        s = str(f)
                        cvor = Cvor(s)
                        cvor1 = Cvor("?")
                        cvor1.levi = cvor
                        izvodi.append(cvor1)
                else:
                    cvor = Cvor("-")
                    cvor.levi = i1
                    cvor.desni = i2
                    izvodi.append(cvor)
            elif next.info=="*":
                i2 = izvodi.pop()
                i1 = izvodi.pop()
                f2 = funkcije.pop()
                f1 = funkcije.pop()
                cvorf = Cvor("*")
                cvorf.levi = f1
                cvorf.desni = f2
                funkcije.append(cvorf)
                if i1.info == "0" and i2.info == "0":
                    cvor = Cvor("0")
                    izvodi.append(cvor)
                elif i1.info == "0":
                    if i2.info=='1':
                        izvodi.append(f1)
                    else:
                        cvor1=Cvor("*")
                        cvor1.levi=i2
                        cvor1.desni=f1
                        izvodi.append(cvor1)
                elif i2.info=="0":
                    if i1.info == '1':
                        izvodi.append(f2)
                    else:
                        cvor1 = Cvor("*")
                        cvor1.levi = i1
                        cvor1.desni = f2
                        izvodi.append(cvor1)
                else:
                    cvor3 = Cvor("+")
                    if i1.info=="1":
                        cvor3.levi=f2
                    else:
                        cvor1 = Cvor("*")
                        cvor1.levi = f2
                        cvor1.desni = i1
                        cvor3.levi = cvor1
                    if i2.info=="1":
                        cvor3.desni=f1
                    else:
                        cvor2 = Cvor("*")
                        cvor2.levi = f1
                        cvor2.desni = i2
                        cvor3.desni = cvor2
                    izvodi.append(cvor3)
            elif next.info=="/":
                i2 = izvodi.pop()
                i1 = izvodi.pop()
                f2 = funkcije.pop()
                f1 = funkcije.pop()
                cvorf = Cvor("/")
                cvorf.levi = f1
                cvorf.desni = f2
                funkcije.append(cvorf)
                cvordelj=Cvor()
                if i1.info == "0" and i2.info == "0":
                    cvor = Cvor("0")
                    izvodi.append(cvor)
                elif i1.info == "0":
                    if i2.info=='1':
                        cvor1=Cvor("?")
                        if f1.info=="?":
                            cvordelj=f1.levi
                        else:
                            cvor1.levi=f1
                            cvordelj=cvor1
                    else:
                        cvor0=Cvor("?")
                        cvor1=Cvor("*")
                        cvor1.levi=i2
                        cvor1.desni=f1
                        cvor0.levi=cvor1
                        cvordelj=cvor0
                elif i2.info=="0":
                    if i1.info == '1':
                        cvordelj=f2
                    else:
                        cvor1 = Cvor("*")
                        cvor1.levi = i1
                        cvor1.desni = f2
                        cvordelj=cvor1
                else:
                    cvor3 = Cvor("-")
                    if i1.info==1:
                        cvor3.levi=f2
                    else:
                        cvor1 = Cvor("*")
                        cvor1.levi = i1
                        cvor1.desni = f2
                        cvor3.levi = cvor1
                    if i2.info==1:
                        cvor3.desni=f1
                    else:
                        cvor2 = Cvor("*")
                        cvor2.levi = f1
                        cvor2.desni = i2
                        cvor3.desni = cvor2
                    cvordelj=cvor3
                if i1.info != "0" or i2.info != "0":
                    if f2.info=="1":
                        izvodi.append(cvordelj)
                    else:
                        cvorst=Cvor("^")
                        cvor22=Cvor("2")
                        cvorst.levi=f2
                        cvorst.desni=cvor22
                        cvori=Cvor("/")
                        cvori.levi=cvordelj
                        cvori.desni=cvorst
                        izvodi.append(cvori)
            elif next.info == "sin":
                i1 = izvodi.pop()
                f1 = funkcije.pop()
                cvor=Cvor("sin")
                cvor.levi=f1
                funkcije.append(cvor)
                if i1.info=="0":
                    cvors = Cvor("0")
                    izvodi.append(cvors)
                elif i1.info=="1":
                    cvor1=Cvor("cos")
                    cvor1.levi=f1
                    izvodi.append(cvor1)
                else:
                    cvor1 = Cvor("cos")
                    cvor1.levi = f1
                    cvorc=Cvor("*")
                    cvorc.levi=i1
                    cvorc.desni=cvor1
                    izvodi.append(cvorc)
            elif next.info == "cos":
                i1 = izvodi.pop()
                f1 = funkcije.pop()
                cvor=Cvor("cos")
                cvor.levi=f1
                funkcije.append(cvor)
                if i1.info=="0":
                    cvors = Cvor("0")
                    izvodi.append(cvors)
                elif i1.info=="1":
                    cvor2=Cvor("?")
                    cvor1=Cvor("sin")
                    cvor1.levi=f1
                    cvor2.levi=cvor1
                    izvodi.append(cvor2)
                else:
                    cvor9=Cvor("?")
                    cvor1 = Cvor("sin")
                    cvor1.levi = f1
                    cvorc=Cvor("*")
                    cvorc.levi=i1
                    cvorc.desni=cvor1
                    cvor9.levi=cvorc
                    izvodi.append(cvor9)
            elif next.info== "ln":
                i1 = izvodi.pop()
                f1 = funkcije.pop()
                cvor = Cvor("ln")
                cvor.levi = f1
                funkcije.append(cvor)
                if i1.info=="0":
                    cvors = Cvor("0")
                    izvodi.append(cvors)
                elif i1.info=="1":
                    cvorl=Cvor("1")
                    cvorp=Cvor("/")
                    cvorp.levi=cvorl
                    cvorp.desni=f1
                    izvodi.append(cvorp)
                else:
                    cvorp = Cvor("/")
                    cvorp.levi = i1
                    cvorp.desni = f1
                    izvodi.append(cvorp)
            elif next.info=="^":
                i2 = izvodi.pop()
                i1 = izvodi.pop()
                f2 = funkcije.pop()
                f1 = funkcije.pop()
                cvorf = Cvor("^")
                cvorf.levi = f1
                cvorf.desni = f2
                funkcije.append(cvorf)
                if i1.info=="0" and i2.info=="0":
                    cvors = Cvor("0")
                    izvodi.append(cvors)
                elif i1.info=="0":
                    if f1.info=="1":
                        cvors = Cvor("0")
                        izvodi.append(cvors)
                    else:
                        cvor1=Cvor("^")
                        cvor1.levi=f1
                        cvor1.desni=f2
                        cvor2=Cvor("ln")
                        cvor2.levi=f1
                        cvor3=Cvor("*")
                        cvor3.levi=cvor1
                        cvor3.desni=cvor2
                        if i2.info=="1":
                            izvodi.append(cvor3)
                        else:
                            cvor4=Cvor("*")
                            cvor4.levi=i2
                            cvor4.desni=cvor3
                            izvodi.append(cvor4)
                else:    #i2.info=="0"
                    if f2.info=="0":
                        cvors = Cvor("0")
                        izvodi.append(cvors)
                    elif f2.info=="1":
                        izvodi.append(i1)
                    else:
                        cvor1=Cvor("-")
                        cvor1.levi=f2
                        cvor1.desni=Cvor("1")
                        cvor2=Cvor("^")
                        cvor2.levi=f1
                        cvor2.desni=cvor1
                        cvor3=Cvor("*")
                        cvor3.desni=cvor2
                        cvor3.levi=f2
                        izvodi.append(cvor3)
            else:
                i1 = izvodi.pop()
                f1 = funkcije.pop()
                cvor = Cvor("?")
                cvor.levi = f1
                funkcije.append(cvor)
                if i1.info=="0":
                    cvors = Cvor("0")
                    izvodi.append(cvors)
                else:
                    cvors = Cvor("?")
                    if i1.info=="?":
                        izvodi.append(i1.levi)
                    else:
                        cvors.levi=i1
                        izvodi.append(cvors)
            stack.pop()
            duz = duz - 1

    koren=izvodi.pop()
    next=koren
    brojcv=0
    max=0
    queue=[]
    duz=0
    queue.append([next,0])
    duz+=1
    while duz>0:
        clan=queue.pop()
        next=clan[0]
        level=clan[1]
        duz=duz-1
        if level>max:
            max=level
        brojcv+=1
        if next.levi!=None:
            queue.insert(0,[next.levi,level+1])
            duz+=1
        if next.desni!=None:
            queue.insert(0, [next.desni, level + 1])
            duz+=1
    koren.nivo=max
    header=Zaglavlje()
    header.broj=brojcv
    header.sledeci=koren
    return header



#Ovde počinje program
print("Meni:")
print("2: Ucitaj izraz i formiraj stablo")
print("3: Ispisi stablo")
print("4: Ispisi izraz u prefiksnoj formi")
print("5: Unesite vrednost za neku promenjivu u obliku X=.... (Ako ne zelite vise da unosite kliknite ENTER)")
print("6: Izračunaj vrednost izraza")
print("7: Diferenciraj izraz")
print("8: Izračunaj vrednost diferenciranog izraza")
print("9: Ispisi stablo diferenciranog izraza")
print("10: Zapocni diferenciranje po novoj promenjivoj")
print("11: Zavrsi")
print("")
recnik={}
ima=False
izraz=""
niz=""
post=[]
rez=0
stablo1=Zaglavlje()
izvod1=Zaglavlje()
k=0
slovo=""
while True:
    print("Unesite zeljeni broj")
    broj=int(input())
    if broj==11:
        break
    if ima==False:
        if broj!=2:
            print("Niste uneli izraz")
            continue
        else:
            print("Unesite izraz")
            izraz=input()
            post = formiraj(izraz)
            stablo1 = stablo(post)
            izvod1=stablo1
            k=0
            print("Vase stablo je formirano")
            ima=True
            continue
    else:
        if broj==2:
            print("Unesite novi izraz")
            izraz = input()
            post = formiraj(izraz)
            stablo1 = stablo(post)
            izvod1=stablo1
            k=0
            print("Vase stablo je formirano")
            continue
        elif broj==3:
            print("Vase stablo:")
            ispis(stablo1)
            continue
        elif broj==4:
            print("Prefiksna forma vaseg stabla:")
            ispis_prefiks(stablo1)
        elif broj==5:
            while True:
                s=input("Unesite vrednost promenjive: ")
                if s=="":
                    break
                else:
                    recnik[s[0]]=float(s[2:])
            continue
        elif broj==6:
            rez=izracunaj(stablo1,recnik)
            if rez==None:
                print("Izraz nije definisan za date vrednosti")
            else:
                print("Rezultat je : {0:.3f}".format(rez))
            continue
        elif broj==7:
            print("Unesite promenjivu po kojoj zelite da diferencirate")
            slovo=input()
            if len(slovo)>1 or slovo[0].isupper()==False:
                print("Samo jedno veliko slovo:")
                continue
            else:
                izvod1=izvod(izvod1,slovo)
                k=k+1
                niz=niz+" "+ slovo
            continue
        elif broj==8:
            if k==0:
                print("Niste jos diferencirali")
            else:
                rez=izracunaj(izvod1)
                print("Rezultat izvoda reda {} po promenjivoj {} je : {0:.3f}".format(k,slovo,rez))
            continue
        elif broj==9:
            if k==0:
                print("Niste jos diferencirali")
            else:
                print("Izvod reda {} vaseg stabla ako ste diferencirali po promenjivim {} (respektivno) , izgleda ovako".format(k,niz))
                ispis(izvod1)
            continue
        elif broj==10:
            k=0
            niz=""
            izvod1=stablo1
            print("Unesite promenjivu po kojoj zelite da diferencirate")
            slovo = input()
            if len(slovo) > 1 or slovo[0].isupper() == False:
                print("Samo jedno veliko slovo:")
                continue
            else:
                izvod1 = izvod(izvod1, slovo)
                k = k + 1
                niz=niz+" "+slovo
                continue
        else:
            print("Niste uneli validan broj")




