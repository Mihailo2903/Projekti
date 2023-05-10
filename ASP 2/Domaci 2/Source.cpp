#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <cmath>

using namespace std;

struct akcija
{
	int prioritet;
	string naziv;
	friend bool operator <=( const akcija& a1, const akcija& a2)
	{
		if (a1.prioritet < a2.prioritet) return true;
		else if (a1.prioritet == a2.prioritet && a1.naziv <= a2.naziv) return true;
		return false;
	}
	friend bool operator >(const akcija& a1, const akcija&a2)
	{
		if (a1 <= a2) return false;
		return true;
	}
	akcija() = default;
	akcija(int a, string s) :prioritet(a), naziv(s){}
	akcija(const akcija& a)
	{
		prioritet = a.prioritet;
		naziv = a.naziv;
	}
};

struct cvor
{
	cvor*parent;
	cvor*pok[4];
	akcija*key[4];
	int number;
	cvor()
	{
		parent = nullptr;
		for (int i = 0; i < 4; i++)
		{
			pok[i] = nullptr;
			key[i] = nullptr;
		}
		number = 0;
	}
};

struct bcvor
{
	akcija key;
	bcvor*left;
	bcvor*right;
	char boja;
	bcvor(akcija a, char c, bcvor*l = nullptr, bcvor*r = nullptr) : key(a), boja(c), left(l), right(r){}
};

struct zavisinu
{
	bcvor*node;
	int k;
	zavisinu(bcvor*nod,int p):node(nod),k(p){}
};

struct zapisanje
{
	cvor*root1;
	zapisanje(cvor*p): root1(p){}
	friend ostream& operator <<(ostream&os, zapisanje z)
	{
		cvor*root = z.root1;
		int visina = 0;
		cvor*p = root;
		while (p->pok[1])
		{
			p = p->pok[1];
			visina++;
		}
		cvor**niz = new cvor*[(pow(4, visina + 1) - 1) / 3];
		niz[0] = root;
		for (int i = 0; i <= (pow(4, visina) - 1) / 3 - 1; i++)
		{
			niz[4 * i + 1] = (niz[i] ? niz[i]->pok[0] : NULL);
			//if (niz[4 * i + 1]) cout << 4 * i + 1 << endl;
			niz[4 * i + 2] = (niz[i] ? niz[i]->pok[1] : NULL);
			//if (niz[4 * i + 2]) cout << 4 * i + 2 << endl;
			niz[4 * i + 3] = (niz[i] ? niz[i]->pok[2] : NULL);
			//if (niz[4 * i + 3]) cout << 4 * i + 3 << endl;
			niz[4 * i + 4] = (niz[i] ? niz[i]->pok[3] : NULL);
			//if (niz[4 * i + 4]) cout << 4 * i + 4 << endl;

		}
		int duzina = 8 * pow(4, visina);
		int br = 0;
		for (int i = 0; i <= visina; i++)
		{
			int cvorduz = duzina / pow(4, i);
			int spacepre = (cvorduz - 7) / 2;
			int spacepos = spacepre + 1;
			for (int j = 0; j < pow(4, i); j++)
			{
				for (int k = 0; k < spacepre; k++)
					os << " ";
				os << "|";
				if (niz[br] && niz[br]->key[1])
					os << niz[br]->key[1]->prioritet;
				else os << "/";
				os << ",";
				if (niz[br] && niz[br]->key[2])
					os << niz[br]->key[2]->prioritet;
				else cout << "/";
				os << ",";
				if (niz[br] && niz[br]->key[3])
					os << niz[br]->key[3]->prioritet;
				else os << "/";
				os << "|";
				br++;
				for (int k = 0; k < spacepos; k++)
					os << " ";
			}
			os << endl;
		}
		return os;
	}
};

struct pomoc {
	cvor*p;
	int k;
	pomoc(cvor*pok, int k1) :p(pok), k(k1) {}
};

cvor*prazno()
{
	return new cvor();
}

