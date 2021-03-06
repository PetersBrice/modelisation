package modelisation.traitement;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import modelisation.graphique.FileChoserPanneau;
import modelisation.graphique.ProgressPanneau;
import modelisation.graphique.RunPanneau;
import modelisation.modele.Modele;

class Test {
	
   static boolean visite[];
   public static void dfs(Graph g, int u)
	 {
		visite[u] = true;
		System.out.println("Je visite " + u);
		for (Edge e: g.next(u))
		  if (!visite[e.to])
			dfs(g,e.to);
	 }

   public static void testHeap()
	 {
		// Crée ue file de priorité contenant les entiers de 0 à 9, tous avec priorité +infty
		Heap h = new Heap(10);
		h.decreaseKey(3,1664);
		h.decreaseKey(4,5);
		h.decreaseKey(3,8);
		h.decreaseKey(2,3);
		// A ce moment, la priorité des différents éléments est:
		// 2 -> 3
		// 3 -> 8
		// 4 -> 5
		// tout le reste -> +infini
		int x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		// La file contient maintenant uniquement les éléments 0,1,5,6,7,8,9 avec priorité +infini
	 }
   
   public static void testGraph()
	 {
		int n = 5;
		int i,j;
		Graph g = new Graph(n*n+2);
		
		for (i = 0; i < n-1; i++)
		  for (j = 0; j < n ; j++)
			g.addEdge(new Edge(n*i+j, n*(i+1)+j, 1664 - (i+j)));

		for (j = 0; j < n ; j++)		  
		  g.addEdge(new Edge(n*(n-1)+j, n*n, 666));
		
		for (j = 0; j < n ; j++)					
		  g.addEdge(new Edge(n*n+1, j, 0));
		
		g.addEdge(new Edge(13,17,1337));
		g.writeFile("test.dot");
		// dfs à partir du sommet 3
		visite = new boolean[n*n+2];
		dfs(g, 3);
	 }
   
   public static void main(String[] args)
	 {
		//testHeap();
		//testGraph();
	   /*int[][] tab;
	   int[][] interest;
	   StringBuilder sb = new StringBuilder();
	   tab = SeamCarving.readpgm("test.pgm");
	   for(int i =0; i < tab.length;i++){
		   for(int j = 0;j<tab[i].length;j++){
			   sb.append(""+tab[i][j]+" ");
		   }   
		   sb.append("\n");
	   }
	   interest = SeamCarving.interest(tab);
	   StringBuilder si = new StringBuilder();
	   for(int i =0; i < interest.length;i++){
		   for(int j = 0;j<interest[i].length;j++){
			   si.append(""+interest[i][j]+" ");
		   }   
		   si.append("\n");
	   }
	   
	   System.out.println(sb);
	   System.out.println(si);
	   Graph testGraph = Graph.tograph(interest);
	   testGraph.writeFile("test.dot");
	   
	   System.out.println(Parcours.dijkstra(testGraph,12,13));
	   
	   SeamCarving.writepgm(tab, "lol.pgm");*/
	   
	   //SeamCarving.mainActivity("modelisation/ex1.pgm", "wsh.pgm");


	   JFrame jf = new JFrame("Projet Modelisation Peters-Debicki");
		
		Modele m = new Modele();
		
		jf.add(new ProgressPanneau(m), BorderLayout.SOUTH);
		jf.add(new FileChoserPanneau(m), BorderLayout.NORTH);
		jf.add(new RunPanneau(m), BorderLayout.CENTER);
		
		jf.pack();
		jf.setVisible(true);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	   
	   int[][] tab;
	   int[][] interest;
	   tab = SeamCarving.readpgm("lol.pgm");
	   interest = SeamCarving.interest(tab);
	  
	   StringBuilder sb = new StringBuilder();
	   for(int i =0; i < tab.length;i++){
		   for(int j = 0;j<tab[i].length;j++){
			   sb.append(""+tab[i][j]+" ");
		   }   
		   sb.append("\n");
	   }
	   
	   System.out.println(sb.toString());
	   
	   StringBuilder sbl = new StringBuilder();
	   for(int i =0; i < interest.length;i++){
		   for(int j = 0;j<interest[i].length;j++){
			   sbl.append(""+interest[i][j]+" ");
		   }   
		   sbl.append("\n");
	   }
	   
	   System.out.println(sbl.toString());
	   
	   Graph g = Graph.tograph2(interest);
	   
	   g.writeFile("jjjjeeej.dot");
	   
	   ArrayList<Integer> ali = new ArrayList<>();
	   
	   ali = Parcours.dijkstra(g, g.vertices() - 2, g.vertices() - 1);
	   
	   System.out.println(ali);
	   
	   /*for(int i =0; i < gris.length;i++){
		   for(int j = 0;j<gris[i].length;j++){
			   sb.append(""+gris[i][j]+" ");
		   }   
		   sb.append("\n");
	   }
	   for(int i =0; i < interest.length;i++){
		   for(int j = 0;j<interest[i].length;j++){
			   si.append(""+interest[i][j]+" ");
		   }   
		   si.append("\n");
	   }
	   
	   ArrayList<Integer> ali = new ArrayList<>();
	   
	   ali = Parcours.dijkstra(testGraph, testGraph.vertices() - 2, testGraph.vertices() - 1);
	   int hauteur = tab.length;
	   int largeur = tab[0].length ;
	   int [][]ntab=  new int [hauteur][largeur - 3];
	   
	   System.out.println("hauteur: "+hauteur);
	   System.out.println("largeur: "+largeur);
	   System.out.println(ali);
	   
	   for (int h = 0; h < hauteur; h++){ 			//pour chaque ligne
		   
		   int l = 0;
		   
		   boolean fin = false;
		   
		   while (!fin){
			   
			   int posAct = (largeur/3 * h) + l;
			   
			   if (ali.contains(posAct)){
				   System.out.println(posAct);
				   for (int nl = l + 3; nl < largeur; nl++)
					   ntab[h][nl - 3] = tab[h][nl];
				   fin = true;
			   } else {
				   ntab[h][l] = tab[h][l];
			   }
			   
			   l++;
		   }
	   }
	   SeamCarving.writeppm(ntab, "lol.ppm");*/
	 }
   
   
}
