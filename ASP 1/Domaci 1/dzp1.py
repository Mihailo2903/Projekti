def napravi(sr):
    n=0
    br=0
    s=sr.split(",")
    list=[]
    for i in s:
        list.append(i)
        br=br+1
        if br>n:
            n=n+1
            br=0
    e=n*(n+1)/2
    a=list[0:int(e)]
    a.append(n)
    return a

def dohvati(matrica,i,j,n):
    if i>n or j<=0:
        return None
    pomeraj=(i*(i-1))//2+j-1
    return matrica[pomeraj]

def promeni(matrica,i,j,n,vr):
    if i > n or j<=0:
        print("Element izvan opsega")
        return
    pomeraj = ((i * (i - 1)) // 2 + j - 1)
    matrica[pomeraj]=vr

print("Meni:")
print("1: Inicijalizuj matricu")
print("3: Dohvati zadati element")
print("4: Promeni vrednost zadatom elementu")
print("5: Izračunaj broj nepodrazumevanih elemenata")
print("6: Ispiši matricu")
print("7: Izračunaj uštedu prostora")
print("8: Briši matricu")
print("9: Prekini")

n=0
matrica=None
element=None
while True:
    s=int(input())
    if s==9:
        break
    if s!=1:
        if matrica==None:
            print("Niste uneli matricu")
            continue
        if s==3:
            unos=input("Dohvati element: ").split(",")
            i=int(unos[0])
            j=int(unos[1])
            if i<j:
                t=j
                j=i
                i=t
            element=dohvati(matrica,i,j,n)
            if element==None:
                print("Element izvan opsega")
                continue
            else:
                print("{}".format(element))
        elif s==4:
            unos=input("Promeni element: ").split(",")
            vrednost=int(input("Vrednost: "))
            i = int(unos[0])
            j = int(unos[1])
            if i < j:
                t = j
                j = i
                i = t
            promeni(matrica,i,j,n,vrednost)
        elif s==5:
            broj=n*(n+1)/2
            print("Broj nepodrazumevanih je {}".format(int(broj)))
        elif s==6:
            for i in range(1,n+1):
                red = ""
                for j in range(1,i+1):
                    red=red+str(dohvati(matrica,i,j,n))+" "
                if i!=n:
                    for j in range(i+1,n+1):
                        red=red+str(dohvati(matrica,j,i,n))+" "
                print(red)

        elif s==7:
            mem=100-(n+1)/2/n*100
            print("Memorijska ušteda je {0:.3f}%".format(mem))
        elif s==8:
            matrica=None
            n=0
        else:
            print("Pogresan unos")
            continue

    else:
        s1=input("Unesite matricu: ")
        matrica=napravi(s1)
        n=matrica.pop()
        continue