cvor*insert(cvor*root, akcija a)
{
	cvor*p = root;
	while (p->pok[1])
	{
		if (p->number == 1)
		{
			if (a <= *(p->key[2]))
				p = p->pok[1];
			else p = p->pok[2];
		}
		else if (p->number == 2)
		{
			if (p->key[1])
			{
				if (a > *(p->key[2]))
					p = p->pok[2];
				else if (a <= *(p->key[1]))
					p = p->pok[0];
				else
					p = p->pok[1];
			}
			else
			{
				if (a <= *(p->key[2]))
					p = p->pok[1];
				else if (a > *(p->key[3]))
					p = p->pok[3];
				else
					p = p->pok[2];
			}
		}
		else
		{
			if (a <= *(p->key[1]))
				p = p->pok[0];
			else if (a > *(p->key[3]))
				p = p->pok[3];
			else if (a > *(p->key[2]))
				p = p->pok[2];
			else p = p->pok[1];				
		}
	}
	if (p->number == 0)
	{
		p->key[2] = new akcija(a);
		p->number = 1;
		return p;
	}
	else if (p->number == 1)
	{
		if (a <= *(p->key[2]))
			p->key[1] = new akcija(a);
		else
			p->key[3] = new akcija(a);
		p->number = 2;
	}
	else if (p->number == 2)
	{
		if (p->key[1])
		{
			if (a > *(p->key[2]))
				p->key[3] = new akcija(a);
			else if (a <= *(p->key[1]))
			{
				p->key[3] = p->key[2];
				p->key[2] = p->key[1];
				p->key[1] = new akcija(a);
			}
			else
			{
				p->key[3] = p->key[2];
				p->key[2] = new akcija(a);
			}
		}
		else
		{
			if (a <= *(p->key[2]))
				p->key[1] = new akcija(a);
			else if (a > *(p->key[3]))
			{
				p->key[1] = p->key[2];
				p->key[2] = p->key[3];
				p->key[3] = new akcija(a);
			}
			else
			{
				p->key[1] = p->key[2];
				p->key[2] = new akcija(a);
			}
		}
		p->number = 3;
	}
	else
	{
		akcija* umetni = new akcija(a);
		cvor*next = p;
		cvor*left = nullptr;
		cvor*right = nullptr;
		while (true)
		{
			//cout << "a";
			cvor*novi = new cvor();
			novi->parent = p->parent;
			akcija* pom = p->key[2];
			if (*umetni <= *(p->key[2]))
			{
				novi->key[2] = p->key[3];
				novi->pok[1] = p->pok[2];
				if (novi->pok[1]) novi->pok[1]->parent = novi;
				novi->pok[2] = p->pok[3];
				if (novi->pok[2]) novi->pok[2]->parent = novi;
				p->key[3] = nullptr;
				p->pok[3] = nullptr;
				p -> pok[2] = nullptr;
				if (*umetni > *(p->key[1]))
				{
					p->pok[1] = p->pok[0];
					p->pok[0] = nullptr;
					p->pok[2] = left;
					p->key[3] = umetni;
					p->pok[3] = right;
					p->key[2] = p->key[1];
					p->key[1] = nullptr;
				}
				else
				{
					p->pok[2] = p->pok[1];
					p->pok[0] = left;
					p->key[2] = p->key[1];;
					p->key[1] = umetni;
					p->pok[1] = right;
				}
				p->number = 2;
				novi->number = 1;
			}
			else
			{
				if (*umetni > *(p->key[3]))
				{
					novi->pok[3] = right;
					if (novi->pok[3]) novi->pok[3]->parent = novi;
					novi->pok[2] = left;
					if (novi->pok[2]) novi->pok[2]->parent = novi;
					novi->key[3] = umetni;
					novi->key[2] = p->key[3];
					novi->pok[1] = p->pok[2];
					if (novi->pok[1]) novi->pok[1]->parent = novi;
				}
				else
				{
					novi->pok[0] = left;
					if (novi->pok[0]) novi->pok[0]->parent = novi;
					novi->key[1] = umetni;
					novi->pok[1] = right;
					if (novi->pok[1]) novi->pok[1]->parent = novi;
					novi->key[2] = p->key[3];
					novi->pok[2] = p->pok[3];
					if (novi->pok[2]) novi->pok[2]->parent = novi;
				}
				p->key[2] = p->key[1];
				p->pok[2] = p->pok[1];
				p->pok[1] = p->pok[0];
				p->key[1] = nullptr;
				p->pok[0] = nullptr;
				p->key[3] = nullptr;
				p->pok[3] = nullptr;
				p->number = 1;
				novi->number = 2;
			}
			umetni = pom;
			left = p;
			right = novi;
			p = p->parent;
			if (!p)
			{
				cvor*koren = new cvor();
				koren->key[2] = umetni;
				koren->pok[1] = left;
				koren->pok[2] = right;
				left->parent = koren;
				right->parent = koren;
				koren->number = 1;
				root = koren;
				break;
			}
			if (p->number != 3)
			{
			  if (p->number == 1)
			  {
				  if (*umetni <= *(p->key[2]))
				  {
					  p->key[1] = umetni;
					  p->pok[0] = left;
					  p->pok[1] = right;
				  }
				else
				  {
					  p->key[3] = umetni;
					  p->pok[3] = right;
					  p->pok[2] = left;
				  }
				p->number = 2;
				break;
			  }
			  else
			  {
				  if (p->key[1])
				  {
					  if (a > *(p->key[2]))
					  {
						  p->key[3] = umetni;
						  p->pok[3] = right;
						  p->pok[2] = left;
					  }
					  else if (a <= *(p->key[1]))
					  {
						  p->key[3] = p->key[2];
						  p->pok[3] = p->pok[2];
						  p->pok[2] = p->pok[1];
						  p->key[2] = p->key[1];
						  p->pok[1] = right;
						  p->key[1] = umetni;
						  p->pok[0] = left;
					  }
					  else
					  {
						  p->key[3] = p->key[2];
						  p->pok[3]=p->pok[2];
						  p->pok[2] = right;
						  p->pok[1] = left;
						  p->key[2] = umetni;
					  }
				  }
				  else
				  {
					  if (a <= *(p->key[2]))
					  {
						  p->key[1] = umetni;
						  p->pok[1] = right;
						  p->pok[0] = left;
					  }
					  else if (a > *(p->key[3]))
					  {
						  p->key[1] = p->key[2];
						  p->pok[0] = p->pok[1];
						  p->pok[1] = p->pok[2];
						  p->key[2] = p->key[3];
						  p->pok[2] = left;
						  p->key[3] = umetni;
						  p->pok[3] = right;
					  }
					  else
					  {
						  p->pok[0] = p->pok[1];
						  p->key[1] = p->key[2];
						  p->pok[1] = left;
						  p->pok[2] = right;
						  p->key[2] = umetni;
					  }
				  }
				  p->number = 3;
				  break;
			  }
			  break;
			}
			continue;
		}
			
	}
	return root;
}

void brisistablo(cvor*root)
{
	int visina = 0;
	cvor*p = root;
	while (p->pok[1])
	{
		visina++;
		p = p->pok[1];
	}
	cvor**niz = new cvor*[(pow(4, visina + 1) - 1) / 3];
	niz[0] = root;
	int br = 1;
	while (br > 0)
	{
		p = niz[br - 1];
		br--;
		if (p->key[1])delete p->key[1];
		if (p->key[2])delete p->key[2];
		if (p->key[3])delete p->key[3];
		if (p->pok[0]) niz[br++] = p->pok[0];
		if (p->pok[1]) niz[br++] = p->pok[1];
		if (p->pok[2]) niz[br++] = p->pok[2];
		if (p->pok[3]) niz[br++] = p->pok[3];
		delete p;
	}
}

pomoc*prethodnik(cvor*node, int k)
{
	cvor*next = node;
	int ind = k;
	if (node->pok[1])
	{
		if (k == 1) next = next->pok[0];
		else if (k == 2) next = next->pok[1];
		else next = next->pok[2];
		while (next->pok[1])
		{
			if (next->key[3]) next = next->pok[3];
			else next = next->pok[2];
		}
		if (next->key[3]) return new pomoc(next, 3);
		else return new pomoc(next, 2);
	}
	else
	{
		if (k == 3 && next->key[2]) return new pomoc(next, 2);
		else if (k == 2 && next->key[1]) return new pomoc(next, 1);
		else
		{
			while (true)
			{
				if (next->parent == nullptr) return nullptr;
				//cout << "a";
				cvor*rod = next->parent;
				if (rod->pok[3] && rod->pok[3] == next) return new pomoc(rod, 3);
				else if (rod->pok[2] == next) return new pomoc(rod, 2);
				else if (rod->pok[0] && rod->pok[1] == next) return new pomoc(rod, 1);
				next = rod;
			}
		}
	}

}

