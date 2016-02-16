package modelisation.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import modelisation.modele.Modele;

public class SeamCarving implements Runnable {

	private boolean partTwo;
	private boolean horizontal;
	
	private boolean isPPM;
	
	private int[][] tabOrigine;
	
	private Modele m;
	
	
	public SeamCarving(boolean isTwo, boolean isHoriz, String fileSourceName, Modele mod){
		
		partTwo = isTwo;
		horizontal = isHoriz;

		m = mod;
		
		isPPM = fileSourceName.endsWith(".ppm");
		   
		if (isPPM)
			tabOrigine = readppm(fileSourceName);
		else
			tabOrigine = readpgm(fileSourceName);
	}
	
	public static int[][] readpgm(String fn){		
        
		try {
        	FileInputStream fis = new FileInputStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(fis));
            String magic = d.readLine();
            String line = d.readLine();
            
		   while (line.startsWith("#")) {
			  line = d.readLine();
		   }
		   Scanner s = new Scanner(line);
		   int width = s.nextInt();
		   int height = s.nextInt();		   
		   line = d.readLine();
		   s = new Scanner(line);
		   int maxVal = s.nextInt();
		   int[][] im = new int[height][width];
		   s = new Scanner(d);
		   int count = 0;
		   while (count < height*width) {
			  im[count / width][count % width] = s.nextInt();
			  count++;
		   }
		   return im;
        }
		
        catch(Throwable t) {
            t.printStackTrace(System.err) ;
            return null;
        }
	}
   
	public static int[][] readppm(String fn){
		
      try {
      	FileInputStream fis = new FileInputStream(fn);
          BufferedReader d = new BufferedReader(new InputStreamReader(fis));
          String magic = d.readLine();
          String line = d.readLine();
          
		   while (line.startsWith("#")) {
			  line = d.readLine();
		   }
		   Scanner s = new Scanner(line);
		   int width = s.nextInt();
		   int height = s.nextInt();		   
		   line = d.readLine();
		   s = new Scanner(line);
		   int maxVal = s.nextInt();
		   int[][] im = new int[height][width*3];
		   s = new Scanner(d);
		   int count = 0;
		   while (count < height*width*3) {
			  im[count / (width*3)][count % (width*3)] = s.nextInt();
			  count++;
		   }
		   return im;
      }
		
      catch(Throwable t) {
          t.printStackTrace(System.err) ;
          return null;
      }
	}
   
	
   
	public static void writepgm(int[][] image, String filename){
		
	  try{
		  File f = new File(filename);
		  FileWriter fw = new FileWriter(f);
		  BufferedWriter bf = new BufferedWriter(fw);
		  bf.write("P2\n");
		  bf.write(image[0].length+" "+image.length);
		  bf.write("\n255\n");
		  for(int i = 0;i < image.length;i++){
			  for(int j = 0;j < image[i].length;j++){
				  bf.write(image[i][j]+" ");
			  }
			  bf.write("\n");
		  }
		  bf.flush();
		  bf.close();
		  fw.close();
	  }catch(IOException ioe){
		  System.out.print("Erreur : ");
		  ioe.printStackTrace();
	  }		
   }
   
   public static void writeppm(int[][] image, String filename){
		  try{
			  File f = new File(filename);
			  FileWriter fw = new FileWriter(f);
			  BufferedWriter bf = new BufferedWriter(fw);
			  bf.write("P3\n");
			  bf.write(image[0].length/3+" "+image.length);
			  bf.write("\n255\n");
			  for(int i = 0;i < image.length;i++){
				  for(int j = 0;j < image[i].length;j++){
					  bf.write(image[i][j]+" ");
				  }
				  bf.write("\n");
			  }
			  bf.flush();
			  bf.close();
			  fw.close();
		  }catch(IOException ioe){
			  System.out.print("Erreur : ");
			  ioe.printStackTrace();
		  }		
	   }
   
   public static int[][] interest(int[][] image){
	   int moyenne;
	   int interestVal;
	   int length = image.length;
	   int width = image[0].length;
	   int[][] interest = new int[length][width];
	   
	   //on remplie le tableau
	   for(int i = 0; i < length;i++){
		   for(int j = 0; j < width;j++){
			   //si on est en fin de ligne
			   if(j == width-1){
				  interestVal = Math.abs(image[i][j]-image[i][j-1]);
			   }else{
				   //si on est en debut de ligne
				   if(j==0){
					   interestVal = Math.abs(image[i][j]-image[i][j+1]);
				   }else{
					   moyenne = (image[i][j-1]+image[i][j+1])/2;
					   interestVal = Math.abs(image[i][j]-moyenne);
				   }
			   }
			   
			   interest[i][j] = interestVal;
		   }
	   }
	   return interest;
   }
   
