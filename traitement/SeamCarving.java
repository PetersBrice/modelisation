package modelisation.traitement;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class SeamCarving {

   public static int[][] readpgm(String fn)
	 {		
        try {
            InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(f));
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
   
   public void writepgm(int[][] image, String filename){
	  try{
		  File f = new File(filename);
		  FileWriter fw = new FileWriter(f);
		  BufferedWriter bf = new BufferedWriter(fw);
		  bf.write("P2\n");
		  bf.write(image.length+" "+image[0].length);
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
   
   public int[][] interest(int[][] image){
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
	   
}

   