pomoc*trazi(cvor*root, int prior)
{
	if (!root) return nullptr;
	pomoc*pom = nullptr;;
	cvor*p = root;
	while (p)
	{
		if (p->number == 1)
		{
			if (p->key[2]->prioritet == prior)
			{
				pom = new pomoc(p, 2);
				break;
			}
			else if (p->key[2]->prioritet > prior)
				p = p->pok[1];
			else p = p->pok[2];
		}
		else if (p->number == 2)
		{
			if (p->key[1])
			{
				if (p->key[1]->prioritet>prior)
					p = p->pok[0];
				else if (p->key[2]->prioritet < prior)
					p = p->pok[2];
				else if (p->key[2]->prioritet == prior)
				{
					pom = new pomoc(p, 2);
					break;
				}
				else if (p->key[1]->prioritet == prior)
				{
					pom = new pomoc(p, 1);
					break;
				}
				else
					p = p->pok[1];
			}
			else
			{
				if (p->key[3]->prioritet < prior)
					p = p->pok[3];
				else if (p->key[2]->prioritet > prior)
					p = p->pok[1];
				else if (p->key[2]->prioritet == prior)
				{
					pom = new pomoc(p, 2);
					break;
				}
				else if (p->key[3]->prioritet == prior)
				{
					pom = new pomoc(p, 3);
					break;
				}
				else
					p = p->pok[2];
			}
		}
		else
		{
			int i;
			for (i = 1; p->key[i]->prioritet < prior && i < 3; i++);
				if (p->key[i]->prioritet == prior)
				{
					pom = new pomoc(p, i);
					break;
				}
				else if (p->key[i]->prioritet > prior)
					p = p->pok[i - 1];
				else
					p = p->pok[i];
		}

	}
	if (!pom) return nullptr;
	//return pom;
	while (true)
	{
		pomoc*pred = nullptr;
		pred = prethodnik(pom->p, pom->k);
		if (!pred) return pom;
		int w = pred->p->key[pred->k]->prioritet;
		if (w != prior) return pom;
	    pom = pred;
	}
}

