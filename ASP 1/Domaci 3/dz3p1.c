#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#define korak 10

typedef struct cvor
{
	char odrediste;
	char op;
	int duzina;
	char arg1; int arg11;
	char arg2; int arg22;
	int sledbenik;
	int prethodnik;

} Cvor;

char* readLine(int , FILE *podaci);
Cvor* napravicvor(int sifra,FILE*podaci);
void form_graph(int***susedi, Cvor**cvorovi, int*duz, int sifra, FILE*podaci);
void write_graph(int**susedi, Cvor*cvorovi, int len, int sifra, FILE*podaci);
void critical_path(int **susedi, Cvor*cvorovi, int len);
void transitive(int **susedi, int len);
void freegraph(int **susedi, Cvor*niz);



int main()
{
	int **mat=NULL;
	Cvor *niz=NULL;
	int len=0;
	int opcija;
	int formiran = 0;
	int otvorena=0;
	int sifra;
	int ispis;
	FILE* podaci=NULL;
	FILE* izlaz = NULL;
	printf("Meni:\n");
	printf("1: Unesite kod od kog zelite da formirate graf\n");
	printf("2: Ispisite uneti kod i formirani graf\n");
	printf("3: Pronadjite kriticni put, cvorove na kriticnom putu i cikluse u kojima cvorovi mogu da se izvrsavaju\n");
	printf("4: Ispisite tranzitivne grane u grafu\n");
	printf("5: Obrisite formirani graf\n");
	printf("6: Zavrsite sa radom\n\n");
	while (1)
	{
		printf("Unesite broj koji zelite: "); 
		scanf("%d", &opcija);
		if (opcija < 1 || opcija >6)
		{
			printf("Nepostojeca opcija\n");
		}
		else if (opcija == 6)
			break;
		else if (opcija != 1 && !formiran)
		{
			printf("Graf nije formiran\n");
		}
		else if (opcija == 1)
		{
			printf("Izaberite nacin unosa (0 za standardni ulaz, 1 za datoteku): ");
			scanf("%d", &sifra);
			if (sifra != 0 && sifra != 1)
			{
				printf("Pogresan unos\n");
				continue;
			}
			if (!sifra)
			{
				printf("Unesite kod:\n");
				getchar();
				form_graph(&mat, &niz, &len,sifra,podaci);
				formiran = 1;
			}
			else
			{
				if (!podaci)
				{
					podaci = fopen("ulaz.txt", "r");
					if (!podaci)
					{
						printf("DAT_GRESKA");
						continue;
					}
				}
				form_graph(&mat, &niz, &len, sifra, podaci);
				formiran = 1;
			}
		}
		else if (opcija == 2)
		{
			printf("Izaberite nacin ispisa (0 za standardni izlaz, 1 za datoteku): ");
			scanf("%d", &ispis);
			if(!ispis)
			write_graph(mat, niz, len, ispis, izlaz);
			else
			{
				if (!izlaz)
					izlaz = fopen("izlaz.txt", "w");
				write_graph(mat, niz, len, ispis, izlaz);
			}
		}
		else if (opcija == 3)
		{
			critical_path(mat, niz, len);
		}
		else if (opcija == 4)
		{
			transitive(mat, len);
		}
		else if (opcija == 5)
		{
			if (!formiran)
				continue;
			freegraph(mat, niz);
			formiran = 0;
		}
		else
		{
			printf("Pogresan unos\n");
			continue;
		}
	}
	if (podaci)
		fclose(podaci);
	if (izlaz)
		fclose(izlaz);
	return 0;
}




char* readLine(int sifra, FILE*podaci)
{
	char* str = NULL;
	int br = 0;
	char znak;
	int kraj = 0;
	if (!sifra)
	{
		while ((znak = getchar()) != '\n')
		{
			if (br%korak == 0)
			{
				str = realloc(str, (br + korak) * sizeof(char));
				if (!str)
				{
					printf("MEM_GRESKA");
					exit(1);
				}
			}
			str[br] = znak;
			br++;
		}
		str = realloc(str, (br + 1) * sizeof(char));
		if (!str)
		{
			printf("MEM_GRESKA");
			exit(1);
		}
		str[br] = '\0';
		return str;
	}
	else
	{
		while ((znak = fgetc(podaci)) != '\n')
		{
			if (znak == EOF)
			{
				kraj = 1;
				break;
			}
			if (br%korak == 0)
			{
				str = realloc(str, (br + korak) * sizeof(char));
				if (!str)
				{
					printf("MEM_GRESKA");
					exit(1);
				}
			}
			str[br] = znak;
			br++;
		}
		if (kraj)
			return str;
		str = realloc(str, (br + 1) * sizeof(char));
		if (!str)
		{
			printf("MEM_GRESKA");
			exit(1);
		}
		str[br] = '\0';
		return str;
	}
}

