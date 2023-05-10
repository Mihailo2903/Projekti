#define _CRT_SECURE_NO_WARNINGS
#include "Trie.h"

struct Kandidati
{
	struct Elem
	{
		string rec;
		int broj;
		Elem* next = nullptr;
		Elem(string r, int b) :rec(r), broj(b) {}
	};

	Elem*prvi = nullptr;
	void dodaj(string rec, int broj)
	{
		Elem*novi = new Elem(rec, broj);
		novi->next = prvi;
		prvi = novi;
	}
	void max3(TrieStablo& stablo)
	{
		string* tri = new string[3];
		tri[0] = tri[1] = tri[2] = " ";
		int m1 = -1, m2 = -1, m3 = -1;
		Elem*tek = prvi;
		while (tek)
		{
			if (tek->broj > m1)
			{
				m3 = m2;
				m2 = m1;
				m1 = tek->broj;
				tri[2] = tri[1];
				tri[1] = tri[0];
				tri[0] = tek->rec;
			}
			else if (tek->broj > m2)
			{
				m3 = m2;
				m2 = tek->broj;
				tri[2] = tri[1];
				tri[1] = tek->rec;
			}
			else if (tek->broj > m3)
			{
				m3 = tek->broj;
				tri[2] = tek->rec;
			}
			tek = tek->next;
		}
		if (m1 != -1)
		{
			cout << "1. " << tri[0] << " " << m1 << endl;
			stablo.umetni(tri[0]);
		}
		if (m2 != -1)
		{
			cout << "2. " << tri[1] << " " << m2 << endl;
			stablo.umetni(tri[1]);
		}
		if (m3 != -1)
		{
			cout << "3. " << tri[2] << " " << m3 << endl;
			stablo.umetni(tri[2]);
		}

		if (m1 == -1 && m2 == -1 && m3 == -1)
			cout << "Ne moze se naci nijedna predikcija" << endl;
	}
};

TrieStablo::TrieCvor::TrieCvor(TrieStablo::TrieCvor *roditelj)
	: m_roditelj(roditelj),
	frekvencija(0),
	m_brojPodstabala(0)
{
	for (int i = 0; i < 26; i++)
	{
		m_niz_pokazivaca_mala[i] = nullptr;
		m_niz_pokazivaca_velika[i] = nullptr;
	}
}

TrieStablo::TrieCvor::~TrieCvor()
{
	for (int i = 0; i < 26; i++)
	{
		if (m_niz_pokazivaca_mala[i])
			m_niz_pokazivaca_mala[i]=nullptr;
		if (m_niz_pokazivaca_velika[i])
			 m_niz_pokazivaca_velika[i]=nullptr;
	}
}

TrieStablo::TrieCvor *TrieStablo::TrieCvor::pronadjiPodstablo(char slovo)
{
	if ('a' <= slovo && slovo <= 'z')
		return m_niz_pokazivaca_mala[slovo - 'a'];
	else
		return m_niz_pokazivaca_velika[slovo - 'A'];
}

void TrieStablo::TrieCvor::dodajPodstablo(char slovo, TrieStablo::TrieCvor *cvor)
{
	if ('a' <= slovo && slovo <= 'z')
	{
		if (!m_niz_pokazivaca_mala[slovo - 'a'])
		{
			m_niz_pokazivaca_mala[slovo - 'a'] = cvor;
			m_brojPodstabala++;
		}
	}
	else
	{
		if (!m_niz_pokazivaca_velika[slovo - 'A'])
		{
			m_niz_pokazivaca_velika[slovo - 'A'] = cvor;
			m_brojPodstabala++;
		}
	}
}

void TrieStablo::TrieCvor::ukloniPodstablo(char slovo)
{
	if ('a' <= slovo && slovo <= 'z')
	{
		if (m_niz_pokazivaca_mala[slovo - 'a'])
		{
			m_niz_pokazivaca_mala[slovo - 'a'] = nullptr;
			m_brojPodstabala--;
		}
	}
	else
	{
		if (m_niz_pokazivaca_velika[slovo - 'A'])
		{
			m_niz_pokazivaca_velika[slovo - 'A'] = nullptr;
			m_brojPodstabala--;
		}
	}
	/*TrieCvor*pom = pronadjiPodstablo(slovo);
	if (pom)
	{
		pom = nullptr;
		m_brojPodstabala--;
	}*/
}