cvor*brisi(cvor*root, pomoc*pom)
{
	if (!root) return nullptr;
    //pomoc*pom = trazi(root, prior);
	if (!pom)
		cout << "Nema akcije sa tim prioritetom" <<endl;
	else
	{
		cvor*p = pom->p;
		cvor*q = pom->p;
		int poz = pom->k;
		int ind;
		if (p->pok[1])
		{
			p = p->pok[poz];
			while (p->pok[1])
			{
				if (p->key[1])
					p = p->pok[0];
				else
					p = p->pok[1];
			}
			if (p->key[1])
			{
				akcija*help;
				help = p->key[1];
				p->key[1]=q->key[poz];
				q->key[poz] = help;
				ind = 1;
			}
			else
			{
				akcija*help;
				help = p->key[2];
				p->key[2] = q->key[poz];
				q->key[poz] = help;
				ind = 2;
			}
		}
		else ind = poz;
		//cvor*rod = p->parent;
		if (ind == 1)
		{
			delete p->key[1];
			p->key[1] = nullptr;
			p->number = p->number - 1;
		}
		else if (ind == 3)
		{
			delete p->key[3];
			p->key[3] = nullptr;
			p->number = p->number - 1;
		}
		else
		{
			if (p->key[3])
			{
				p->key[2] = p->key[3];
				p->key[3] = nullptr;
				p->pok[3] = nullptr;
				p->number = p->number - 1;
			}
			else if (p->key[1])
			{
				p->key[2] = p->key[1];
				p->key[1] = nullptr;
				p->pok[0] = nullptr;
				p->number = p->number - 1;
			}
			else
			{
				cvor*rod = p->parent;
				while (true)
				{
					if (!rod)
					{
						if (p->pok[1])
						{
							root = p->pok[1];
							root->parent = nullptr;
							return root;
						}
						else
						{
							if (p->pok[2])
							{
								root = p->pok[2];
								root->parent = nullptr;
								return root;
							}
							return nullptr;
						}
						
					}
					if (rod->pok[0] == p)
					{
						q = rod->pok[1];
						if (q->number == 2)
						{
							if (q->key[1])
							{
								if (!p->pok[1])
									p->pok[1] = p->pok[2];
								p->key[2] = rod->key[1];
								rod->key[1] = q->key[1];
								p->pok[2] = q->pok[0];
								q->key[1] = nullptr;
								q->pok[0] = nullptr;
								if(p->pok[2]) p->pok[2]->parent = p;
							}
							else
							{
								if (!p->pok[1])
									p->pok[1] = p->pok[2];
								p->pok[2] = q->pok[1];
								if (p->pok[2]) p->pok[2]->parent = p;
								p->key[2] = rod->key[1];
								rod->key[1] = q->key[2];
								q->key[2] = q->key[3];
								q->pok[1] = q->pok[2];
								q->pok[2] = q->pok[3];
								q->key[3] = nullptr;
								q->pok[3] = nullptr;
							}
							q->number = 1;
						}
						else if (q->number == 3)
						{
							q->number = 1;
							p->number = 2;
							if (!p->pok[1])
								p->pok[1] = p->pok[2];
							p->key[2] = rod->key[1];
							p->key[3] = q->key[1];
							p->pok[2] = q->pok[0];
							if (p->pok[2]) p->pok[2]->parent = p;
							p->pok[3] = q->pok[1]; 
							if (p->pok[3]) p->pok[3]->parent = p;
							q->pok[1] = q->pok[2];
							q->pok[2] = q->pok[3];
							rod->key[1] = q->key[2];
							q->key[2] = nullptr;
							q->key[1] = nullptr;
							q->key[2] = q->key[3];
							q->key[3] = nullptr;
							q->pok[0] = nullptr;
							q->pok[3] = nullptr;
						}
						else if (q->number == 1)
						{		
							rod->pok[0] = nullptr;
							q->number = 2;
							q->key[3] = q->key[2];
							q->pok[3] = q->pok[2];
							q->pok[2] = q->pok[1];
							q->key[2] = rod->key[1];
							rod->key[1] = nullptr;
							if (!p->pok[1])
							{
								q->pok[1] = p->pok[2];
								if (q->pok[1])
									q->pok[1]->parent = q;
							}
							else
							{
								q->pok[1] = p->pok[1];
								if (q->pok[1])
									q->pok[1]->parent = q;
							}
							rod->number = rod->number - 1;
						}
						break;
					}
					else if (rod->pok[3] == p)
					{
						q = rod->pok[2];
						if (q->number == 2)
						{
							if (q->key[3])
							{
								if (!p->pok[2])
									p->pok[2] = p->pok[1];
								p->key[2] = rod->key[3];
								rod->key[3] = q->key[3];
								p->pok[1] = q->pok[3];
								q->key[3] = nullptr;
								q->pok[3] = nullptr;
								if (p->pok[1]) p->pok[1]->parent = p;
							}
							else
							{
								if (!p->pok[2])
									p->pok[2] = p->pok[1];
								p->pok[1] = q->pok[2];
								if (p->pok[1]) p->pok[1]->parent = p;
								p->key[2] = rod->key[3];
								rod->key[3] = q->key[2];
								q->key[2] = q->key[1];
								q->pok[2] = q->pok[1];
								q->pok[1] = q->pok[0];
								q->key[1] = nullptr;
								q->pok[0] = nullptr;

							}
							q->number = 1;
						}
						else if (q->number == 3)
						{
							q->number = 1;
							p->number = 2;
							if (!p->pok[2])
								p->pok[2] = p->pok[1];
							p->key[2] = rod->key[3];
							p->key[1] = q->key[3];
							p->pok[1] = q->pok[3];
							if (p->pok[1]) p->pok[1]->parent = p;
							p->pok[0] = q->pok[2];
							if (p->pok[0]) p->pok[0]->parent = p;
							q->pok[2] = q->pok[1];
							q->pok[1] = q->pok[0];
							rod->key[3] = q->key[2];
							q->key[2] = nullptr;
							q->key[1] = nullptr;
							q->key[2] = q->key[1];
							q->key[3] = nullptr;
							q->pok[0] = nullptr;
							q->pok[3] = nullptr;
							
						}
						else if (q->number == 1)
						{
							rod->pok[3] = nullptr;
							q->number = 2;
							q->key[1] = q->key[2];
							q->pok[0] = q->pok[1];
							q->pok[1] = q->pok[2];
							q->key[2] = rod->key[3];
							rod->key[3] = nullptr;
							if (!p->pok[1])
							{
								q->pok[2] = p->pok[2];
								if (q->pok[2])
									q->pok[2]->parent = q;
							}
							else
							{
								q->pok[2] = p->pok[1];
								if (q->pok[2])
									q->pok[2]->parent = q;
							}
							rod->number = rod->number - 1;		
						}
						break;
					}
					else if (rod->pok[1] == p)
					{
						q = rod->pok[0];
						if (q)
						{
							if (q->number == 2)
							{
								if (q->key[3])
								{
									if (!p->pok[2])
										p->pok[2] = p->pok[1];
									p->key[2] = rod->key[1];
									rod->key[1] = q->key[3];
									p->pok[1] = q->pok[3];
									q->key[3] = nullptr;
									q->pok[3] = nullptr;
									if (p->pok[1]) p->pok[1]->parent = p;
								}
								else
								{
									if (!p->pok[2])
										p->pok[2] = p->pok[1];
									p->pok[1] = q->pok[2];
									if (p->pok[1]) p->pok[1]->parent = p;
									p->key[2] = rod->key[1];
									rod->key[1] = q->key[2];
									q->key[2] = q->key[1];
									q->pok[2] = q->pok[1];
									q->pok[1] = q->pok[0];
									q->key[1] = nullptr;
									q->pok[0] = nullptr;

								}
								q->number = 1;
							}
							else if (q->number == 3)
							{
								q->number = 1;
								p->number = 2;
								if (!p->pok[2])
									p->pok[2] = p->pok[1];
								p->key[2] = rod->key[1];
								p->key[1] = q->key[3];
								p->pok[1] = q->pok[3];
								if (p->pok[1]) p->pok[1]->parent = p;
								p->pok[0] = q->pok[2];
								if (p->pok[0]) p->pok[0]->parent = p;
								q->pok[2] = q->pok[1];
								q->pok[1] = q->pok[0];
								rod->key[1] = q->key[2];
								q->key[2] = nullptr;
								q->key[1] = nullptr;
								q->key[2] = q->key[1];
								q->key[3] = nullptr;
								q->pok[0] = nullptr;
								q->pok[3] = nullptr;

							}
							else if (q->number == 1)
							{
								if (!p->pok[2])
									p->pok[2] = p->pok[1];
								rod->pok[0] = nullptr;
								p->number = 2;
								p->key[2] = rod->key[1];
								p->pok[1] = q->pok[2];
								if (p->pok[1]) p->pok[1]->parent = p;
								p->pok[0] = q->pok[1];
								if (p->pok[0]) p->pok[0]->parent = p;
								p->key[1] = q->key[2];
								rod->key[1] = nullptr;
								rod->number = rod->number - 1;
							}
							break;
						}
						else
						{
							q = rod->pok[2];
							if (q->number == 2)
							{
								if (q->key[1])
								{
									if (!p->pok[1])
										p->pok[1] = p->pok[2];
									p->key[2] = rod->key[2];
									rod->key[2] = q->key[1];
									p->pok[2] = q->pok[0];
									q->key[1] = nullptr;
									q->pok[0] = nullptr;
									if (p->pok[2]) p->pok[2]->parent = p;
								}
								else
								{
									if (!p->pok[1])
										p->pok[1] = p->pok[2];
									p->pok[2] = q->pok[1];
									if (p->pok[2]) p->pok[2]->parent = p;
									p->key[2] = rod->key[2];
									rod->key[2] = q->key[2];
									q->key[2] = q->key[3];
									q->pok[1] = q->pok[2];
									q->pok[2] = q->pok[3];
									q->key[3] = nullptr;
									q->pok[3] = nullptr;
								}
								if (rod->number == 2)
								{
									rod->pok[0] = rod->pok[1];
									rod->key[1] = rod->key[2];
									rod->pok[1] = rod->pok[2];
									rod->key[2] = rod->key[3];
									rod->pok[2] = rod->pok[3];
									rod->key[3] = nullptr;
									rod->pok[3] = nullptr;
								}
								q->number = 1;
								break;
							}
							else if (q->number == 3)
							{
								q->number = 1;
								p->number = 2;
								if (!p->pok[1])
									p->pok[1] = p->pok[2];
								p->key[2] = rod->key[2];
								p->key[3] = q->key[1];
								p->pok[2] = q->pok[0];
								if (p->pok[2]) p->pok[2]->parent = p;
								p->pok[3] = q->pok[1];
								if (p->pok[3]) p->pok[3]->parent = p;
								q->pok[1] = q->pok[2];
								q->pok[2] = q->pok[3];
								rod->key[2] = q->key[2];
								q->key[2] = nullptr;
								q->key[1] = nullptr;
								q->key[2] = q->key[3];
								q->key[3] = nullptr;
								q->pok[0] = nullptr;
								q->pok[3] = nullptr;
								if (rod->number == 2)
								{
									rod->pok[0] = rod->pok[1];
									rod->key[1] = rod->key[2];
									rod->pok[1] = rod->pok[2];
									rod->key[2] = rod->key[3];
									rod->pok[2] = rod->pok[3];
									rod->key[3] = nullptr;
									rod->pok[3] = nullptr;
								}
								q->number = 1;
								break;
							}
							else if (q->number == 1)
							{
								if (!p->pok[1])
									p->pok[1] = p->pok[2];
								rod->pok[2] = nullptr;
								p->number = 2;
								p->key[2] = rod->key[2];
								p->pok[2] = q->pok[1];
								if (p->pok[2]) p->pok[2]->parent = p;
								p->pok[3] = q->pok[2];
								if (p->pok[3]) p->pok[3]->parent = p;
								p->key[3] = q->key[2];
								rod->key[2] = nullptr;											
								if (rod->number == 2)
								{
									rod->key[2] = rod->key[3];
									rod->pok[2] = rod->pok[3];
									rod->pok[3] = nullptr;
									rod->key[3] = nullptr;
									rod->number = rod->number - 1;
									break;
								}
								else if (rod->number == 1)
								{
									p = rod;
									rod = p->parent;

									continue;
								}
							}
						}
					}
					else if (rod->pok[2] == p)
					{
					q = rod->pok[3];
					if (q)
					{
						if (q->number == 2)
						{
							if (q->key[1])
							{
								if (!p->pok[1])
									p->pok[1] = p->pok[2];
								p->key[2] = rod->key[3];
								rod->key[3] = q->key[1];
								p->pok[2] = q->pok[0];
								q->key[1] = nullptr;
								q->pok[0] = nullptr;
								if (p->pok[2]) p->pok[2]->parent = p;
							}
							else
							{
								if (!p->pok[1])
									p->pok[1] = p->pok[2];
								p->pok[2] = q->pok[1];
								if (p->pok[2]) p->pok[2]->parent = p;
								p->key[2] = rod->key[3];
								rod->key[3] = q->key[2];
								q->key[2] = q->key[3];
								q->pok[1] = q->pok[2];
								q->pok[2] = q->pok[3];
								q->key[3] = nullptr;
								q->pok[3] = nullptr;

							}

							q->number = 1;
						}
						else if (q->number == 3)
						{
							q->number = 1;
							p->number = 2;
							if (!p->pok[1])
								p->pok[1] = p->pok[2];
							p->key[2] = rod->key[3];
							p->key[3] = q->key[1];
							p->pok[2] = q->pok[0];
							if (p->pok[2]) p->pok[2]->parent = p;
							p->pok[3] = q->pok[1];
							if (p->pok[3]) p->pok[3]->parent = p;
							q->pok[1] = q->pok[2];
							q->pok[2] = q->pok[3];
							rod->key[3] = q->key[2];
							q->key[2] = nullptr;
							q->key[1] = nullptr;
							q->key[2] = q->key[3];
							q->key[3] = nullptr;
							q->pok[0] = nullptr;
							q->pok[3] = nullptr;

						}
						else if (q->number == 1)
						{
							if (!p->pok[1])
								p->pok[1] = p->pok[2];
							rod->pok[3] = nullptr;
							p->number = 2;
							p->key[3] = q->key[2];
							p->pok[3] = q->pok[2];
							p->pok[2] = q->pok[1];
							if (p->pok[2]) p->pok[2]->parent = p;
							if (p->pok[3]) p->pok[3]->parent = p;
							q->key[2] = rod->key[3];
							rod->key[3] = nullptr;
							rod->number = rod->number - 1;
						}
						break;
					}
					else
					{
						q = rod->pok[1];//krenuti
						if (q->number == 2)
						{
							if (q->key[3])
							{
								if (!p->pok[2])
									p->pok[2] = p->pok[1];
								p->key[2] = rod->key[2];
								rod->key[2] = q->key[3];
								p->pok[1] = q->pok[3];
								q->key[3] = nullptr;
								q->pok[3] = nullptr;
								if (p->pok[1]) p->pok[1]->parent = p;
							}
							else
							{
								if (!p->pok[2])
									p->pok[2] = p->pok[1];
								p->pok[2] = q->pok[2];
								if (p->pok[2]) p->pok[2]->parent = p;
								p->key[2] = rod->key[2];
								rod->key[2] = q->key[2];
								q->key[2] = q->key[1];
								q->pok[1] = q->pok[0];
								q->pok[1] = q->pok[1];
								q->key[1] = nullptr;
								q->pok[0] = nullptr;
							}
							if (rod->number == 2)
							{
								rod->pok[3] = rod->pok[2];
								rod->key[3] = rod->key[2];
								rod->pok[2] = rod->pok[1];
								rod->key[2] = rod->key[1];
								rod->pok[1] = rod->pok[0];
								rod->key[1] = nullptr;
								rod->pok[0] = nullptr;
							}
							q->number = 1;
							break;
						}
						else if (q->number == 3)
						{
							q->number = 1;
							p->number = 2;
							if (!p->pok[2])
								p->pok[2] = p->pok[1];
							p->key[2] = rod->key[2];
							p->key[1] = q->key[3];
							p->pok[1] = q->pok[3];
							if (p->pok[1]) p->pok[1]->parent = p;
							p->pok[0] = q->pok[2];
							if (p->pok[0]) p->pok[0]->parent = p;
							q->pok[2] = q->pok[1];
							q->pok[1] = q->pok[0];
							rod->key[2] = q->key[2];
							q->key[2] = nullptr;
							q->key[1] = nullptr;
							q->key[2] = q->key[1];
							q->key[3] = nullptr;
							q->pok[0] = nullptr;
							q->pok[3] = nullptr;
							if (rod->number == 2)
							{
								rod->pok[3] = rod->pok[2];
								rod->key[3] = rod->key[2];
								rod->pok[2] = rod->pok[1];
								rod->key[2] = rod->key[1];
								rod->pok[1] = rod->pok[0];
								rod->key[1] = nullptr;
								rod->pok[0] = nullptr;
							}
							break;
						}
						else if (q->number == 1)
						{
							if (!p->pok[2])
								p->pok[2] = p->pok[1];
							rod->pok[1] = nullptr;
							p->number = 2;
							p->key[2] = rod->key[2];
							p->pok[1] = q->pok[2];
							if (p->pok[1]) p->pok[1]->parent = p;
							p->pok[0] = q->pok[1];
							if (p->pok[0]) p->pok[0]->parent = p;
							p->key[1] = q->key[2];
							rod->key[2] = nullptr;
							if (rod->number == 2)
							{
								rod->key[2] = rod->key[1];
								rod->pok[2] = rod->pok[0];
								rod->pok[0] = nullptr;
								rod->key[1] = nullptr;
								rod->number = rod->number - 1;
								break;
							}
							else if (rod->number == 1)
							{
								p = rod;
								rod = p->parent;
								continue;
							}
						}
					}
				}

					
					
				}

			}
		}
	}
		return root;
}