Cvor* napravicvor(int sifra, FILE*podaci)
{
	Cvor*tmp = NULL;
	int br = 0;
	int a = 0;
	Cvor operacija;
	char*niz = readLine(sifra,podaci);
	if (!niz)
	{
		operacija.arg1 = '!';
		return &operacija;
	}
	if (niz[0] == '\0')
		return tmp;
	operacija.arg1 = '?';
	operacija.arg2 = '?';
	operacija.sledbenik = 0;
	operacija.prethodnik = 0;
	if (!islower(niz[br]))
	{
		printf("Neispravan unos\n");
		operacija.arg1 = '/';
		return &operacija;
	}
	operacija.odrediste = niz[br]; // space = space
	if (niz[1] != ' ' || niz[2] != '=' || niz[3] != ' ')
	{
		printf("Neispravan unos\n");
		operacija.arg1 = '/';
		return &operacija;
	}
	br = 4;
	if (niz[br] != 'N')
	{
		if (islower(niz[br]))
			operacija.arg1 = niz[br++];
		else if(isdigit(niz[br]))
		{
			while (isdigit(niz[br]))
			{
				a = a * 10 + (niz[br] - '0');
				br++;
			} //space
			operacija.arg11 = a;
		}
		else
		{
			printf("Neispravan unos\n");
			operacija.arg1 = '/';
			return &operacija;
		}
		if(niz[br]!= ' ')
		{
			printf("Neispravan unos\n");
			operacija.arg1 = '/';
			return &operacija;
		}
		a = 0;
		br++;
		operacija.op = niz[br];
		switch (niz[br])
		{
		case '+': case'-': operacija.duzina = 3;
			br = br + 2;
			break;
		case '/': operacija.duzina = 23;
			br = br + 2;
			break;
		case '*': operacija.duzina = 10;
			br = br + 2;
			break;
		case 'A': case'X': operacija.duzina = 1;
			br = br + 4;
			break;
		case 'O': operacija.duzina = 1;
			br = br + 3;
			break;
		default: printf("Neispravan unos\n");
			operacija.arg1 = '/';
			return &operacija;

		}
		if (islower(niz[br]))
			operacija.arg2 = niz[br++];
		else if (isdigit(niz[br]))
		{
			int a = 0;
			while (isdigit(niz[br]))
			{
				a = a * 10 + (niz[br] - '0');
				br++;
			}
			operacija.arg22 = a;
		}
		else
		{
			printf("Neispravan unos\n");
			operacija.arg1 = '/';
			return &operacija;
		}
	}
	else
	{
		operacija.op = 'N';
		operacija.duzina = 1;
		br = br + 4;
		if (islower(niz[br]))
			operacija.arg1 = niz[br++];
		else if (isdigit(niz[br]))
		{
			while (isdigit(niz[br]))
			{
				a = a * 10 + (niz[br] - '0');
				br++;
			} //space
			operacija.arg11 = a;
		}
		else
		{
			printf("Neispravan unos\n");
			operacija.arg1 = '/';
			return &operacija;
		}
	}
	tmp = &operacija;
	return tmp;
}