public static void voilaPourquoiJeVoulaisRefaireSeamCarvingEtQueTuFassePlusRien(String filesourcename, Modele m){
	   
	   boolean formatPPM = filesourcename.endsWith(".ppm");
	   boolean addligne = true;
	   
	   int[][] tabOrigine;
	   
	   if (formatPPM)
		   tabOrigine = readppm(filesourcename);
	   else
		   tabOrigine = readpgm(filesourcename);
	   
	   int[][] tab;
	   
	   m.setTaskFinished(false);
	   
	   for (int i = 0; i < 50; i++){
			   
		   int hauteur = tabOrigine.length;
		   int largeur = tabOrigine[0].length;
		   
		   Graph g;
		   
		   if(!addligne){
			   if (formatPPM){
				   int[][] gris = SeamCarving.ppmToPgm(tabOrigine);
				   g = Graph.tograph(interest(gris));
				   tab = new int[hauteur][largeur - 3];
			   } else {
				   g = Graph.tograph(interest(tabOrigine)); 
				   tab = new int[hauteur][largeur - 1];
			   }
		   }else{
			   if (formatPPM){
				   int[][] gris = SeamCarving.ppmToPgm(tabOrigine);
				   g = Graph.tograph(interest(gris));
				   tab = new int[hauteur][largeur + 3];
			   } else {
				   g = Graph.tograph(interest(tabOrigine)); 
				   tab = new int[hauteur][largeur + 1];
			   }
		   }
		   
		   ArrayList<Integer> ali = new ArrayList<>();
		   
		   ali = Parcours.dijkstra(g, g.vertices() - 2, g.vertices() - 1);
		   
		   for (int h = 0; h < hauteur; h++){ 			//pour chaque ligne
			   
			   int l = 0;
			   
			   boolean fin = false;
			   
			   while (!fin){
				    
				   int posAct;
				   
				   if (formatPPM)
					   posAct = (largeur/3 * h) + l;
				   else
					   posAct = (largeur * h) + l;
				   
				   if (ali.contains(posAct)){
					   
					   if (formatPPM){
						   if(addligne){
							 for(int j =0; j < 3; j ++){
								 tab[h][l+j]= (int) Math.round(tabOrigine[h][l-3+j]*0.25+0.75*tabOrigine[h][l+3+j]);
								 tab[h][l+3+j]= (int) Math.round(tabOrigine[h][l-3+j]*0.75+0.25*tabOrigine[h][l+3+j]);
							 }
							   for (int nl = l + 9; nl < largeur; nl++)
								   tab[h][nl - 3] = tabOrigine[h][nl];
						   }else{
						   for (int nl = l + 3; nl < largeur; nl++)
							   tab[h][nl - 3] = tabOrigine[h][nl];
						   }
					   } else {
						   if(addligne){
							   tab[h][l] = (int) Math.round(tabOrigine[h][l-1]*0.25+0.75*tabOrigine[h][l+1]);
							   tab[h][l+1] = (int) Math.round(tabOrigine[h][l-1]*0.75+0.25*tabOrigine[h][l+1]);
							   for (int nl = l + 3; nl < largeur; nl++){
								   tab[h][nl - 1] = tabOrigine[h][nl];
							   }
						   }else{
							   for (int nl = l + 1; nl < largeur; nl++){
								   tab[h][nl - 1] = tabOrigine[h][nl];
							}  
						   }
					   }
					   
					   fin = true;
				   } else {
					   tab[h][l] = tabOrigine[h][l];
				   }
				   
				   l++;
			   }
		   }
		   
		   tabOrigine = tab;
		   m.setProgress((i + 1) * 2);
	   }
	   
	   m.setTabFinal(tabOrigine);
	   
	   m.setTaskFinished(true);
	   m.setRunning(false);
   }
   

   public static int [][] ppmToPgm(int[][] color){
	   int[][] gris = new int [color.length][color[0].length/3];
	   
	   for(int i = 0; i < gris.length;i++){
		   for(int j = 0; j < gris[0].length;j++){
			   //gris[i][j] = (int) (0.2126 * (float)color[i][j] + 0.7152 * (float)color[i][j+1] + 0.0722 * (float)color[i][j+2]);
			   gris[i][j] = (int) ((color[i][j] + color[i][j+1] + color[i][j+2]) / 3);
		   }
	   }
	   
	   return gris;   
   }
   
   
   @Override
   public void run(){
	   System.out.println("lllll");
	   if (partTwo){
		   if (horizontal)
			   ;
		   else
			   ;
	   } else {
		   System.out.println("jj");
		   if (horizontal)
			   ;
		   else
			   firstPartActivityVertical();
	   }
   }
   
   
