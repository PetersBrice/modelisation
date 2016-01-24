package modelisation.graphique;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import modelisation.modele.Modele;

public class ProgressPanneau extends JPanel implements Observer {

	private Modele m;
	private JProgressBar jpb;
	
	public ProgressPanneau(Modele mod) {
		this.m = mod;
		
		m.addObserver(this);
		
		jpb = new JProgressBar();
		jpb.setMaximum(100);
		jpb.setMinimum(0);
		
		add(jpb);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		jpb.setValue(m.getProgress());
		this.repaint();
	}

}
