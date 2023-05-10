#include <iostream>
#include <fstream>
#include <string>

#include <filesystem>
namespace fs = std::filesystem;

#include "Trie.h"
using namespace std;

bool dobar(string s)
{
	//if (s == "") return false;
	if (s.length()==0) return false;
	for (int i = 0; i < s.length(); i++)
		if (!('a' <= s[i] && s[i]<='z') && !('A' <= s[i] &&  s[i] <= 'Z'))
			return false;
	return true;
	
}

void ucitajdatoteku(TrieStablo& stablo, string naziv){
	ifstream dat;
	dat.open(naziv);
	if (!dat.is_open()) exit(2);
	string s, s1, s2;
	getline(dat, s);
	getline(dat, s);
	getline(dat, s);
	int prvi = -1, drugi = -1;
	int i = 0;
	int duz = s.length();
	int br = 0;
	while (i < duz)
	{
		if (s[i] != ' ')
		{
			i++; continue;
		}
		prvi = drugi;
		drugi = i;
		if (prvi == -1)
		{
			s1 = s.substr(0, drugi);
			if (dobar(s1))
				stablo.umetni(s1);
		}
		else
		{
			s1 = s.substr(prvi + 1, drugi - prvi - 1);
			if (dobar(s1))
				stablo.umetni(s1);	
		}
		i++;
	}
	s1 = s.substr(drugi + 1);
	if (dobar(s1))
		stablo.umetni(s1);
	dat.close();
}

int main()
{
	string path = "fajlovi";
	ofstream fs;
	fs.open("NaziviDatoteka.txt");
	if (!fs.is_open()) exit(1);
	for (const auto & entry : fs::directory_iterator(path))
		fs << entry.path() << endl;
	fs.close();
	ifstream ulaz;
	ulaz.open("NaziviDatoteka.txt");
	if (!ulaz.is_open()) exit(2);
	
	int ukupno = 0;
	string niz[1500];
	while (true)
	{
		string help;
		getline(ulaz, help);
		if (help.length() == 0)
			break;
		help = help.substr(1);
		help = help.substr(0, help.length() - 1);
		string a, b;
		a = help.substr(0, 7);
		b = help.substr(8);
		help = a + b;
		niz[ukupno++] = help;
	}
	ulaz.close();

	int BrojUcitanih = 0;
	int indeks;
	bool stvoren = false;
	TrieStablo*stablo=nullptr;
	TrieStablo tree;
	while (true)
	{
		cout << "Meni:" << endl;
		cout << "1.Stvori prazan recnik" << endl;
		cout << "2.Ucitaj datoteke u recnik" << endl;
		cout << "3.Umetni reci u recnik, unesite 0 za kraj unosa" << endl;
		cout << "4.Obrisi rec iz recnika" << endl;
		cout << "5.Prebroj pojavljivanje reci, unesite 0 za kraj unosa" << endl;
		cout << "6.Ispisi statistiku recnika" << endl;
		cout << "7.Predvidi rec, unesite 0 za kraj unosa" << endl;
		cout << "8.Obrisi recnik" << endl;
		cout << "9.Zavrsi rad" << endl<<endl;
		cout << "Unesite zeljenu opciju: "; cin >> indeks;
		if (indeks < 1 || indeks >9)
		{
			cout << "Pogresan unos" << endl<<endl;
			continue;
		}
		if (indeks != 1 && !stvoren)
		{
			cout << "Nije formiran recnik" << endl<<endl;
			continue;
		}
		if (indeks == 1)
		{
			if (stvoren)
			{
				cout << "Vec je stvoren recnik" << endl <<endl;
				continue;
			}
			stablo = new TrieStablo();
			stvoren = true;
			cout << endl;
			continue;
		} 
		if (indeks == 2)
		{
			cout << "Izaberite koliko datoteka hocete da ucitate" << endl;
			int br;
			cin >> br;
			for (int i = BrojUcitanih; i < BrojUcitanih + br && i < ukupno;i++)
			{
				string s = niz[i];
				ucitajdatoteku(*stablo, s);
			}
			BrojUcitanih = BrojUcitanih + br;
			stablo->ispisiStatistiku(cout);
			cout << endl;
		}
		if (indeks == 3)
		{
			while (true)
			{
				cout << "Unesite rec" << endl;
				string pom;
				cin >> pom;
				if (pom == "0")
				{
					cout << "Kraj unosa" << endl << endl;
					break;
				}
				if (!dobar(pom))
				{
					cout << "Losa rec" << endl << endl;
					break;
				}
				stablo->umetni(pom);
				cout << endl;
			}
		} 
		if (indeks == 4)
		{
			cout << "Unesite rec koja se brise" << endl;
			string pom;
			cin >> pom;
			if (!dobar(pom))
			{
				cout << "Losa rec" << endl << endl;
				continue;
			}
			stablo->obrisi(pom);
			cout << endl;
		}
		if (indeks == 5)
		{
			while (true)
			{
				cout << "Unesite rec koja se prebrojava" << endl;
				string pom;
				cin >> pom;
				if (pom == "0")
				{
					cout << "Kraj unosa" << endl << endl;
					break;
				}
				if (!dobar(pom))
				{
					cout << "Losa rec" << endl << endl;
					break;
				}
				cout << "Broj poljavljivanja: " << stablo->dohvatiFrek(pom);
				stablo->umetni(pom);
				cout << endl << endl;
			}
		} 
		if (indeks == 6)
		{
			stablo->ispisiStatistiku(cout);
			cout << endl;
		}
		if (indeks == 8)
		{
			stablo->brisi();
			stablo = nullptr;
			stvoren = false;
			BrojUcitanih = 0;
			cout << endl;
			continue;
		}
		if (indeks == 9)
		{
			cout << endl << "Kraj programa";
			break;
		}
		if (indeks == 7)
		{
			while (true)
			{
				cout << "Unesite string za koji se vrsi predikcija" << endl;
				string pom;
				cin >> pom;
				if (pom == "0")
				{
					cout << "Kraj unosa" << endl << endl;
					break;
				}
				if (!dobar(pom))
				{
					cout << "Los string" << endl << endl;
					break;
				}
				cout << "Predikcija:" << endl;
				stablo->predvidi(pom);
				cout << endl;
			}
		}
	}
}

/*ifstream daa;
	string s = niz[0],a,b;
	daa.open(s);
	if (s != "fajlovi\\fic_1817_8550.txt")
		cout << 233 << endl;
	cout<<"fajlovi\\fic_1817_8550.txt" << endl;
	cout << s<<endl;
	if (!daa.is_open()) exit(3);
	daa.close();*/



