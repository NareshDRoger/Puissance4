import java.util.Random;
import java.util.Scanner;

public class JoueurContreIA {

	private int numero_joueur_1 = 1;
	private int numero_joueur_IA = 2;
	private Jeu jeu;
	
	public JoueurContreIA() {
		this.jeu = new Jeu();
		jouer();
	}
	
	public void jouer() {
		int niveau_de_difficulte = -1;
		Scanner sc = new Scanner(System.in);
		while (niveau_de_difficulte<1 || niveau_de_difficulte>3) {
			System.out.println("Choisissez un niveau de difficulté de 1 à 3 (1 étant le niveau facile, 2 étant le niveau normal et 3 le niveau difficile)");
			niveau_de_difficulte = sc.nextInt();
		}
		boolean victoire_joueur_1 = false;
		boolean victoire_joueur_2 = false;
		while(!victoire_joueur_1 && !victoire_joueur_2) {
			jeu.jouerUnCoup(numero_joueur_1);
			victoire_joueur_1=jeu.gagner(jeu.getPlateau().getGrille(),numero_joueur_1);
			if (victoire_joueur_1) {
				System.out.println("Victoire du joueur "+numero_joueur_1+"!");
				break;
			}
			if (niveau_de_difficulte == 1) 
				jouerContreIANiveauFacile(numero_joueur_IA);
			else if (niveau_de_difficulte == 2) {
				
			}
			else {
				
			}
			victoire_joueur_2=jeu.gagner(jeu.getPlateau().getGrille(),numero_joueur_IA);
			if (victoire_joueur_2) {
				System.out.println("Victoire du joueur "+numero_joueur_IA+"!");
				break;
			}
		}
	}
	
	
	public void jouerContreIANiveauFacile(int numero_joueur) {
		Random random = new Random();
		System.out.println("Au tour du joueur "+numero_joueur);
		//int numero_colonne = jeu.saisirColonne();
		int numero_colonne = -1;
		int numero_ligne = -1;
		while (numero_ligne==-1) {
			numero_colonne = random.nextInt((5)+1);
			numero_ligne = jeu.getPlateau().determinerLigne(numero_colonne);
		}
		jeu.getPlateau().modifierGrille(numero_colonne, numero_joueur);
		jeu.ajouterDernierDisque(numero_joueur);
		System.out.println("L'IA a placé le disque dans la colonne "+(numero_colonne+1));
		jeu.getPlateau().afficher();
	}
}