void TrieStablo::TrieCvor::postaviFrek(int k)
{
	frekvencija = k;
}

int TrieStablo::TrieCvor::dohvatiFrek()
{
	return frekvencija;
}


/*void TrieStablo::TrieCvor::poseti(ostream &it)
{
	if (m_info)  it << m_info << endl;

	for (int i = 0; i < 255; i++)
		if (m_niz_pokazivaca[i])
			m_niz_pokazivaca[i]->poseti(it);
}*/

//---------------------------------------------------------------

int TrieStablo::brisi()
{
	int count = 0;
	if (!m_koren) return count;
	TrieCvor** stek = nullptr;
	stek =(TrieCvor**)realloc(stek, 100*sizeof(TrieCvor*));
	if (!stek) { printf("Mem_greska"); exit(2); }
	int kap = 100;
	int br = 1;
	stek[0] = m_koren;
	while (br > 0)
	{
		count++;
		br = br - 1;
		TrieCvor*pom = stek[br];
		for (int i = 0; i < 26; i++)
		{
			if (pom->m_niz_pokazivaca_mala[i])
			{
				if (br >= kap)
				{
					stek = (TrieCvor**)realloc(stek, (kap = kap + 100) * sizeof(TrieCvor*));
					if (!stek) { printf("Mem_greska"); exit(2); }
				}
				stek[br++] = pom->m_niz_pokazivaca_mala[i];
			}

			if (pom->m_niz_pokazivaca_velika[i])
			{
				if (br >= kap)
				{
					stek = (TrieCvor**)realloc(stek, (kap = kap + 100) * sizeof(TrieCvor*));
					if (!stek) { printf("Mem_greska"); exit(2); }
				}
				stek[br++] = pom->m_niz_pokazivaca_velika[i];
			}
		}
	 delete pom;
	}
	m_koren = nullptr;
	return count;
}

TrieStablo::TrieCvor *TrieStablo::pronadjiCvor(string kljuc)
{
	if (!m_koren)   return nullptr;
	TrieCvor *tek = m_koren;

	for (int i = 0; i < kljuc.length() && tek; i++)
		tek = tek->pronadjiPodstablo(kljuc[i]);
	return tek;
}

TrieStablo::TrieStablo() : m_koren(nullptr) { }

TrieStablo::~TrieStablo()
{
	brisi();
}

void TrieStablo::umetni(string kljuc)
{
	if (!m_koren)
	{
		m_koren = new TrieCvor(nullptr);
		m_brCvorova++;
	}

	TrieCvor *tek = m_koren;
	TrieCvor *sledeci = nullptr;

	for (int i = 0; i < kljuc.length(); i++)
	{
		sledeci = tek->pronadjiPodstablo(kljuc[i]);
		if (!sledeci)
		{
			sledeci = new TrieCvor(tek);
			tek->dodajPodstablo(kljuc[i], sledeci);
			m_brCvorova++;
		}
		tek = sledeci;
	}
	if (sledeci->dohvatiFrek() == 0)
		m_brReci++;
	sledeci->postaviFrek(sledeci->dohvatiFrek()+1);
	m_brKljuceva++;
}

void TrieStablo::obrisi(string kljuc)
{
	if (!m_koren) return;
	TrieCvor *tek = pronadjiCvor(kljuc);
	if (!tek)
		return;
	int k = tek->dohvatiFrek();
	tek->postaviFrek(0);
	int i = kljuc.length() - 1;
	while (i >= 0 && tek && tek->brojPodstabala() == 0 && tek->dohvatiFrek()==0)
	{
		TrieCvor *roditelj = tek->roditelj();
		delete tek;
		m_brCvorova--;
		if (roditelj)   
			roditelj->ukloniPodstablo(kljuc[i--]);
		else            
			m_koren = nullptr;
		tek = roditelj;
	}
	m_brKljuceva=m_brKljuceva-k;
	m_brReci--;
}

/*void TrieStablo::obidji(ostream &it)
{
	if (m_koren)  m_koren->poseti(it);
	else
		it << "Stablo je prazno." << endl;
}*/

int TrieStablo::dohvatiFrek(string kljuc)
{
	TrieCvor *cvor = pronadjiCvor(kljuc);
	if (cvor) return cvor->dohvatiFrek();
	return 0;
}

