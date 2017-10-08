

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MaJFenetre extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Variable Importante
	public final int nbboules = 8; // Nombre de balles totales sur l'écran

	// Autres attributs
	boolean startstop = true; //Activation ou non du bouton start et stop
	Cercle[] liste_cercle = new Cercle[nbboules]; // Stock les balles
	int nb_actu_boules = 0; // Nombre de boules actuellement sur l'écran
	int score = 0; // Le score
	boolean sessionstart = true; //Premier démarrage de la session
	Horloge h;

	// Composantes d'interface
	JButton start;
	JButton pluss;
	JButton minus;

	JLabel score_tab;
	JLabel timing;

	/* Constructeur de la fenêtre */
	public MaJFenetre() {
		super("Balles en mouvement");
		setSize(400, 600);
		setLocation(100, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Initilialisation des buttons et labels
		start = new JButton("Start");
		pluss = new JButton("+");
		minus = new JButton("-");
		score_tab = new JLabel("Score : 0");
		timing = new JLabel("Time : 00:00");
		h = new Horloge(timing);

		// Ecouteurs des boutons
		start.addActionListener(this);
		pluss.addActionListener(this);
		minus.addActionListener(this);

		// Ajout dans les conteneurs
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());

		// Score
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		panel.add(score_tab);
		panel.add(timing);
		pane.add(panel, BorderLayout.NORTH);

		// Ligne de commandes
		JPanel panel_button = new JPanel();
		panel_button.setLayout(new GridLayout(1, 3));
		panel_button.add(start);
		panel_button.add(pluss);
		panel_button.add(minus);
		minus.setEnabled(false);
		pane.add(panel_button, BorderLayout.SOUTH);

		setVisible(true);

	}
	
	/*Fonction de dessin*/
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < nb_actu_boules; i++) {
			if (liste_cercle[i] != null) {
				liste_cercle[i].draw(g);
			}
		}
	}

	/* Threads pour le déplacement et les collisions */
	public void movingball() {
		Thread thread = new Thread() {
			public void run() {
				while (true) {
					// Gérer la désactivation du bouton - dans le cas d'une
					// collision
					if (nb_actu_boules != 0) {
						minus.setEnabled(true);
					}
					if (nb_actu_boules == 0) {
						minus.setEnabled(false);
					}
					if (nb_actu_boules == nbboules) {
						pluss.setEnabled(false);
					}
					if (nb_actu_boules >= 0 && nb_actu_boules < nbboules) {
						pluss.setEnabled(true);
					}

					int X, Y;

					for (int i = 0; i < nb_actu_boules && liste_cercle[i] != null && !startstop; i++) {
						// Déplacement
						X = liste_cercle[i].getX();
						Y = liste_cercle[i].getY();

						if (X + 75 > 400 || X - 75 < 0) {
							liste_cercle[i].dx = -liste_cercle[i].dx;
						}
						if (Y + 75 > 575 || Y - 75 < 0) {
							liste_cercle[i].dy = -liste_cercle[i].dy;
						}

						X = X + liste_cercle[i].dx;
						Y = Y + liste_cercle[i].dy;

						liste_cercle[i].setXY(X, Y);

						// Collision
						for (int j = 0; j < nb_actu_boules; j++) {

							int xpos, ypos;

							if (liste_cercle[j] != null && liste_cercle[i] != null) {
								xpos = liste_cercle[j].getX();
								ypos = liste_cercle[j].getY();

								if (liste_cercle[i].collision(xpos, ypos) && i != j) {

									System.out.println("Boules " + i + " " + j + " entrées en colli");

									for (int k = i; k < nb_actu_boules - 1; k++) {
										liste_cercle[k] = liste_cercle[k + 1];
									}

									for (int l = j; l < nb_actu_boules - 1; l++) {
										liste_cercle[l] = liste_cercle[l + 1];
									}

									liste_cercle[nb_actu_boules - 1] = null;
									liste_cercle[nb_actu_boules - 2] = null;

									nb_actu_boules = nb_actu_boules - 2;

									System.out.println("Boules " + nb_actu_boules);

									// Modification du score total
									score++;
									score_tab.setText("Score : " + score);
								}

							}
						}

						repaint();
					}
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
					}
				}
			}

		};

		if (sessionstart) {
			h.start();
			thread.start();
			sessionstart = false;
		}
	}

	/* Gestion des boutons */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == start) {
			if (startstop) {
				/*Quand le bouton Start est pressé*/
				h.setpause(false);
				movingball();
				startstop = false;
				start.setText("Stop");
				
			} else {
				/*Quand le bouton avec le label stop est pressé*/
				startstop = true;
				h.setpause(true);
				start.setText("Start");
			}

		} else if (source == pluss) {
			int x, y, cred, cblue, cgreen;
			Cercle ball;

			// Spawn random de la boule
			x = (int) (Math.random() * 270 + 75);
			y = (int) (Math.random() * 370 + 75);

			// Vérification double spawn
			boolean check = true; // état de vérification
			boolean tour = false; // si une vérification a été faites ce tour-ci

			dance: while (check) {
				for (int j = 0; j < nb_actu_boules && liste_cercle[j] != null; j++) {
					if (liste_cercle[j].collision(x, y)) { // Si collision
						x = (int) (Math.random() * 270 + 75);
						y = (int) (Math.random() * 370 + 75);
						tour = true;
					}
				}
				if (!tour) {
					break dance; // on break si aucune vérification n'a été
									// faites
				}
				tour = false;
			}

			// Couleurs random
			cred = (int) (Math.random() * 255);
			cblue = (int) (Math.random() * 255);
			cgreen = (int) (Math.random() * 255);

			// Ajout de la balle au pool de balles
			ball = new Cercle(x, y, 25, new Color(cred, cblue, cgreen));
			liste_cercle[nb_actu_boules] = ball;
			nb_actu_boules++;

			// Affichage ou non des boutons
			if (nb_actu_boules == 8) {
				pluss.setEnabled(false);
			}
			if (nb_actu_boules > 0) {
				minus.setEnabled(true);
			}

			repaint();
			
		} else if (source == minus) {
			/*Gestion du bouton -*/
			liste_cercle[nb_actu_boules - 1] = null;
			nb_actu_boules--;
			if (nb_actu_boules == 0) {
				minus.setEnabled(false);
			}
			pluss.setEnabled(true);
			repaint();
		}
	}
}


