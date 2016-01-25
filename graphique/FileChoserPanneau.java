package modelisation.graphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import modelisation.modele.Modele;

public class FileChoserPanneau extends JPanel implements Observer{
	
	private Modele mod;
	private JFileChooser jfc;
	private JButton openButton, saveButton;
	
	public FileChoserPanneau(Modele m){
		
		mod = m;
		
		mod.addObserver(this);
		
		jfc = new JFileChooser();
		
		openButton = new JButton("Open File...");
		saveButton = new JButton("Save File...");
		
		openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = jfc.showOpenDialog(FileChoserPanneau.this);
				 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                mod.setFileOrigin(jfc.getSelectedFile());
	                mod.setFileOriginChoosed(true);
	            }
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = jfc.showSaveDialog(FileChoserPanneau.this);
				 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                mod.setFileSave(jfc.getSelectedFile());
	                mod.saveFile();
	            }
			}
		});
		
		saveButton.setEnabled(false);
		openButton.setEnabled(true);
		
		add(openButton);
		add(saveButton);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (mod.isTaskFinished()){
			saveButton.setEnabled(true);
		} else {
			saveButton.setEnabled(false);
		}
		
	}

}