string zamene(char slovo)
{
	switch (slovo)
	{
	case 'a': return "qwsz";
	case 'b': return "njhvg";
	case 'c': return "dfgxv";
	case 'd': return "ersfx";
	case 'e': return "wsdr";
	case 'f': return "cgdrt";
	case 'g': return "ftyhv";
	case 'h': return "bgjyu";
	case 'i': return "ujko";
	case 'j': return "kinhu";
	case 'k': return "lojim";
	case 'l': return "kopm";
	case 'm': return "njkl";
	case 'n': return "bmhjk";
	case 'o': return "kilp";
	case 'p': return "lo";
	case 'r': return "etfd";
	case 's': return "wedaz";
	case 't': return "ryfg";
	case 'u': return "yhji";
	case 'v': return "cbfgh";
	case 'w': return "qase";
	case 'q': return "wa";
	case 'x': return "cfdsz";
	case 'y': return "tghu";
	case 'z': return "xdsa";

	case 'A': return "QWSZ";
	case 'B': return "NJHGV";
	case 'C': return "XDFGV";
	case 'D': return "ERSFX";
	case 'E': return "WSDR";
	case 'F': return "CRTDG";
	case 'G': return "FTYHV";
	case 'H': return "BGJUY";
	case 'I': return "UJKO";
	case 'J': return "KINHU";
	case 'K': return "LOJIM";
	case 'L': return "KOPM";
	case 'M': return "NJKL";
	case 'N': return "BMKHJ";
	case 'O': return "KLIP";
	case 'P': return "LO";
	case 'R': return "ETFD";
	case 'S': return "WEDAZ";
	case 'T': return "RYFG";
	case 'U': return "YHIJ";
	case 'V': return "CBFGH";
	case 'W': return "QASE";
	case 'Q': return "WA";
	case 'X': return "CFDSZ";
	case 'Y': return "TGHU";
	case 'Z': return "XDSA";


	default:
		return " ";
	}

}