public static void firstPartActivityAvant(String filesourcename, Modele m){
	   
	   boolean formatPPM = filesourcename.endsWith(".ppm");
	   
	   int[][] tabOrigine;
	   
	   if (formatPPM)
		   tabOrigine = readppm(filesourcename);
	   else
		   tabOrigine = readpgm(filesourcename);
	   
	   int[][] tab;
	   
	   m.setTaskFinished(false);
	   
	   for (int i = 0; i < 50; i++){
			   
		   int hauteur = tabOrigine.length;
		   int largeur = tabOrigine[0].length;
		   
		   Graph g;
		   
		   if (formatPPM){
			   int[][] gris = SeamCarving.ppmToPgm(tabOrigine);
			   g = Graph.tograph(interest(gris));
			   tab = new int[hauteur][largeur - 3];
		   } else {
			   g = Graph.tograph(interest(tabOrigine)); 
			   tab = new int[hauteur][largeur - 1];
		   }
		   
		   ArrayList<Integer> ali = new ArrayList<>();
		   
		   ali = Parcours.dijkstra(g, g.vertices() - 2, g.vertices() - 1);
		   
		   for (int h = 0; h < hauteur; h++){ 			//pour chaque ligne
			   
			   int l = 0;
			   
			   boolean fin = false;
			   
			   while (!fin){
				    
				   int posAct;
				   
				   if (formatPPM)
					   posAct = (largeur/3 * h) + l;
				   else
					   posAct = (largeur * h) + l;
				   
				   if (ali.contains(posAct)){
					   
					   if (formatPPM){
						   for (int nl = l + 3; nl < largeur; nl++)
							   tab[h][nl - 3] = tabOrigine[h][nl];
					   } else {
						   for (int nl = l + 1; nl < largeur; nl++)
							   tab[h][nl - 1] = tabOrigine[h][nl];
					   }
					   
					   fin = true;
				   } else {
					   tab[h][l] = tabOrigine[h][l];
				   }
				   
				   l++;
			   }
		   }
		   
		   tabOrigine = tab;
		   m.setProgress((i + 1) * 2);
	   }
	   
	   m.setTabFinal(tabOrigine);
	   
	   m.setTaskFinished(true);
	   m.setRunning(false);
   }
   
   public void firstPartActivityVertical(){
	   
	   int[][] tab;
	   
	   m.setTaskFinished(false);
	   
	   for (int i = 0; i < 50; i++){
			   
		   int hauteur = tabOrigine.length;
		   int largeur = tabOrigine[0].length;
		   
		   Graph g;
		   
		   if (isPPM){
			   int[][] gris = SeamCarving.ppmToPgm(tabOrigine);
			   g = Graph.tograph(interest(gris));
			   tab = new int[hauteur][largeur - 3];
		   } else {
			   g = Graph.tograph(interest(tabOrigine)); 
			   tab = new int[hauteur][largeur - 1];
		   }
		   
		   ArrayList<Integer> ali = new ArrayList<>();
		   
		   ali = Parcours.dijkstra(g, g.vertices() - 2, g.vertices() - 1);
		   
		   for (int h = 0; h < hauteur; h++){ 			//pour chaque ligne
			   
			   int l = 0;
			   
			   boolean fin = false;
			   
			   while (!fin){
				    
				   int posAct;
				   
				   if (isPPM)
					   posAct = (largeur/3 * h) + l;
				   else
					   posAct = (largeur * h) + l;
				   
				   if (ali.contains(posAct)){
					   
					   if (isPPM){
						   for (int nl = l + 3; nl < largeur; nl++)
							   tab[h][nl - 3] = tabOrigine[h][nl];
					   } else {
						   for (int nl = l + 1; nl < largeur; nl++)
							   tab[h][nl - 1] = tabOrigine[h][nl];
					   }
					   
					   fin = true;
				   } else {
					   tab[h][l] = tabOrigine[h][l];
				   }
				   
				   l++;
			   }
		   }
		   
		   tabOrigine = tab;
		   m.setProgress((i + 1) * 2);
	   }
	   
	   m.setTabFinal(tabOrigine);
	   
	   m.setTaskFinished(true);
	   m.setRunning(false);
   }
	   
   public void firstPartActivityHorizontal(){
	   
	   int[][] tab;
	   
	   m.setTaskFinished(false);
	   
	   for (int i = 0; i < 50; i++){
			   
		   int hauteur = tabOrigine.length;
		   int largeur = tabOrigine[0].length;
		   
		   Graph g;
		   
		   // a voir a partir de là
		   if (isPPM){
			   int[][] gris = SeamCarving.ppmToPgm(tabOrigine);
			   g = Graph.tograph(interest(gris));
			   tab = new int[hauteur][largeur - 3];
		   } else {
			   g = Graph.tograph(interest(tabOrigine)); 
			   tab = new int[hauteur][largeur - 1];
		   }
		   
		   ArrayList<Integer> ali = new ArrayList<>();
		   
		   ali = Parcours.dijkstra(g, g.vertices() - 2, g.vertices() - 1);
		   
		   //a changer pour les lignes
		   for (int h = 0; h < hauteur; h++){ 			//pour chaque ligne
			   
			   int l = 0;
			   
			   boolean fin = false;
			   
			   while (!fin){
				    
				   int posAct;
				   
				   if (isPPM)
					   posAct = (largeur/3 * h) + l;
				   else
					   posAct = (largeur * h) + l;
				   
				   if (ali.contains(posAct)){
					   
					   if (isPPM){
						   for (int nl = l + 3; nl < largeur; nl++)
							   tab[h][nl - 3] = tabOrigine[h][nl];
					   } else {
						   for (int nl = l + 1; nl < largeur; nl++)
							   tab[h][nl - 1] = tabOrigine[h][nl];
					   }
					   
					   fin = true;
				   } else {
					   tab[h][l] = tabOrigine[h][l];
				   }
				   
				   l++;
			   }
		   }
		   
		   tabOrigine = tab;
		   m.setProgress((i + 1) * 2);
	   }
	   
	   m.setTabFinal(tabOrigine);
	   
	   m.setTaskFinished(true);
	   m.setRunning(false);
   }
   
  
   public void secondPartActivityVertical(){
	   
	   int[][] tab;
	   
	   
	   m.setTaskFinished(false);
	   
	   for (int i = 0; i < 50; i++){
		   
		   //a finir
		   
		   /*
		   tab = SeamCarving.readpgm("src/test.pgm");
		   interest = SeamCarving.interest(tab);
		   Graph testGraph = Graph.tograph2(interest);
		   testGraph.writeFile("test2.txt");
		   Parcours.twograph(testGraph, testGraph.vertices() - 2, testGraph.vertices() - 1);*/
	   
	   }
	   
   }
   
   public void secondPartActivityHorizontal(){
	   
   }
   
}

   