pomoc*sledbenik(cvor*node,int k)
{
	if (!node) return nullptr;
	cvor*next = node;
	int ind = k;
	if (node->pok[1])
	{
		if (k == 1) next = next->pok[1];
		else if (k == 2) next = next->pok[2];
		else next = next->pok[3];
		while (next->pok[1])
		{
			if (next->key[1]) next = next->pok[0];
			else next = next->pok[1];
		}
		if (next->key[3]) return new pomoc(next, 3);
		else return new pomoc(next, 2);
	}
	else
	{
		if (k == 1 && next->key[2]) return new pomoc(next, 2);
		else if (k == 2 && next->key[3]) return new pomoc(next, 3);
		else
		{
			while (true)
			{
				if (!next->parent) return nullptr;
				cvor*rod = next->parent;
				if (rod->pok[0] && rod->pok[0] == next) return new pomoc(rod, 1);
				else if (rod->pok[1] == next) return new pomoc(rod, 2);
				else if (rod->pok[2] && rod->pok[2] == next) return new pomoc(rod, 3);
				next = rod;		
			}
		}
	}
}

cvor*brisipro(cvor*root, int prior)
{
	pomoc*pom = trazi(root, prior);
	if (pom)
	return brisi(root, pom);
	else return nullptr;
}

cvor*brisimax(cvor*root)
{
	if (!root) return nullptr;
	cvor*next = root;
	pomoc*pom = nullptr;
	while (next->pok[1])
	{
		if (next->pok[3]) next = next->pok[3];
		else next = next->pok[2];
	}
	if (next->key[3]) pom = new pomoc(next, 3);
	else pom = new pomoc(next, 2);
	return brisi(root, pom);
}

