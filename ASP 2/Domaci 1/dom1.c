#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>
#include <time.h>
#include <string.h>


typedef struct cvor
{
	int info;
	int balans;
	struct cvor* levi;
	struct cvor* desni;

} Cvor;


typedef struct elem
{
	int row;
	int col;
	int nasao;
	int broj;

} Element;

typedef struct nc
{
	int nasao;
	int broj;
} trazencvor;

int** unesi_mat(int m,int n)
{
	int **mat = malloc(m * sizeof(int*));
	if(!mat)
	{
		printf("\nMEM_GRESKA");
		exit(1);

	}
	for (int i = 0; i < m; i++)
	{
		mat[i] = malloc(n * sizeof(int));
		if (!mat[i])
		{
			printf("\nMEM_GRESKA");
			exit(1);
		}
	}
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				scanf("%d", &mat[i][j]);		

	return mat;

} //standardni ulaz

int** form_mat(int m, int n)
{
	int **mat = malloc(m * sizeof(int*));
	if (!mat)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}
	for (int i = 0; i < m; i++)
	{
		mat[i] = malloc(n * sizeof(int));
		if (!mat[i])
		{
			printf("\nMEM_GRESKA");
			exit(1);
		}
	}
	return mat;
} //datoteka

void pisi(int** mat, int m, int n)
{
	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < n-1; j++)
			printf("%d ", mat[i][j]);
		printf("%d\n", mat[i][n - 1]);
	}
}

Element pretrazi_mat(int**mat, int m, int n, int k)
{
	int indrow = 0,indcol=n-1;
	Element el;
	el.nasao = 0;
	el.broj = 0;
	while (1)
	{
		if (indrow > m-1 || indcol < 0)
			break;
		el.broj++;
		if (mat[indrow][indcol] == k)
		{
			el.row = indrow;
			el.col = indcol;
			el.nasao = 1;
			break;
		}
		else if (mat[indrow][indcol] > k)
			indcol--;
		else
			indrow++;
	}
	return el;
	
}

void evaluiraj(int**mat, int m, int n, int low, int high, int broj)
{
	double prosek = 0;
	int k;
	Element el;
	for (int i = 0; i < broj; i++)
	{
		k = rand() / (RAND_MAX + 1.0)*(high - low) + low;
		el = pretrazi_mat(mat, m, n, k);
		if (el.nasao)
			printf("Element %d nadjen na poziciji %d,%d\n", k, el.row, el.col);
		else
			printf("Element %d nije pronadjen\n",k);
		prosek = prosek + el.broj;
		
	}
	printf("Prosecan broj pristupa matrici je %lf\n", prosek / broj);

}

void rightrot(Cvor*x)
{
	Cvor*y = x->levi;
	Cvor*pom = y->desni;
	y->desni = x;
	x->levi = pom;
}

void leftrot(Cvor*x)
{
	Cvor*y = x->desni;
	Cvor*pom = y->levi;
	y->levi = x;
	x->desni = pom;
}

Cvor* avl_insert(Cvor*root, int k)
{
	Cvor* fpp, *fx, *p, *x, *y,*q;
	fpp = NULL;
	fx = NULL;
	p = root;
	x = p;
	int imbal = 0;
	while (p)
	{
		if (k < p->info)
			q = p->levi;
		else
			q = p->desni;
		if (q)
		{
			if (q->balans != 0)
			{
				fx = p;
				x = q;
			}
		}
		fpp = p;
		p = q;
	}
	q = malloc(sizeof(Cvor));
	if (!q)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}
	q->info = k;
	q->balans = 0;
	q->levi = NULL;
	q->desni = NULL;
	if (!fpp)
	{
		p = q;
		return p;
	}
	if (k < fpp->info)
		fpp->levi = q;
	else
		fpp->desni = q;
	if (k < x->info)
		p = x->levi;
	else
		p = x->desni;
	y = p;
	while (p != q)
	{
		if (k < p->info)
		{
			p->balans = 1;
			p = p->levi;
		}
		else
		{
			p->balans = -1;
			p = p->desni;
		}
	}
	if (k < x->info)
		imbal = 1;
	else
		imbal = -1;
	if (x->balans == 0)
	{
		x->balans = imbal;
		return root;
	}
	if (x->balans != imbal)
	{
		x->balans = 0;
		return root;
	}
	if (y->balans == imbal)
	{
		p = y;
		if (imbal == 1)
			rightrot(x);
		else
			leftrot(x);
		x->balans = y->balans = 0;
	}
	else
	{
		if (imbal == 1)
		{
			p = y->desni;
			leftrot(y);
			x->levi = p;
			rightrot(x);
		}
		else
		{
			p = y->levi;
			rightrot(y);
			x->desni = p;
			leftrot(x);
		}
		if(p->balans==0)
			x->balans = y->balans = 0;
		else
		{
			if (p->balans == imbal)
			{
				x->balans = -imbal;
				y->balans = 0;
			}
			else
			{
				x->balans = 0;
				y->balans = imbal;
			}
		}
		p->balans = 0;
	}

	if (!fx)
		root = p;
	else
	{
		if (x == fx->desni)
			fx->desni = p;
		else
			fx->levi = p;
	}
	return root;

}