void form_graph(int***susedi, Cvor**cvorovi, int*duz, int sifra, FILE*podaci)
{
	int **mat=NULL;
	Cvor *niz=NULL;
	int len = 0;
	Cvor* pom;
	Cvor node;
	while (1)
	{
		pom = napravicvor(sifra,podaci);
		if (!pom)
			break;
		if (pom->arg1 == '!')
		{
			printf("Kraj datoteke\n");
			return;
		}
		if (pom->arg1 == '/')
			continue;
		node = *pom;
    	mat = realloc(mat, (len + 1) * sizeof(int*));
		if (!mat)
		{
			printf("MEM_GRESKA");
			exit(1);
		}
		for (int i = 0; i < len; i++)
		{
			mat[i] = realloc(mat[i], (len + 1) * sizeof(int));
			if (!mat[i])
			{
				printf("MEM_GRESKA");
				exit(1);
			}
		}
		mat[len] = malloc((len + 1) * sizeof(int));
		if (!mat[len])
		{
			printf("MEM_GRESKA");
			exit(1);
		}
			
		niz = realloc(niz, (len + 1) * sizeof(Cvor));
		if (!niz)
		{
			printf("MEM_GRESKA");
			exit(1);
		}
		mat[len][len] = 0;
		niz[len] = node;

		for (int i = 0; i < len; i++)
		{
			if (niz[i].odrediste == niz[len].arg1 || niz[i].odrediste == niz[len].arg2)
			{
				mat[i][len] = 1;
				niz[i].sledbenik++;
				niz[len].prethodnik++;
			}
			else
				mat[i][len] = 0;
			mat[len][i] = 0;
		}
		len++;
	}
	mat = realloc(mat, (len + 1) * sizeof(int*));
	if (!mat)
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	//mat[len + 1] = NULL;
	mat[len] = malloc((len + 1) * sizeof(int));
	if (!mat[len])
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	niz = realloc(niz, (len + 1) * sizeof(Cvor));
	if (!niz)
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	Cvor p;
	p.prethodnik = 0;
	p.sledbenik = 0;
	niz[len] = p;

	for (int i = 0; i < len; i++)
	{
		if (niz[i].sledbenik == 0)
		{
			mat[i][len] = 1;
			niz[i].sledbenik = 1;
			niz[len].prethodnik++;
		}
		else
			mat[i][len] = 0;
		mat[len][i] = 0;
	}
	mat[len][len] = 0;

	*duz = len;
	*cvorovi = niz;
	*susedi = mat;
}

void write_graph(int**susedi, Cvor*cvorovi, int len, int sifra, FILE*podaci)
{
	if (!sifra)
	{
		for (int i = 0; i < len; i++)
		{
			Cvor pom = cvorovi[i];
			printf("%d: ", i + 1);
			printf("%c = ", pom.odrediste);
			if (pom.op == 'N')
			{
				printf("NOT ");
				if (pom.arg1 == '?')
					printf("%d", pom.arg11);
				else
					printf("%c", pom.arg1);
			}
			else
			{
				if (pom.arg1 == '?')
					printf("%d ", pom.arg11);
				else
					printf("%c ", pom.arg1);
				if (pom.op == '+' || pom.op == '-' || pom.op == '*' || pom.op == '/')
					printf("%c ", pom.op);
				else if (pom.op == 'A')
					printf("AND ");
				else if (pom.op == 'X')
					printf("XOR ");
				else
					printf("OR ");
				if (pom.arg2 == '?')
					printf("%d", pom.arg22);
				else
					printf("%c", pom.arg2);
			}
			printf("\n");
		}
		for (int i = 0; i < len; i++)
			for (int j = i; j < len; j++)
			{
				if (susedi[i][j] == 1)
					printf("%d -> %d , tezina grane = %d\n", i + 1, j + 1, cvorovi[i].duzina);
			}
		printf("\n");
	}
	else
	{
		for (int i = 0; i < len; i++)
		{
			Cvor pom = cvorovi[i];
			fprintf(podaci,"%d: ", i + 1);
			fprintf(podaci,"%c = ", pom.odrediste);
			if (pom.op == 'N')
			{
				fprintf(podaci,"NOT ");
				if (pom.arg1 == '?')
					fprintf(podaci,"%d", pom.arg11);
				else
					fprintf(podaci,"%c", pom.arg1);
			}
			else
			{
				if (pom.arg1 == '?')
					fprintf(podaci,"%d ", pom.arg11);
				else
					fprintf(podaci, "%c ", pom.arg1);
				if (pom.op == '+' || pom.op == '-' || pom.op == '*' || pom.op == '/')
					fprintf(podaci, "%c ", pom.op);
				else if (pom.op == 'A')
					fprintf(podaci, "AND ");
				else if (pom.op == 'X')
					fprintf(podaci, "XOR ");
				else
					fprintf(podaci, "OR ");
				if (pom.arg2 == '?')
					fprintf(podaci, "%d", pom.arg22);
				else
					fprintf(podaci, "%c", pom.arg2);
			}
			fprintf(podaci, "\n");
		}
		for (int i = 0; i < len; i++)
			for (int j = i; j < len; j++)
			{
				if (susedi[i][j] == 1)
					fprintf(podaci, "%d -> %d , tezina grane = %d\n", i + 1, j + 1, cvorovi[i].duzina);
			}
		fprintf(podaci, "\n");
	}
}

