package modelisation.graphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import modelisation.modele.Modele;

public class RunPanneau extends JPanel implements Observer {

	Modele mod;
	JButton part1, part2;
	
	public RunPanneau(Modele m) {
		mod = m;
		
		mod.addObserver(this);
		
		part1 = new JButton("Lancer Algo Partie 1");
		part2 = new JButton("Lancer Algo Partie 2");

		part1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mod.partOneActivity();
			}
		});
		
		part2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mod.partTwoActivity();
			}
		});
		
		part1.setEnabled(false);
		part2.setEnabled(false);
		
		add(part1);
		add(part2);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (mod.isFileOriginChoosed()){
			part1.setEnabled(true);
		}

	}

}