Cvor*form_tree(int**mat, int m, int n)
{
	Cvor*avl = NULL;
	for (int i = 0; i < m; i++)
		for (int j = 0; j < n; j++)
			avl = avl_insert(avl, mat[i][j]);
	return avl;
}

void delete_tree(Cvor*root)
{
	int br = 0;
	Cvor**niz = NULL;
	Cvor*next,*pom;
	int duzina = 20;
	niz = malloc(20 * sizeof(Cvor*));
	if (!niz)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}
	niz[br] = root;
	br++;
	while (br > 0)
	{
		next = niz[br - 1];
		br--;
		while (next)
		{
			if (next->desni)
			{
				if (br > duzina)
				{
					duzina += 20;
					niz = realloc(niz,duzina * sizeof(Cvor*));
					if (!niz)
					{
						printf("\nMEM_GRESKA");
						exit(1);
					}
				}
				niz[br] = next->desni;
				br++;
			}
			pom = next;
			next = next->levi;
			free(pom);
		}
	}

}

void ispis(Cvor*root)
{
	int visina=0;
	int br;
	Cvor**niz;
	Cvor*p = root;
	while (p)
	{
		if (p->balans == 1)
			p = p->levi;
		else
			p = p->desni;
		visina++;
	}
	br = pow(2, visina + 1) - 1;
	niz = malloc(br * sizeof(Cvor*));
	if (!niz)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}
	niz[0] = root;

	for (int i = 0; i < pow(2, visina)-1; i++)
	{
		niz[2 * i + 1] = niz[i] ? niz[i]->levi : NULL;
		niz[2 * i + 2] = niz[i] ? niz[i]->desni : NULL;
	}

	int  level = 0;
	int j = 0;
	while (level <= visina)
	{
		for (int i = 0; i < pow(2, visina - level) - 1; i++)
			printf(" "); //spejs
		for (int i = 0; i < pow(2, level) - 1; i++)
		{
			if(niz[j])
			printf("%d", niz[j]->info);
			else
				printf(" ");
			j++; //cvor
			for(int t=0; t<pow(2,visina-level+1)-1;t++)
				printf(" ");
		}

		if (niz[j])
			printf("%d", niz[j]->info);
		else
			printf(" ");
		j++; //cvor

		for (int i = 0; i < pow(2, visina - level) - 1; i++)
			printf(" ");//spejs+

		level++;
		printf("\n");
	}

}

trazencvor find_node(Cvor*root, int k)
{
	trazencvor cv;
	cv.nasao = 0;
	cv.broj = 0;
	Cvor*p = root;
	while (p)
	{
		cv.broj++;
		if (p->info == k)
		{
			cv.nasao = 1;
			break;
		}
		else if (p->info < k)
			p = p->desni;
		else
			p = p->levi;
	}
	return cv;
}

