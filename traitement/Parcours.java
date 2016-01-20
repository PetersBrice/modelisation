package modelisation.traitement;

import java.util.ArrayList;

public class Parcours {
	
	boolean visite[];
	int tab[][];
	
	Graph gr;
	
	public Parcours(){}
	
	public void dijkstra(Graph g,int s,int t){
		
		gr = g;
		
		visite = new boolean[g.vertices()];
		for (boolean n : visite){
			n = false;
		}
		
		/* 
		 * [x][0] = longueur du chemin du noeud x
		 * venant du noeud [x][1]
		 */
		tab = new int[g.vertices()][2];
		for (int i = 0; i < g.vertices(); i++){
			tab[i][0] = Integer.MAX_VALUE;
			tab[i][1] = -1;
		}
		
		visite[s] = true;
		tab[s][0] = 0;
		
		for (Edge e : g.next(s)){
			dijkstraDFS(e, t);
		}
		
		for (int i = 0; i < g.vertices(); i++){
			System.out.println("cout du noeud " + i + " : " + tab[i][0] + " venant du noeud " + tab[i][1]);
		}
	}
	
	private void dijkstraDFS(Edge e, int t){
		
		int coutTotal = e.cost + tab[e.from][0];
		if (coutTotal <= tab[e.to][0]){
			tab[e.to][0] = coutTotal;
			tab[e.to][1] = e.from;
		}
		
	}
}
