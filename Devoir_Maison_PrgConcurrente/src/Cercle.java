

import java.awt.Color;
import java.awt.Graphics;

public class Cercle {
	/*
	 * Attributs de cercle, respectivement : x la position x du centre, y la
	 * position y du centre, r le rayon du cercle, dx,dy les composantes de
	 * mouvement
	 */
	int x, y, r, dx, dy;
	Color color;
	
	/*Constructeur*/
	public Cercle(int xpos, int ypos, int rayon, Color c) {
		x = xpos;
		y = ypos;
		r = rayon;
		color = c;
		dx = dy = 2; // Arbitrairement de 2 pour un déplacement smooth
	}

	/* Accesseurs aux attributs*/
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/* Méthodes de modification des attributs de position */
	public void setXY(int xpos, int ypos) {
		x = xpos;
		y = ypos;
	}

	/* Méthode pour la gestion des collisions */
	public boolean collision(int xpos, int ypos) {
		int a = x - xpos;
		int b = y - ypos;

		if ((a * a) + (b * b) <= 2 * r * 2 * r) {
			return true;
		}
		return false;
	}

	/* Méthode pour les dessins de chaque cercle */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillArc(x, y, r * 2, r * 2, 0, 360);
	}

}