bcvor*umetni(bcvor* root, akcija a, char boja)
{
	bcvor*p = root;
	bcvor*q = nullptr;
	if (!root) return new bcvor(a, 'B');
	while (p)
	{
		q = p;
		if (a <= p->key)
			p = p->left;
		else p = p->right;
	}
	if (a <= q->key)
		q->left = new bcvor(a, boja);
	else
		q->right = new bcvor(a, boja);
	return root;
}

bcvor* binarno(cvor*root)
{
	if (!root) return nullptr;
	int visina = 0;
	cvor*p = root;
	bcvor*koren = nullptr;
	while (p->pok[1])
	{
		p = p->pok[1];
		visina++;
	}
	cvor**niz = new cvor*[(pow(4, visina + 1) - 1) / 3];
	niz[0] = root;
	for (int i = 0; i <= (pow(4, visina) - 1) / 3 - 1; i++)
	{
		niz[4 * i + 1] = (niz[i] ? niz[i]->pok[0] : NULL);
		niz[4 * i + 2] = (niz[i] ? niz[i]->pok[1] : NULL);
		niz[4 * i + 3] = (niz[i] ? niz[i]->pok[2] : NULL);
		niz[4 * i + 4] = (niz[i] ? niz[i]->pok[3] : NULL);
	}
	for (int i = 0; i < (pow(4, visina + 1) - 1) / 3; i++)
	{
		if (niz[i])
		{
			cvor* node = niz[i];
			if (node->key[2])
				koren = umetni(koren, *node->key[2], 'B');
			if (node->key[1])
				koren = umetni(koren, *node->key[1], 'R');
			if (node->key[3])
				koren = umetni(koren, *node->key[3], 'R');
		}
	}
	return koren;
}

int visinabin(bcvor*root)
{
	if (!root) return 0;
	zavisinu**stek =(zavisinu**)malloc(200 * sizeof(zavisinu*));
	int duz = 200;
	int br = 0;
	int max = 0;
	int ind;
	bcvor*next = root;
	stek[0] = new zavisinu(next, 0);
	br = 1;
	while (br > 0)
	{
		zavisinu*pom = stek[br - 1];
		br--;
		next = pom->node;
		ind = pom->k;
		if (ind > max) max = ind;
		if (next->left)
		{
			if (br == duz)
			{
				stek = (zavisinu**)realloc(stek, (duz + 200) * sizeof(zavisinu*));
				duz = duz + 200;
			}
			stek[br] = new zavisinu(next->left, ind + 1);
			br++;
		}
		if (next->right)
		{
			if (br == duz)
			{
				stek = (zavisinu**)realloc(stek, (duz + 200) * sizeof(zavisinu*));
				duz = duz + 200;
			}
			stek[br] = new zavisinu(next->right, ind + 1);
			br++;
		}
	}
	return max;
}

