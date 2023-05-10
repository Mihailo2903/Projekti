class ListNode:
 def __init__(self, info=None):
     self.info = info
     self.next = None

def proveri(s):
    brojac=0
    for i in range(len(s)):
        if s[i]=="+" or s[i]=="-" or s[i]=="/" or s[i]=="*":
            brojac=brojac-2
            if brojac<0:
                return False
            brojac+=1
        elif s[i].isupper():
            brojac = brojac + 1
        else:
            return False
    if brojac==1:
        return True
    return  False

def izracunaj(izraz,recnik):
    vrh=None
    for i in izraz:
        if i.isupper():
            novi = ListNode()
            if i not in recnik:
                print("Unesi vrednost za {}".format(i))
                recnik[i]=float(input())
            novi.info = recnik[i]
            novi.next = vrh
            vrh = novi
        else:
            a = vrh.info
            b = vrh.next.info
            if i=="+":
                vrh.next.info=b+a
            elif i=="*":
                vrh.next.info=b*a
            if i=="-":
                vrh.next.info=b-a
            if i=="/":
                if a==0:
                    return None
                else:
                    vrh.next.info=b/a
            r=vrh
            vrh=vrh.next
            del(r)
    return vrh.info

print("Meni:")
print("1: Unesite izraz")
print("2: Unesite vrednost promenjive")
print("3: Izracunaj izraz")
print("4: Prekini")
izraz=None
recnik={}
while True:
    n=int(input())
    if n==4:
        break
    if n!=1:
        if n==2:
            while True:
                s=input("Unesite vrednost promenjive:")
                if s=="":
                    break
                else:
                    recnik[s[0]]=float(s[2:])
        elif n==3:
            if izraz==None:
                 print("Niste uneli izraz, kliknite 1")
                 continue
            else:
                 rezultat=izracunaj(izraz,recnik)
                 if rezultat==None:
                     print("Deljenje sa nulom")
                 else:
                     print("Rezultat je : {0:.3f}".format(rezultat))
        else:
            print("Pogresan unos")
            continue
    else:
        izraz=input("Unesite izraz: ")
        if proveri(izraz):
            continue
        else:
            print("Pogresan izraz")
            izraz=None



