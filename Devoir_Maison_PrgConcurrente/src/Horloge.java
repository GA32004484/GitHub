

import javax.swing.JLabel;

public class Horloge extends Thread {
	JLabel timing;
	int minutes = 0;
	int secondes = 0;
	boolean pause = false; // Pour synchroniser l'arrêt de l'horloge avec le
							// bouton de l'interface

	/* Constructeur */
	public Horloge(JLabel timing1) {
		timing = timing1;
	}

	/* Edite la valeur du booleen de l'attribut pause de la classe Horloge */
	public void setpause(boolean pause1) {
		pause = pause1;
	}

	/* Méthode pour le démarrage du thread */
	public void run() {
		while (true) {
			try {
				sleep(1000);
				if (!pause) {
					secondes += 1;
					timing.setText("Time :   " + minutes + " :   " + secondes);
					if (secondes == 60) {
						minutes += 1;
						secondes = 0;
						timing.setText("Time :   " + minutes + " :   " + secondes);
					}
				}
			} catch (InterruptedException e) {

			}

		}
	}

}
