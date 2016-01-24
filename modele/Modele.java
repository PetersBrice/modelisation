package modelisation.modele;

import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;

import modelisation.graphique.ProgressPanneau;
import modelisation.traitement.SeamCarving;

public class Modele extends Observable {
	
	private int progression;
	private File fileOrigin;
	
	public Modele(){
		super();
		
		progression = 0;
	}
	
	public int getProgress(){
		return progression;
	}
	
	public void setProgress(int p){
		progression = p;
		this.setChanged();
		this.notifyObservers();
	}

	public static void main(String[] args) {
		
		JFrame jf = new JFrame("Projet Modelisation Peters-Debicki");
		
		Modele m = new Modele();
		
		jf.add(new ProgressPanneau(m));
		
		jf.pack();
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SeamCarving.mainActivity("modelisation/ex1.pgm", "wsh.pgm", m);
	}

}
