package modelisation.graphique;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import modelisation.modele.Modele;

public class ProgressPanneau extends JPanel implements Observer {

	private Modele m;
	private JProgressBar jpb;
	private JLabel jl;
	private int troisPetitsPoints;
	
	public ProgressPanneau(Modele mod) {
		this.m = mod;
		
		m.addObserver(this);
		
		troisPetitsPoints = 0;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		jpb = new JProgressBar();
		jpb.setMaximum(100);
		jpb.setMinimum(0);
		
		jl = new JLabel("");
		
		add(jpb);
		add(jl);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		jpb.setValue(m.getProgress());
		
		if (m.isRunning()){
			switch (troisPetitsPoints) {
			case 0:
				jl.setText("En cours.");
				troisPetitsPoints = 1;
				break;
			case 1:
				jl.setText("En cours..");
				troisPetitsPoints = 2;
				break;
			case 2:
				jl.setText("En cours...");
				troisPetitsPoints = 3;
				break;
			case 3:
				jl.setText("En cours");
				troisPetitsPoints = 0;
				break;
			}
		} else {
			if (m.isTaskFinished()){
				jl.setText("Fini !");
			} else {
				jl.setText("");
			}
			
			troisPetitsPoints = 0;
		}
		
		this.repaint();
	}

}
