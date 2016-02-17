package modelisation.modele;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;

import modelisation.graphique.FileChoserPanneau;
import modelisation.graphique.ProgressPanneau;
import modelisation.graphique.RunPanneau;
import modelisation.traitement.SeamCarving;

public class Modele extends Observable{
	
	private int progression;
	private int[][] tabFinal;
	private int whichPart;
	
	private boolean running;

	private boolean taskFinished;
	private boolean fileOriginChoosed;
	
	private boolean supprimerLignes;
	
	private SeamCarving sc;
	
	private String fileOriginAbsPath;
	private String fileSaveAbsPath;
	

	public Modele(){
		super();
		
		fileOriginAbsPath = null;
		fileSaveAbsPath = null;
		
		progression = 0;
		whichPart = 0;
		
		taskFinished = false;
		fileOriginChoosed = false;
		
		supprimerLignes = false;
	}
	
	public void saveFile(){
		if (fileSaveAbsPath.endsWith(".ppm"))
			SeamCarving.writeppm(tabFinal, fileSaveAbsPath);
		else 
			SeamCarving.writepgm(tabFinal, fileSaveAbsPath);
	}
	
	public void partOneActivity(){
		sc = new SeamCarving(false, supprimerLignes, fileOriginAbsPath, this);
		Thread t = new Thread(sc, "SeamCarving");
		t.start();
		setRunning(true);
	}
	
	public void partTwoActivity(){
		sc = new SeamCarving(true, supprimerLignes, fileOriginAbsPath, this);
		Thread t = new Thread(sc, "SeamCarving");
		t.start();
		setRunning(true);
	}

	public String getFileSave() {
		return fileSaveAbsPath;
	}

	public void setFileSave(File fileSave) {
		this.fileSaveAbsPath = fileSave.getAbsolutePath();
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getFileOrigin() {
		return fileOriginAbsPath;
	}

	public void setFileOrigin(File fileOrigin) {
		this.fileOriginAbsPath = fileOrigin.getAbsolutePath();
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean isFileOriginChoosed() {
		return fileOriginChoosed;
	}

	public void setFileOriginChoosed(boolean fileOriginChoosed) {
		this.fileOriginChoosed = fileOriginChoosed;
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean isTaskFinished(){
		return taskFinished;
	}
	
	public void setTaskFinished(boolean tf){
		taskFinished = tf;
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean isSupprimerLignes() {
		return supprimerLignes;
	}

	public void setSupprimerLignes(boolean supprimerLignes) {
		this.supprimerLignes = supprimerLignes;
	}

	public int getProgress(){
		return progression;
	}
	
	public void setProgress(int p){
		progression = p;
		this.setChanged();
		this.notifyObservers();
	}
	
	public int[][] getTabFinal() {
		return tabFinal;
	}

	public void setTabFinal(int[][] tabFinal) {
		this.tabFinal = tabFinal;
		this.setChanged();
		this.notifyObservers();
	}
	
	

	
}
