#ifndef _TRIESTABLO_H_
#define _TRIESTABLO_H_
#include <stdlib.h>
#include "Stablo.h"

class TrieStablo : public Stablo
{
protected:
	class TrieCvor
	{
		/*TrieCvor* m_niz_pokazivaca_mala[26];
		TrieCvor* m_niz_pokazivaca_velika[26];*/
		TrieCvor* m_roditelj;
		int	frekvencija;
		int m_brojPodstabala;

		TrieCvor(const TrieCvor &) { }
		TrieCvor &operator=(const TrieCvor &) { }

	public:
		TrieCvor* m_niz_pokazivaca_mala[26];
		TrieCvor* m_niz_pokazivaca_velika[26];
		TrieCvor(TrieCvor *roditelj);
		virtual ~TrieCvor();
		TrieCvor *roditelj()
		{
			return m_roditelj;
		}
		TrieCvor *pronadjiPodstablo(char deoKljuca); //vrati sina u odnosu na slovo
		void dodajPodstablo(char deoKljuca, TrieCvor *cvor);
		void ukloniPodstablo(char deoKljuca);
		int brojPodstabala() const { return m_brojPodstabala; }
		int dohvatiFrek(); //vraca frekvenciju
		void postaviFrek(int k); //postavlja frekvenciju
		//void poseti(ostream &it);
	};

	TrieCvor* m_koren;
	TrieStablo(const TrieStablo &) { }
	TrieStablo &operator=(const TrieStablo &) { }
public:
	int brisi();
	TrieCvor* pronadjiCvor(string kljuc); // pronalazi cvor koji sadrzi dati kljuc
	TrieStablo();
	virtual ~TrieStablo();
	friend string zamene(char slovo);
	virtual void umetni(string kljuc);
	virtual void obrisi(string kljuc);
	//virtual void obidji(ostream &it);
	virtual int dohvatiFrek(string kljuc);
	virtual void predvidi(string kljuc);
	virtual void probaj(string&s, int& broj);

};
#endif 
