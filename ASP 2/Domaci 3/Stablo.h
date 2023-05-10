#ifndef _STABLO_H_
#define _STABLO_H_
#include <iostream>
#include <string>
using namespace std;

class Stablo
{
protected:
	int   m_brCvorova;
	int   m_brKljuceva;
	int   m_brReci;
	Stablo()
	{
		m_brKljuceva = 0;
		m_brCvorova = 0;
		m_brReci = 0;
	}
public:

	virtual ~Stablo() { }
	virtual void umetni(string s) = 0;
	virtual void obrisi(string s) = 0;
	//virtual char *dohvatiInfo(const char *kljuc) = 0;
	virtual int brojCvorova() { return m_brCvorova; }
	virtual int brojKljuceva() { return m_brKljuceva; }
	virtual int brojReci() { return m_brReci; }
	//virtual void obidji(ostream &it) = 0;
	virtual void ispisiStatistiku(ostream &it)
	{
		it << "Ukupno cvorova: " << brojCvorova() << endl;
		it << "Ukupno kljuceva: " << brojKljuceva() << endl;
		it << "Ukupno razlicitih reci: " << brojReci() << endl;
	}
};

#endif