struct zapisanjebin 
{
	bcvor*root1;
	zapisanjebin(bcvor*p) : root1(p) {}
	friend ostream& operator <<(ostream& os, zapisanjebin z)
	{
		bcvor*root = z.root1;
		int visina = visinabin(root);
		bcvor**niz = new bcvor*[(pow(2, visina + 1) - 1)];
		niz[0] = root;
		for (int i = 0; i <= (pow(2, visina) - 1) - 1; i++)
		{
			niz[2 * i + 1] = (niz[i] ? niz[i]->left : NULL);
			niz[2 * i + 2] = (niz[i] ? niz[i]->right : NULL);	
		}
		int duzina = 4 * pow(2, visina);
		int br = 0;
		for (int i = 0; i <= visina; i++)
		{
			int cvorduz = duzina / pow(2, i);
			int spacepre = (cvorduz - 3) / 2;
			int spacepos = spacepre+1;
			//cout << spacepre;
			for (int j = 0; j < pow(2, i); j++)
			{
				for (int k = 0; k < spacepre; k++)
					os << " ";
				if (niz[br])
					os <<niz[br]->key.prioritet<<niz[br]->boja;
				else os << "   ";
				br++;
				for (int k = 0; k < spacepos; k++)
					os << " ";
			}
			os << endl;
		}
		return os;
	}

};

int pronadjiakcije(cvor*root, int prior)
{
	int br = 0;
	pomoc*pom = trazi(root, prior);
	pomoc*pom1 = pom;
	if (!pom) return 0;
	br++;
	while (true)
	{
		pomoc*pred = nullptr;
		pred = prethodnik(pom->p, pom->k);
		if (!pred) break;
		cvor*node = pred->p;
		if (node)
		{
			int w = node->key[pred->k]->prioritet;
			if (w != prior) break;
		}
		pom = pred;
		br++;
	}
	//pomoc*pom1 = trazi(root, prior);
	while (true)
	{
		pomoc*pred = nullptr;
		pred = sledbenik(pom1->p, pom1->k);
		if (!pred) break;
		cvor*node = pred->p;
		if (node)
		{
			int w = node->key[pred->k]->prioritet;
			if (w != prior) break;
		}
		pom1 = pred;
		br++;
	}
	return br;
}

pomoc* poimenu(cvor*root, string ime)
{
	int visina = 0;
	cvor*p = root;
	while (p->pok[1])
	{
		p = p->pok[1];
		visina++;
	}
	cvor**niz = new cvor*[(pow(4, visina + 1) - 1) / 3];
	niz[0] = root;
	if (root->key[1] && root->key[1]->naziv == ime) return new pomoc(root, 1);
	if (root->key[2] && root->key[2]->naziv == ime) return new pomoc(root, 2);
	if (root->key[3] && root->key[3]->naziv == ime) return new pomoc(root, 3);

	for (int i = 0; i <= (pow(4, visina) - 1) / 3 - 1; i++)
	{
		niz[4 * i + 1] = (niz[i] ? niz[i]->pok[0] : NULL);
		if (niz[4 * i + 1])
		{
			if (niz[4 * i + 1]->key[1] && niz[4 * i + 1]->key[1]->naziv == ime) return new pomoc(niz[4 * i + 1], 1);
			if (niz[4 * i + 1]->key[2] && niz[4 * i + 1]->key[2]->naziv == ime) return new pomoc(niz[4 * i + 1], 2);
			if (niz[4 * i + 1]->key[3] && niz[4 * i + 1]->key[3]->naziv == ime) return new pomoc(niz[4 * i + 1], 3);
		}
		niz[4 * i + 2] = (niz[i] ? niz[i]->pok[1] : NULL);
		if (niz[4 * i + 2])
		{
			if (niz[4 * i + 2]->key[1] && niz[4 * i + 2]->key[1]->naziv == ime) return new pomoc(niz[4 * i + 2], 1);
			if (niz[4 * i + 2]->key[2] && niz[4 * i + 2]->key[2]->naziv == ime) return new pomoc(niz[4 * i + 2], 2);
			if (niz[4 * i + 2]->key[3] && niz[4 * i + 2]->key[3]->naziv == ime) return new pomoc(niz[4 * i + 2], 3);
		}
		niz[4 * i + 3] = (niz[i] ? niz[i]->pok[2] : NULL);
		if (niz[4 * i + 3])
		{
			if (niz[4 * i + 3]->key[1] && niz[4 * i + 3]->key[1]->naziv == ime) return new pomoc(niz[4 * i + 3], 1);
			if (niz[4 * i + 3]->key[2] && niz[4 * i + 3]->key[2]->naziv == ime) return new pomoc(niz[4 * i + 3], 2);
			if (niz[4 * i + 3]->key[3] && niz[4 * i + 3]->key[3]->naziv == ime) return new pomoc(niz[4 * i + 3], 3);
		}
		niz[4 * i + 4] = (niz[i] ? niz[i]->pok[3] : NULL);
		if (niz[4 * i + 4])
		{
			if (niz[4 * i + 4]->key[1] && niz[4 * i + 4]->key[1]->naziv == ime) return new pomoc(niz[4 * i + 4], 1);
			if (niz[4 * i + 4]->key[2] && niz[4 * i + 4]->key[2]->naziv == ime) return new pomoc(niz[4 * i + 4], 2);
			if (niz[4 * i + 4]->key[3] && niz[4 * i + 4]->key[3]->naziv == ime) return new pomoc(niz[4 * i + 4], 3);
		}

	}
	return nullptr;
}

cvor*izmeni(cvor*root, string ime, int prior)
{
	if (!root) return nullptr;
	pomoc*pom = poimenu(root, ime);
	if (!pom)
	{
		cout << "Nema akcija sa tim imenom" << endl;
		return root;
	}
	root=brisi(root, pom);
	akcija a(prior, ime);
	root = insert(root, a);
	return root;
}

