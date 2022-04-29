package PROJECT;

import java.util.Arrays;

public class TriSelect {

	static int[] tab ;
	static int intervIndiceMin (int a, int b) {
		int indiceMin= a;
		for(int i=a; i<=b ; i++) {
			if(tab[i]<tab[indiceMin])
				indiceMin = i;
		}
		return indiceMin;
	}
	static void main(String[] args) {
		// TODO Auto-generated method stub

		int n=17;
		tab =new int[n];
		for(int i=0 ;i<n ; i++ )
			tab[i]=(int)(50*java.lang.Math.random()) ;
		// Affichage tabl trié
		System.out.println(java.util.Arrays.toString(tab));
		//Réaliser le tri avec une boucle;
		for(int i=0;i<n;i++) {
			int j= intervIndiceMin(i , n-1);
			int tmp = tab[j];
			tab[j]= tab[i];
			tab[i]= tmp;
		}
		System.out.println(Arrays.toString(tab)); //tab trié
		boolean triFini=false;
		while(triFini==false) {
		    triFini = true ;
		    for(int i=0 ; i <n-1; i ++)
		        if ( tab [i]>tab [i + 1] ) {
		            triFini = false ;
		            int tmp = tab [i] ;
		            tab [i] = tab [i +1] ;
		            tab [i +1]=tmp ;
		         }
		    int [ ] apparitions = new int [ 50 ] ;
		    for ( int i=0 ; i <n ; i ++)
		         apparitions[tab [i]]++;
		    int pos = 0 ;
		    for ( int val=0 ; val <50 ; val ++)
		    for ( int j=0 ; j < apparitions[val] ; j++) {
		         tab [ pos ] = val ;
		         pos++ ;
		    }
		    
		}

	}
	

}
