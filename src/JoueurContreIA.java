import java.math.BigInteger;
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
		jeu.getPlateau().afficher();
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
				jouerContreIANiveauMoyen(numero_joueur_IA);
			}
			else {
				jouerContreIANiveauDifficile(numero_joueur_IA);
			}
			victoire_joueur_2=jeu.gagner(jeu.getPlateau(),numero_joueur_IA);
			if (victoire_joueur_2) {
				System.out.println("Victoire de l'IA!");
				break;
			}
		}
	}
	
	public double evaluer_alignement(List<Integer> alignement,int numero_joueur) {
		double score=0;
		int numero_joueur_adverse = -1;
		if (numero_joueur==1)
			numero_joueur_adverse=2;
		else 
			numero_joueur_adverse=1;
		int compteur_cases_joueur=Utils.compteurInteger(alignement, numero_joueur);
		int compteur_cases_joueur_adverse=Utils.compteurInteger(alignement, numero_joueur_adverse);;
		int compteur_cases_vides=Utils.compteurInteger(alignement, 0);
		if (compteur_cases_joueur == 4)
			score+=100;
		else if (compteur_cases_joueur==3 && compteur_cases_vides==1)
			score +=5;
		else if (compteur_cases_joueur==2 && compteur_cases_vides==2)
			score +=2;
		if (compteur_cases_joueur_adverse==3 && compteur_cases_vides==1)
			score-=4;
		//System.out.println(score);
		return score;	
	}
	
	public double score_position (int[][] grille, int numero_joueur) {
		double score = 0;
		
		//Score sur la colonne centrale
		int compteur_disques_colonne_centrale = 0;
		List<Integer> alignement_colonne_centrale = new ArrayList<Integer>();
		int taille_colonne_centrale = ((grille[0].length)/2);
		for (int i=0; i<grille.length;i++) {
			alignement_colonne_centrale.add(grille[i][taille_colonne_centrale]);
		}
		compteur_disques_colonne_centrale=Utils.compteurInteger(alignement_colonne_centrale, numero_joueur);
		score += compteur_disques_colonne_centrale*3;
		
		//Score à l'horizontale
		List<Integer> rowArray = new ArrayList<Integer>();
		List<Integer> alignement = new ArrayList<Integer>();
		for (int numero_ligne=0;numero_ligne<grille.length;numero_ligne++,rowArray.clear()) {
			for (int i=0;i<grille[0].length;i++) {
				rowArray.add(grille[numero_ligne][i]);
			}
			for (int numero_colonne=0;numero_colonne<((grille[0].length)-3);numero_colonne++,alignement.clear()) {
				for (int i=numero_colonne;i<(numero_colonne+longueur_alignement);i++) {
					alignement.add(rowArray.get(i));
				}
				score += evaluer_alignement(alignement,numero_joueur);
				
			}
		}
		
		
		//Score à la verticale
		List<Integer> colArray = new ArrayList<Integer>();
		for (int numero_colonne=0;numero_colonne<grille[0].length;numero_colonne++,colArray.clear()) {
			for (int i=0;i<grille.length;i++) {
				colArray.add(grille[i][numero_colonne]);
			}
			for (int numero_ligne=0;numero_ligne<((grille.length)-3);numero_ligne++,alignement.clear()) {
				for (int i=numero_ligne;i<(numero_ligne+longueur_alignement);i++) {
					alignement.add(colArray.get(i));
				}
				score += evaluer_alignement(alignement,numero_joueur);	
			}
		}
		
		//Score sur les diagonales
		for (int numero_ligne=0; numero_ligne<((grille.length)-3);numero_ligne++) {
			for (int numero_colonne=0; numero_colonne<((grille[0].length)-3); numero_colonne++,alignement.clear()) {
				for (int i=0;i<longueur_alignement;i++) {
					alignement.add(grille[numero_ligne+i][numero_colonne+i]);
					score += evaluer_alignement(alignement,numero_joueur);
				}
			}
		}
		
		
		for (int numero_ligne=0; numero_ligne<((grille.length)-3);numero_ligne++) {
			for (int numero_colonne=0; numero_colonne<((grille[0].length)-3); numero_colonne++,alignement.clear()) {
				for (int i=0;i<longueur_alignement;i++) {
					alignement.add(grille[numero_ligne+3-i][numero_colonne+i]);
					score += evaluer_alignement(alignement,numero_joueur);
				}
			}
		}
		
		return score;
	}
	
	public boolean fin_de_partie(Jeu jeu) {
		return (jeu.gagner(jeu.getPlateau(), 1) || jeu.gagner(jeu.getPlateau(), 2) || jeu.getPlateau().verifierGrillePleine());
	}
	
	public IAPredictScore min_max(Jeu jeu, int profondeur, double alpha, double beta, boolean maximizingPlayer) {
		int [][] grille = jeu.getPlateau().getGrille();
		List<Integer> colonnes_disponibles = jeu.getColonnesDisponibles();
		boolean fin_de_partie = fin_de_partie(jeu);
		if (profondeur==0 || fin_de_partie) {
			if (fin_de_partie) {
				if (jeu.gagner(jeu.getPlateau(), 2)) {
					return new IAPredictScore(-1,1000000000);
				}
				else if (jeu.gagner(jeu.getPlateau(), 1)) {
					return new IAPredictScore(-1,-1000000000);
				}
				//Grille pleine
				else {
					return new IAPredictScore(-1,0);
				}
			}
			//Profondeur == 0
			else {
				//System.out.println(score_position(grille,2));
				return new IAPredictScore(-1,score_position(grille, 2));
			}
		}
		if (maximizingPlayer) {
			double valeur = Double.NEGATIVE_INFINITY;
			Random rand = new Random();
			int index_numero_colonne_choisie = rand.nextInt(colonnes_disponibles.size());
			int numero_colonne_choisie = colonnes_disponibles.get(index_numero_colonne_choisie);
			int numero_colonne=-1;
			for (int x=0;x<colonnes_disponibles.size();x++) {
				numero_colonne = colonnes_disponibles.get(x);
				int numero_ligne = jeu.plateau.determinerLigne(numero_colonne);
				Jeu copie_jeu = Utils.copierJeu(jeu);
				copie_jeu.getPlateau().modifierGrille(numero_colonne, 2);
				double nouveau_score = min_max(copie_jeu, profondeur-1, alpha, beta, false).getScore();
				if (nouveau_score>valeur) {
					valeur = nouveau_score;
					numero_colonne_choisie = numero_colonne;
				}
				alpha = Math.max(alpha, valeur);
				if (alpha>=beta) {
					break;
				}
			}
			//System.out.println(new IAPredictScore (numero_colonne_choisie,valeur));
			return new IAPredictScore (numero_colonne_choisie,valeur);		
		}
		//Minimizing player
		else {
			double valeur = Double.POSITIVE_INFINITY;
			Random rand = new Random();
			int numero_colonne_choisie = rand.nextInt(colonnes_disponibles.size());
			int numero_colonne=-1;
			for (int x=0;x<colonnes_disponibles.size();x++) {
				numero_colonne = colonnes_disponibles.get(x);
				int numero_ligne = jeu.plateau.determinerLigne(numero_colonne);
				Jeu copie_jeu = Utils.copierJeu(jeu);
				copie_jeu.getPlateau().modifierGrille(numero_colonne, 1);
				double nouveau_score = min_max(copie_jeu, profondeur-1, alpha, beta, true).getScore();
				if (nouveau_score<valeur) {
					valeur = nouveau_score;
					numero_colonne_choisie = numero_colonne;
				}
				beta = Math.min(beta, valeur);
				if (alpha>=beta) {
					break;
				}
			}
			//System.out.println(new IAPredictScore (numero_colonne_choisie,valeur));
			return new IAPredictScore (numero_colonne_choisie,valeur);		
		}
	}
	
	//Inutile dans min_max
	public int choisir_meilleure_colonne (Jeu jeu, int numero_joueur) {
		List<Integer> colonnes_disponibles = jeu.getColonnesDisponibles();
		double meilleur_score = -10000;
		int meilleure_colonne = new Random().nextInt(colonnes_disponibles.size());
		for (int numero_colonne=0;numero_colonne<jeu.getPlateau().getGrille()[0].length;numero_colonne++) {
			int numero_ligne = jeu.getPlateau().determinerLigne(numero_colonne);
			Jeu copie_jeu = new Jeu();
			for (int i=0;i<jeu.getPlateau().getGrille().length;i++) {
				for (int j=0;j<jeu.getPlateau().getGrille()[0].length;j++) {
					copie_jeu.getPlateau().getGrille()[i][j]=jeu.getPlateau().getGrille()[i][j];
				}
			}
			copie_jeu.getPlateau().modifierGrille(numero_colonne, numero_joueur);
			double score = score_position(copie_jeu.getPlateau().getGrille(),numero_joueur);
			if (score>meilleur_score) {
				meilleur_score = score;
				meilleure_colonne = numero_colonne;
			}
		}
		return meilleure_colonne;
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
	
	public void jouerContreIANiveauMoyen(int numero_joueur) {
		IAPredictScore ia_predict_score = min_max(jeu, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
		int numero_colonne = ia_predict_score.getNumero_colonne();
		double min_max_score = ia_predict_score.getScore();
		if (jeu.getPlateau().determinerLigne(numero_colonne)!=-1) {
			//int numero_ligne = jeu.getPlateau().determinerLigne(numero_colonne);
			jeu.getPlateau().modifierGrille(numero_colonne, numero_joueur);
			jeu.ajouterDernierDisque(numero_joueur);
			System.out.println("L'IA a placé le disque dans la colonne "+(numero_colonne+1));
			jeu.getPlateau().afficher();
		}
	}
	
	public void jouerContreIANiveauDifficile(int numero_joueur) {
		IAPredictScore ia_predict_score = min_max(jeu, 5, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
		int numero_colonne = ia_predict_score.getNumero_colonne();
		double min_max_score = ia_predict_score.getScore();
		if (jeu.getPlateau().determinerLigne(numero_colonne)!=-1) {
			//int numero_ligne = jeu.getPlateau().determinerLigne(numero_colonne);
			jeu.getPlateau().modifierGrille(numero_colonne, numero_joueur);
			jeu.ajouterDernierDisque(numero_joueur);
			System.out.println("L'IA a placé le disque dans la colonne "+(numero_colonne+1));
			jeu.getPlateau().afficher();
		}
	}
	
}