void merge(int arr[], int l, int m, int r)
{
	int i, j, k;
	int n1 = m - l + 1;
	int n2 = r - m;

	
	int* L = malloc(n1 * sizeof(int)); 
	if (!L)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}
	int* R=malloc(n2*sizeof(int));
	if (!R)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}

	for (i = 0; i < n1; i++)
		L[i] = arr[l + i];
	for (j = 0; j < n2; j++)
		R[j] = arr[m + 1 + j];

	
	i = 0;
	j = 0;
	k = l;
	while (i < n1 && j < n2)
	{
		if (L[i] <= R[j])
		{
			arr[k] = L[i];
			i++;
		}
		else
		{
			arr[k] = R[j];
			j++;
		}
		k++;
	}


	while (i < n1)
	{
		arr[k] = L[i];
		i++;
		k++;
	}


	while (j < n2)
	{
		arr[k] = R[j];
		j++;
		k++;
	}
}

int minimum(int x, int y) 
{ 
	return x < y ? x : y; 
}

void merge_sort(int *arr, int n)
{
	int step; 
	int start; 
	for (step = 1; step <= n - 1; step = 2 * step)
	{
		for (start = 0; start < n - 1; start += 2 * step)
		{
			int mid = minimum(start + step - 1, n - 1);
			int right= minimum(start + 2 * step - 1, n - 1);
			merge(arr, start, mid, right);
		}
	}


}

int** generisi_mat(int m, int n, int low, int high)
{
	int **mat = malloc(m * sizeof(int*));
	if (!mat)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}
	for (int i = 0; i < m; i++)
	{
		mat[i] = malloc(n * sizeof(int));
		if (!mat[i])
		{
			printf("\nMEM_GRESKA");
			exit(1);
		}
	}
	int *niz = malloc(m*n * sizeof(int));
	if (!niz)
	{
		printf("\nMEM_GRESKA");
		exit(1);
	}
	for (int i = 0; i < m*n; i++)
		niz[i] = rand() / (RAND_MAX + 1.0) * (high - low + 1) + low;
	merge_sort(niz, m*n);
	int glob = 0;
	for (int i = 0; i < minimum(m, n); i++)
	{
		mat[i][i] = niz[glob++];
		int j, k;
		for (j = i+1, k = i+1; j < m && k < n; j++, k++)
		{
			mat[j][i] = niz[glob++];
			mat[i][k] = niz[glob++];
		}
		while (j < m)
			mat[j++][i] = niz[glob++];
		while(k<n)
			mat[i][k++] = niz[glob++];
	}
	return mat;
}

void free_mat(int**mat, int m, int n)
{
	for (int i = 0; i < m; i++)
		free(mat[i]);
	free(mat);
}

void mat_vs_tree(Cvor*avl, int**mat, int m, int n, int low, int high, int broj)
{
	double prosekm = 0, prosekst = 0;
	int k;
	Element el;
	trazencvor tc;
	for (int i = 0; i < broj; i++)
	{
		k = rand() / (RAND_MAX + 1.0)*(high - low) + low;
		el = pretrazi_mat(mat, m, n, k);
		tc = find_node(avl, k);
		if (el.nasao)
			printf("Element %d nadjen na poziciji %d,%d\n", k, el.row, el.col);
		else
			printf("Element %d nije pronadjen\n",k);
		prosekm = prosekm + el.broj;
		prosekst = prosekst + tc.broj;
		
	}
	printf("Prosecan broj pristupa matrici je %lf\n", prosekm / broj);
	printf("Prosecan broj pristupa stablu je %lf\n", prosekst / broj);
}

void pisim(int **mat, int m, int n)
{
	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < n - 1; j++)
			printf("%d ", mat[i][j]);
		printf("%d\n", mat[i][n - 1]);
	}
}