void TrieStablo::predvidi(string kljuc)
{
	string drugi = kljuc;
	TrieCvor* cvor = pronadjiCvor(kljuc);
	if (cvor)
	{
		if (cvor->dohvatiFrek())
		{
			cout << kljuc << endl;
			this->umetni(kljuc);
			return;
		}

		TrieCvor*pom = cvor;
		while (pom && pom->brojPodstabala() == 1)
		{
			for (int i = 0; i < 26; i++)
			{
				if (pom->m_niz_pokazivaca_mala[i])
				{
					drugi = drugi + char('a' + char(i));
					pom = pom->m_niz_pokazivaca_mala[i];
					continue;
				}
			}

			for (int i = 0; i < 26; i++)
			{
				if (pom->m_niz_pokazivaca_velika[i])
				{
					drugi = drugi + char('A' + char(i));
					pom = pom->m_niz_pokazivaca_velika[i];
					continue;
				}
			}
		}
		if (pom && pom->brojPodstabala()  == 0 && pom->dohvatiFrek())
		{
			cout << drugi << endl;
			this->umetni(drugi);
			return;
		}
	}

	Kandidati kan;
	// duzina reci je 1
	if (kljuc.length() == 1)
	{
		string zam = zamene(kljuc[0]);
		string novi=kljuc;
		for (int i = 0; i < zam.length(); i++)
		{
			novi = zam[i];
			if (pronadjiCvor(novi) && dohvatiFrek(novi) == 0)
			{
				string str = novi; int z = 0;
				probaj(str, z);
				if (z != 0)
					kan.dodaj(str, z);
			}
			if (dohvatiFrek(novi) != 0)
				kan.dodaj(novi, dohvatiFrek(novi));
		}
	}
	//duzina reci je 2
	else if (kljuc.length() == 2)
	{
		// menja 1 slovo
		for (int i = 0; i < kljuc.length(); i++)
		{
			string zam = zamene(kljuc[i]);
			for (int j = 0; j < zam.length(); j++)
			{
				string novi = kljuc;
				novi[i] = zam[j];
				if (pronadjiCvor(novi) && dohvatiFrek(novi) == 0)
				{
					string str = novi; int z = 0;
					probaj(str, z);
					if (z != 0)
						kan.dodaj(str, z);
				}
		
				if (dohvatiFrek(novi) != 0)
					kan.dodaj(novi, dohvatiFrek(novi));
			}
		}
		//menja 2 slova
		for (int i = 0; i < kljuc.length() - 1; i++)
			for (int j = i + 1; j < kljuc.length(); j++)
			{
				string zam1 = zamene(kljuc[i]);
				string zam2 = zamene(kljuc[j]);
				for (int iu = 0; iu < zam1.length(); iu++)
					for (int ju = 0; ju < zam2.length(); ju++)
					{
						string novi = kljuc;
						novi[i] = zam1[iu];
						novi[j] = zam2[ju];
						if (pronadjiCvor(novi) && dohvatiFrek(novi) == 0)
						{
							string str = novi; int z = 0;
							probaj(str, z);
							if (z != 0)
								kan.dodaj(str, z);
						}

						if (dohvatiFrek(novi) != 0)
							kan.dodaj(novi, dohvatiFrek(novi));
					}
			}
	} 
	//duzina reci je 3 ili vise
	else
	{
		// menja 1 slovo
		for (int i = 0; i < kljuc.length(); i++)
		{
			string zam = zamene(kljuc[i]);
			for (int j = 0; j < zam.length(); j++)
			{
				string novi = kljuc;
				novi[i] = zam[j];

				if (pronadjiCvor(novi) && dohvatiFrek(novi) == 0)
				{
					string str = novi; int z = 0;
					probaj(str, z);
					if (z != 0)
						kan.dodaj(str, z);
				}

				if (dohvatiFrek(novi) != 0)
					kan.dodaj(novi, dohvatiFrek(novi));
			}
		}
		//menja 2 slova
		for (int i = 0; i < kljuc.length() - 1; i++)
			for (int j = i + 1; j < kljuc.length(); j++)
			{
				string zam1 = zamene(kljuc[i]);
				string zam2 = zamene(kljuc[j]);
				for (int iu = 0; iu < zam1.length(); iu++)
					for (int ju = 0; ju < zam2.length(); ju++)
					{
						string novi = kljuc;
						novi[i] = zam1[iu];
						novi[j] = zam2[ju];

						if (pronadjiCvor(novi) && dohvatiFrek(novi) == 0)
						{
							string str = novi; int z = 0;
							probaj(str, z);
							if (z != 0)
								kan.dodaj(str, z);
						}

						if (dohvatiFrek(novi) != 0)
							kan.dodaj(novi, dohvatiFrek(novi));
					}
			}
		// menja 3 slova
		for (int i = 0; i < kljuc.length() - 2;i++)
			for(int j = i + 1; j < kljuc.length()-1; j++)
				for (int k = j + 1; k < kljuc.length(); k++)
				{
					string zam1 = zamene(kljuc[i]);
					string zam2 = zamene(kljuc[j]);
					string zam3 = zamene(kljuc[k]);
					for (int iu = 0; iu < zam1.length(); iu++)
						for (int ju = 0; ju < zam2.length(); ju++)
							for (int ku = 0; ku < zam3.length(); ku++)
							{
								string novi = kljuc;
								novi[i] = zam1[iu];
								novi[j] = zam2[ju];
								novi[k] = zam3[ku];

								if (pronadjiCvor(novi) && dohvatiFrek(novi) == 0)
								{
									string str = novi; int z = 0;
									probaj(str, z);
									if (z != 0)
										kan.dodaj(str, z);
								}

								if (dohvatiFrek(novi) != 0)
									kan.dodaj(novi, dohvatiFrek(novi));
							}
				}

	}
	//pise prve tri najverovatnije reci
	kan.max3(*this);
}

void TrieStablo::probaj(string & s, int & broj)
{
	TrieCvor*pom = pronadjiCvor(s);
	while (pom && pom->brojPodstabala() == 1)
	{
		for (int i = 0; i < 26; i++)
		{
			if (pom->m_niz_pokazivaca_mala[i])
			{
				s = s + char('a' + char(i));
				pom = pom->m_niz_pokazivaca_mala[i];
				continue;
			}
		}

		for (int i = 0; i < 26; i++)
		{
			if (pom->m_niz_pokazivaca_velika[i])
			{
				s = s + char('A' + char(i));
				pom = pom->m_niz_pokazivaca_velika[i];
				continue;
			}
		}
	}
	if (pom && pom->brojPodstabala() == 0 && pom->dohvatiFrek())
		broj = pom->dohvatiFrek();
}


