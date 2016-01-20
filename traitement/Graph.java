package modelisation.traitement;

import java.util.ArrayList;
import java.io.*;

public class Graph {
	
   private ArrayList<Edge>[] adj;
   private final int V;
   int E;
   
   @SuppressWarnings("unchecked")
   
   public Graph(int N)
	 {
		this.V = N;
		this.E = 0;
		this.adj = (ArrayList<Edge>[]) new ArrayList[N];
		for (int v= 0; v < N; v++)
		  adj[v] = new ArrayList<Edge>();
		
	 }

   public int vertices()
	 {
		return getV();
	 }
   
   public void addEdge(Edge e)
	 {
		int v = e.from;
		int w = e.to;
		adj[v].add(e);
		adj[w].add(e);
	 }
   
   public Iterable<Edge> adj(int v)
	 {
		return adj[v];
	 }      

   public Iterable<Edge> next(int v)
	 {
		ArrayList<Edge> n = new ArrayList<Edge>();
		for (Edge e: adj(v))
		  if (e.to != v)
			n.add(e);
		return n;
	 }      
   
   public Iterable<Edge> edges()
	 {
		ArrayList<Edge> list = new ArrayList<Edge>();
        for (int v = 0; v < getV(); v++)
            for (Edge e : adj(v)) {
                if (e.to != v)
                    list.add(e);
            }
        return list;
    }
   
   
   static public Graph tograph(int[][] itr){
	   int hauteur = itr.length;
	   int largeur = itr[0].length;
	   
	   int tailleG = (largeur * hauteur) + 2;
	   
	   Graph g = new Graph(tailleG); //creation d'un graphe de taille (hauteur * largeur) + 2
	   
	   /* Le E num tailleG - 2 est le premier E, et le E num tailleG - 1 est le dernier E */
	   
	   //initialisation des premiers V de cout 0 partant du premier E vers les E de la premiere ligne de itr
	   for (int i = 0; i < largeur; i++){
		   g.addEdge(new Edge(tailleG - 2, i, 0));
	   }
	   
	   //creation des V partant des (hauteur) lignes de itr
	   for (int h = 0; h < hauteur; h++){
		   for (int l = 0; l < largeur; l++){
			   
			   int posAct = (largeur * h) + l; //num de E depuis lequel les V partent
			   int coutAct = itr[h][l]; //cout des V
			   if (h == hauteur - 1){ //cas dernière ligne (Les E n'ont qu'un seul V)
				   g.addEdge(new Edge(posAct, tailleG - 1, itr[h][l]));
			   } else {
				   if (l == 0){						//cas premiere colonne (Les E n'ont que 2 V)
					 
					   g.addEdge(new Edge(posAct, posAct + largeur, coutAct));		//E "juste en dessous"
					   g.addEdge(new Edge(posAct, posAct + largeur + 1, coutAct));	//E à "droite" du precedent
				   } else if (l == largeur - 1){ 	//cas derniere colonne (Les E n'ont que 2 V)
					   g.addEdge(new Edge(posAct, posAct + largeur, coutAct));		//E "juste en dessous"
					   g.addEdge(new Edge(posAct, posAct + largeur - 1, coutAct));	//E à "gauche" du premier
				   } else {							//les E ont 3 V
					   g.addEdge(new Edge(posAct, posAct + largeur, coutAct));		//E "juste en dessous"
					   g.addEdge(new Edge(posAct, posAct + largeur + 1, coutAct));	//E à "droite" du precedent
					   g.addEdge(new Edge(posAct, posAct + largeur - 1, coutAct));	//E à "gauche" du premier
				   }
			   }
		   }
	   }
	   
	   return g;
   }
   
   public void writeFile(String s)
	 {
		try
		  {			 
			 PrintWriter writer = new PrintWriter(s, "UTF-8");
			 writer.println("digraph G{");
			 for (Edge e: edges())
			   writer.println(e.from + "->" + e.to + "[label=\"" + e.cost + "\"];");
			 writer.println("}");
			 writer.close();
		  }
		catch (IOException e)
		  {
			e.printStackTrace();
		  }						
	 }

public int getV() {
	return V;
}
   
}