int main()
{
	srand(time(NULL));
	int **mat = NULL;
	int opcija;
	int matex = 0;
	int treeex = 0;
	int sifra;
	int m, n;
	FILE* podaci = NULL;
	Cvor*avl = NULL;
	printf("Meni:\n");
	printf("1: Unesite zeljenu matricu\n");
	printf("2: Generisi nasumicnu matricu\n");
	printf("3: Pronadjite zadati element u matrici\n");
	printf("4: Evaluirajte performanse\n");
	printf("5: Formirajte stablo na osnovu date matrice\n");
	printf("6: Pronadjite zadati element u stablu\n");
	printf("7: Umetnite zadati element u stablo\n");
	printf("8: Ispisi stablo\n");
	printf("9: Obrisi stablo\n");
	printf("10: Uporedi performanse stabla i matrice\n");
	printf("11: Zavrsi\n\n");

	while (1)
	{
		printf("Unesite broj koji zelite: ");
		scanf("%d", &opcija);
		if (opcija < 1 || opcija > 11)
		{
			printf("Nepostojeca opcija\n");
		}
		else if (opcija == 11)
			break;
		else if ((opcija ==3 || opcija==5 || opcija==4 || opcija ==10) && !matex)
		{
			printf("Matrica nije formirana\n");
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
				if (mat)
				{
					free_mat(mat, m, n);
					matex = 0;
				}
				printf("Unesite dimenzije: ");
				scanf("%d %d", &m, &n);
				if (m < 0 || n < 0)
				{
					printf("Lose dimenzije\n");
					continue; //gubi se stara matrcia
				}
				mat = unesi_mat(m, n);
				matex = 1;
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
				if (mat)
				{
					free_mat(mat, m, n);
					matex = 0;
				}

				if (fscanf(podaci, "%d %d", &m, &n) != 2)
				{
					printf("Kraj datoteke\n"); //svejedno se brise stara matrica
					continue;
				}
				else
					matex = 1;
				mat = form_mat(m, n);
				for (int i = 0; i < m; i++)
					for (int j = 0; j < n; j++)
						fscanf(podaci, "%d", &mat[i][j]);
				pisim(mat, m, n);

			}
		}
		else if (opcija == 2)
		{
			int high, low;
			if (mat)
			{
				free_mat(mat, m, n);
				matex = 0;
			}
			printf("Unesite dimenzije: ");
			scanf("%d %d", &m, &n);
			printf("Unesite interval: ");
			scanf("%d,%d", &low, &high);
			mat = generisi_mat(m, n, low, high);
			printf("\n");
			pisim(mat, m, n);
			printf("\n");
			matex = 1;
		}
		else if (opcija == 3)
		{
			int el;
			Element pom;
			printf("Unesite trazeni element: ");
			scanf("%d", &el);
			pom = pretrazi_mat(mat, m, n, el);
			if (!pom.nasao)
				printf("Nije nadjen trazeni element\n");
			else
				printf("Element pronadjen na poziciji %d,%d\n", pom.row, pom.col);
		}
		else if (opcija == 4)
		{
			int low1, high1,br;
			printf("Unesite interval: ");
			scanf("%d,%d", &low1, &high1);
			printf("Unesite broj elemenata: ");
			scanf("%d", &br);
			evaluiraj(mat, m, n, low1, high1, br);			
		}

		else if (opcija == 5)
		{
			if (avl)
				delete_tree(avl);
			avl = form_tree(mat, m, n);  
		}
		else if (opcija == 6)
		{
			if (!avl)
			{
				printf("Niste uneli stablo\n");
				continue;
				}
			trazencvor tc;
			int k;
			printf("Unesite trazeni element: ");
			scanf("%d", &k);
			tc = find_node(avl, k);
			if (!tc.nasao)
				printf("Nije nadjen trazeni element\n");
			else
				printf("Pronadjen element na visini %d\n", tc.broj - 1);
		}
		else if (opcija == 7)
		{
			int k;
			printf("Unesite element koji se umece: ");
			scanf("%d", &k);
			avl = avl_insert(avl, k);
		}
		else if (opcija == 8)
		{ 
			if (avl)
			ispis(avl);
			printf("\n");
        }
		else if (opcija == 9)
		{
		    delete_tree(avl);
			avl = NULL;
        }
		else if (opcija == 10)
		{
		Cvor*start = form_tree(mat, m, n);
		int low2, high2, br2;
		printf("Unesite interval: ");
		scanf("%d,%d", &low2, &high2);
		printf("Unesite broj elemenata: ");
		scanf("%d", &br2);
		mat_vs_tree(start, mat, m, n, low2, high2, br2);
		delete_tree(start);
		}

	}
	
	if (podaci)
		fclose(podaci);
	return 0;
	
}



/*4 4
2 3 5 6
4 7 10 12
5 8 13 20
9 12 15 26

5 3
4 6 7
8 10 12
9 11 13
10 15 17
18 19 28

3 3
1 6 9
2 8 10
7 9 12

5 5
20 24 28 30 32
22 26 29 33 36
26 27 32 38 40
30 32 36 41 45
34 37 40 46 49*/