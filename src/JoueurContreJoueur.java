
public class JoueurContreJoueur {

	private int numero_joueur_1=1;
	private int numero_joueur_2=2;
	private Jeu jeu;
	
	public JoueurContreJoueur() {
		this.jeu=new Jeu();
		jeu.getPlateau().afficher();
		jouer();
	}

	public void jouer() {
		boolean victoire_joueur_1 = false;
		boolean victoire_joueur_2 = false;
		boolean grille_pleine = false;
		while(!victoire_joueur_1 && !victoire_joueur_2) {
			jeu.jouerUnCoup(numero_joueur_1);
			grille_pleine = jeu.getPlateau().verifierGrillePleine();
			if (grille_pleine==true) {
				System.out.println("La grille est pleine! Match nul.");
				break;
			}
			victoire_joueur_1=jeu.gagner(jeu.getPlateau(),numero_joueur_1);
			if (victoire_joueur_1) {
				System.out.println("Victoire du joueur "+numero_joueur_1+"!");
				break;
			}
			jeu.jouerUnCoup(numero_joueur_2);
			grille_pleine = jeu.getPlateau().verifierGrillePleine();
			if (grille_pleine==true) {
				System.out.println("La grille est pleine! Match nul.");
				break;
			}
			victoire_joueur_2=jeu.gagner(jeu.getPlateau(),numero_joueur_2);
			if (victoire_joueur_2) {
				System.out.println("Victoire du joueur "+numero_joueur_2+"!");
				break;
			}
		}
	}
	
}
