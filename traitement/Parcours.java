package modelisation.traitement;

import java.util.ArrayList;

public class Parcours {

	public static ArrayList<Integer> belmann_end_ford_moore(Graph g, int s, int t){
		
		int nb_sommet = g.vertices();
		int[] d = new int[nb_sommet];
		int[] parent = new int[nb_sommet];
		
		for(int i = 0; i < nb_sommet; i++){
			d[i] = Integer.MAX_VALUE;
			parent[i] = -1;
		}
		
		d[s] = 0;
		
		int j = 0;
		int modifie = 0;
		
		while ((j < nb_sommet) && (modifie != -1)){
			modifie = -1;
			for (int u = 0; u < nb_sommet; u++){
				for (Edge e : g.next(u)){
					int cout = e.cost;
					int voisin = e.to;
					if (d[u] + cout < d[voisin]){
						d[voisin] = d[u] + cout;
						parent[voisin] = u;
						modifie = u;
					}
				}
			}
			
			j++;
		}
		
		ArrayList<Integer> ali = new ArrayList<>();
		
		int som = t;
		ali.add(som);
		
		while (som != s){
			som = parent[som];
			ali.add(som);
		}
		
		return ali;
	}

	public static ArrayList<Integer> dijkstra(Graph g,int s,int t){
		int noeud;
		ArrayList<Integer> chemin = new ArrayList<>();
		Heap hp = new Heap(g.vertices());
		hp.decreaseKey(s,0);
		boolean visited[] = new boolean[g.getV()];
		int parents[] = new int [g.vertices()];
		for(int i = 0; i < visited.length;i++){
			visited[i]=false;
			parents[i] = -1;
		}
		
		
		while(!hp.isEmpty()){
			noeud = hp.pop();
			visited[noeud] = true;
			for(Edge e : g.next(noeud)){
				if(!visited[e.to]){
					if(e.cost+hp.priority(e.from)<=hp.priority(e.to) ){
						hp.decreaseKey(e.to,e.cost+hp.priority(e.from));
						parents[e.to] = e.from;
					}
				}
			}
		}
		
		noeud = t;
		while(noeud != s){
			chemin.add(noeud);
			noeud = parents[noeud];
		}
		chemin.add(noeud);
		
		return chemin;

	}
	
	public static ArrayList<Integer> twograph(Graph g,int s,int t){
		ArrayList<Integer> chemin ;
		ArrayList<Integer> chemin2 ;
		//recherche du premier chemin
		chemin = dijkstra(g,s,t);
		
		//inversion des arretes
		for(int i =0; i < chemin.size()-1;i++){
			g.addEdge(new Edge(chemin.get(i),chemin.get(i+1),0));
		}
		
		//recherche du deuxieme chemin
		chemin2 = dijkstra(g,s,t);
		
		for(int i = 0; i < chemin2.size();i++){
			chemin.add(chemin2.get(i));
		}
		
		return chemin ;
	}
	
	
}
