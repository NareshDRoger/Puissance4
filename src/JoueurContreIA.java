import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JoueurContreIA {

	private int numero_joueur_1 = 1;
	private int numero_joueur_IA = 2;
	private Jeu jeu;
	private int longueur_alignement = 4;
	
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
		jeu.getPlateau().afficher();
		boolean victoire_joueur_1 = false;
		boolean victoire_joueur_2 = false;
		while(!victoire_joueur_1 && !victoire_joueur_2) {
			jeu.jouerUnCoup(numero_joueur_1);
			victoire_joueur_1=jeu.gagner(jeu.getPlateau(),numero_joueur_1);
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
			victoire_joueur_2=jeu.gagner(jeu.getPlateau(),numero_joueur_IA);
			if (victoire_joueur_2) {
				System.out.println("Victoire du joueur "+numero_joueur_IA+"!");
				break;
			}
		}
	}
	
	public int evaluer_alignement(List<Integer> alignement,int numero_joueur) {
		int score=0;
		int numero_joueur_adverse = -1;
		if (numero_joueur==1)
			numero_joueur_adverse=2;
		else 
			numero_joueur_adverse=1;
		int compteur_cases_joueur=0;
		int compteur_cases_joueur_adverse=0;
		int compteur_cases_vides=0;
		for (int i=0; i<alignement.size(); i++) {
			if (alignement.get(i)==numero_joueur)
				compteur_cases_joueur++;
			else if (alignement.get(i)==numero_joueur_adverse)
				compteur_cases_joueur_adverse++;
			else 
				compteur_cases_vides++;
		}
		if (compteur_cases_joueur == 4)
			score+=100;
		else if (compteur_cases_joueur==3 && compteur_cases_vides==1)
			score +=5;
		else if (compteur_cases_joueur==2 && compteur_cases_vides==2)
			score +=2;
		if (compteur_cases_joueur_adverse==3 && compteur_cases_vides==1)
			score-=4;
		return score;	
	}
	
	public void score_position (int[][] grille, int numero_joueur) {
		int score = 0;
		//Score sur la colonne centrale
		
		//Score à l'horizontale
		List<Integer> alignement = new ArrayList<Integer>();
		for (int numero_ligne=0;numero_ligne<grille.length;numero_ligne++) {
			//
			for (int numero_colonne=0;numero_colonne<((grille[0].length)-3);numero_colonne++) {
				//alignement.add
			}
		}
	}
	
	public boolean fin_de_partie(Jeu jeu) {
		return (jeu.gagner(jeu.getPlateau(), 1) || jeu.gagner(jeu.getPlateau(), 2) || jeu.getPlateau().verifierGrillePleine());
	}
	
	public int min_max(Jeu jeu, int profondeur, int alpha, int beta, boolean maximizingPlayer) {
		int [][] grille = jeu.getPlateau().getGrille();
		List<Integer> colonnes_disponibles = jeu.getColonnesDisponibles(jeu.getPlateau());
		boolean fin_de_partie = fin_de_partie(jeu);
		if (profondeur==0 || fin_de_partie) {
			if (fin_de_partie) {
				if (jeu.gagner(jeu.getPlateau(), 2)) {
					//return ()
				}
				else if (jeu.gagner(jeu.getPlateau(), 1)) {
					//return ()
				}
			}
			else {
				//return 
			}		
		}
		if (maximizingPlayer) {
			double valeur = Double.NEGATIVE_INFINITY;
		}
		return 0;
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