int main()
{
	int broj;
	bool formirano = false;
	cvor*root=nullptr;
	while (true)
	{
		cout << endl;
		cout << "Meni:" << endl;
		cout << "1: Procitaj stablo iz datoteke" << endl;
		cout << "2: Pronadji akciju sa datim prioritetom" << endl;
		cout << "3: Umetni novu akciju" << endl;
		cout << "4: Obrisi akciju sa datim prioritetom" << endl;
		cout << "5: Obrisi akicju sa najvecim prioritetom" << endl;
		cout << "6: Ispisi stablo u 2-3-4 obliku" << endl;
		cout << "7: Ispisi stablo kao crveno-crno" << endl;
		cout << "8: Izmeni prioritet datoj akciji"<< endl;
		cout << "9: Pronadji ukupan broj akcija sa datim prioritetom" << endl;
		cout << "10: Brisi stablo" << endl;
		cout << "11: Zavrsi rad" << endl << endl;
		cout << "Unesite broj koji zelite:";
		cin >> broj; 
		if (broj < 1 || broj>11)
		{
			cout << "Nepostojeca opcija" << endl;
			continue;
		}
		if (broj == 11)
			break;
		if (!formirano && broj != 1 && broj != 3)
		{
			cout << "Nije formirano stablo" << endl;
			continue;
		}
		if (broj == 1)
		{
			if (!root) root = prazno(), formirano=true;
			ifstream dat;
			dat.open("datoteka.txt");
			if (!dat.is_open())
			{
				cout << "DAT_GRESKA" << endl;
				continue;
			}
			while (true)
			{
				string s;
				getline(dat, s);
				int i = s.length();
				if (i == 0) break;
				while (s[i - 1] != ' ') i--;
				string s1, s2;
				s1 = s.substr(0, i - 1);
				s2 = s.substr(i);
				int k=0;
				stringstream con(s2);
				con >> k;
				//cout << k<<endl;
				root = insert(root, akcija(k, s1));
				/*zapisanje z(root);
				cout << endl << z << endl;*/
			}
			dat.close();
			continue;
		}
		if (broj == 2)
		{
			cout << "Unesite prioritet: ";
			int k; cin >> k;
			pomoc*pom = trazi(root, k);
			if (pom)
				cout << pom->p->key[pom->k]->naziv << " " << pom->p->key[pom->k]->prioritet << endl;
			else
				cout << "Nema akcije sa datim prioritetom" << endl;
			continue;
		}
		if (broj == 3)
		{
			if (!root) root = prazno(), formirano = true;
			string s,s3;
			cout << "Unesite ime akcije i prioritet"<<endl;
			cin >> s;
			getline(cin, s3);
			s = s + s3;
			int i = s.length();
			if (i == 0) continue;
			while (s[i - 1] != ' ') i--;
			string s1, s2;
			s1 = s.substr(0, i - 1);
			s2 = s.substr(i);
			int k = 0;
			stringstream con(s2);
			con >> k;
			root = insert(root, akcija(k, s1));
			continue;
		}
		if (broj == 4)
		{
			int pr;
			cout << "Unesite prioritet:";
			cin >> pr;
			root = brisipro(root, pr);
			continue;
		}
		if (broj == 5)
			root = brisimax(root);
		if (broj == 6)
		{
			zapisanje z(root);
			cout << endl << z << endl;
			continue;
		}
		if (broj == 7)
		{
			cout << endl;
			bcvor*bin = binarno(root);
			zapisanjebin z(bin);
			cout << z << endl;
			continue;
		}
		if (broj == 8)
		{
			string s,s3;
			cout << "Unesite ime akcije i novi prioritet" << endl;
			cin >> s;
			getline(cin, s3);
			s = s + s3;
			int i = s.length();
			if (i == 0) continue;
			while (s[i - 1] != ' ') i--;
			string s1, s2;
			s1 = s.substr(0, i - 1);
			s2 = s.substr(i);
			int k = 0;
			stringstream con(s2);
			con >> k;
			root = izmeni(root, s1, k);
			continue;
		}
		if (broj == 9)
		{
			int pr;
			cout << "Unesite prioritet:";
			cin >> pr;
			cout << "Ukupan broj je: " << pronadjiakcije(root, pr)<<endl;
			continue;
		}
		if (broj == 10)
		{
			brisistablo(root);
			root = nullptr;
			formirano = false;
			continue;
		}
		
	}

	/*  akka 12
		djfufrn 54
		kdk ff 37
		jdjie 28
		flokdk 38
		dldl ool ll 45
		ijr 90
		sleor 54
		tom 35
		kekekeke 23
		frifim 34
    */


	/*akka 12
		djfufrn 54
		kdk ff 37
		jdjie 28
		flokdk 38
		dldl ool ll 45
		ijr 90
		sleor 54
		tom 35
		kekekeke 23
		frifim 34
		gt 27
		frj 35
		jfk 55
		fjri 87
		fjnb 24
		ffjjn 15
		fjf 67
		mss 32
		op 45
		gee 31
		fnma 53
		fjkn 72
		fjnr 21
		fjn 83
		fjnfj 79
		fbfhr 44
		fkdk 32
		hdhd 11
*/



	/*cvor*root = prazno();
	akcija a(12, "akka"), b(21, "tfgf"), c(53, "gfat");
	akcija a1(41, "asuza"), b1(83, "tfdf"), c1(34, "gsqft");
	akcija c2(92, "aswwas");
	akcija a11(13, "aeea"), b11(25, "tfwf"), c11(90, "gcft");
	akcija a12(25, "assa"), b12(77, "tfwqf"), c12(90, "gsdft");
	akcija c22(97, "asas");
	root = insert(root, a);
	root = insert(root, c);
	root = insert(root, c1);
	root = insert(root, b);
	root = insert(root, a1);
	root = insert(root, b1);
	root = insert(root, c2);
	root = insert(root, a11);
	root = insert(root, c22);
	root = insert(root, c11);
	root = insert(root, b11);
	root = insert(root, a12);
	root = insert(root, b12);
	root = insert(root, c12);
	//brisipro(root, 83);
	//brisipro(root, 90);
	//brisimax(root);
	root = izmeni(root, "gcft",48);
	/*zapisanje z(root);
	cout << z;*/
	/*bcvor*bin = binarno(root);
	zapisanjebin z(bin);
	cout << z;
	cout << pronadjiakcije(root, 25);*/
	
	/*pomoc *pomo = trazi(root, 77);
	pomoc*pred = sledbenik(pomo->p, pomo->k);
	cout << pred->p->key[pred->k]->prioritet;
	cout << pred->k;*/
}