void critical_path(int **susedi, Cvor*cvorovi, int len)
{
	int *est, *lst, *top, *stek, *posecen;
	int nasteku = 0, br = 0;
	est = calloc((len + 1), sizeof(int));
	if (!est)
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	top = malloc((len + 1) * sizeof(int));
	if (!top)
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	lst = calloc((len + 1), sizeof(int));
	if (!lst)
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	stek = malloc((len + 1) * sizeof(int));
	if (!stek)
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	posecen = calloc((len + 1), sizeof(int));
	if (!posecen)
	{
		printf("MEM_GRESKA");
		exit(1);
	}
	stek[0] = 0;
	nasteku = 1;
	posecen[0] = 1;
	while (nasteku)
	{
		int m = stek[nasteku - 1];
		nasteku--;
		top[br++] = m;
		for (int i = 0; i < len; i++)
		{
			if (susedi[m][i])
				cvorovi[i].prethodnik--;
			if (cvorovi[i].prethodnik == 0 && !posecen[i])
			{
				stek[nasteku] = i;
				nasteku++;
				posecen[i] = 1;
			}
		}
	}
	top[br] = len;

	int max;
	for (int i = 0; i <= len; i++)
	{
		max = 0;
		int z = top[i];
		for (int j = 0; j <= len; j++)
		{
			if (susedi[j][z])
			{
				if (est[j] + cvorovi[j].duzina > max)
					max = cvorovi[j].duzina + est[j];
			}
		}
		est[z] = max;
	}
	printf("Kriticni put je duzine %d\n", est[len]);

	int min;
	for (int i = len; i >= 0; i--)
	{
		min = est[len];
		int z = top[i];
		for (int j = 0; j <= len; j++)
		{
			if (susedi[z][j])
				if (lst[j] - cvorovi[z].duzina < min)
					min = lst[j] - cvorovi[z].duzina;
		}
		lst[z] = min;
	}
	printf("Cvorovi na kriticnom putu su: ");
	for (int i = 0; i < len; i++)
	{
		if (lst[i] == est[i])
			printf("%d ", i + 1);
	}
	printf("\n");

	for (int i = 0; i < len; i++)
	{
		if (est[i] == lst[i] && cvorovi[i].duzina == 1)
			printf("Operacija %d mora krenuti da se izvrsava u ciklusu %d i traje 1 ciklus\n", i + 1, est[i]);
		else if (est[i] == lst[i])
			printf("Operacija %d mora krenuti da se izvrsava u ciklusu %d i traje %d ciklusa\n", i + 1, est[i], cvorovi[i].duzina);
		else
		{
			printf("Operacija %d mora da krene da se izvrsava u nekom od ciklusa ", i + 1);
			for (int j = est[i]; j < lst[i]; j++)
				printf("%d, ", j);
			printf("%d i traje %d ciklus%c\n", lst[i], cvorovi[i].duzina, cvorovi[i].duzina==1 ? ' ' : 'a');
		}
	}
}

void transitive(int **susedi, int len)
{
	int pro = 0;
	char **warshall;
	warshall = malloc(len * sizeof(int*));
	if (!warshall)
	{
		perror("NULL");
		exit(1);
	}
	for (int a = 0; a < len; a++)
	{
		warshall[a] = malloc(len * sizeof(int));
		if (!warshall[a])
		{
			perror("NULL");
			exit(1);
		}
	}
	for (int i = 0; i < len; i++)
		for (int j = 0; j < len; j++)
			warshall[i][j] = susedi[i][j];
	for (int k = 0; k < len; k++)
		for (int i = 0; i < len; i++)
			if (warshall[i][k] == 1)
			{
				for (int j = 0; j < len; j++)
				{
					warshall[i][j] = warshall[i][j] || warshall[k][j];
				}
			}
	for (int i = 0; i < len; i++)
		for (int j = 0; j < len; j++)
		{
			if (susedi[i][j])
			{
				for (int k = 0; k < len; k++)
					if (k != i && susedi[k][j] && warshall[i][k])
						printf("Grana %d -> %d je tranzitivna\n", i + 1, j + 1),pro++;
			}
		}
	if (!pro)
		printf("Nema tranzitivnih grana\n");
}

void freegraph(int **susedi, Cvor*niz)
{   
	//free(susedi);
	//free(niz);
}