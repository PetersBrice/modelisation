package modelisation.traitement;

import java.util.ArrayList;

public class Parcours {

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
	
}
