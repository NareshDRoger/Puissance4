import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Jeu {
	
	Plateau plateau;
	HashMap<Integer, ArrayList<Disque>> disques_joueurs;
	
	public Jeu() {
		this.plateau = new Plateau ();
		plateau.initialiser();
		disques_joueurs = new HashMap<Integer,ArrayList<Disque>>();
		//plateau.afficher();
	}
	
	
	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public HashMap<Integer, ArrayList<Disque>> getDisques_joueurs() {
		return disques_joueurs;
	}

	public void setDisques_joueurs(HashMap<Integer, ArrayList<Disque>> disques_joueurs) {
		this.disques_joueurs = disques_joueurs;
	}

	public int saisirColonne() {
		Scanner sc = new Scanner(System.in);
		int numero_colonne = -1;
		boolean colonne_pleine = true;
		while(numero_colonne<1 || numero_colonne>7 || colonne_pleine==true) {
			System.out.println("Veuillez saisir le numéro de la colonne (de 1 à 7) :");
			numero_colonne = sc.nextInt();
			if (numero_colonne<1 || numero_colonne>7)
				System.out.println("Vous n'avez pas saisi un nombre compris entre 1 et 7!");
			else {
				colonne_pleine = plateau.verifierColonnePleine(numero_colonne-1);
				if (colonne_pleine)
					System.out.println("Colonne pleine!");
			}
		}
		System.out.println();
		//sc.close();
		return numero_colonne-1;
	}
	
	public void jouerUnCoup(int numero_joueur) {
		System.out.println("Au tour du joueur "+numero_joueur);
		int numero_colonne = saisirColonne();
		plateau.modifierGrille(numero_colonne, numero_joueur);
		ajouterDernierDisque(numero_joueur);
		System.out.println("Le joueur "+numero_joueur+" a placé le disque dans la colonne "+(numero_colonne+1));
		plateau.afficher();
	}
	
	public boolean gagner(Plateau plateau, int numero_joueur) {
		int [][] grille = plateau.getGrille();
		//Vérifier sur l'horizontale
		for(int colonne=0;colonne<((grille[0].length)-3);colonne++) {
			for (int ligne = 0;ligne<grille.length;ligne++) {
				if (grille[ligne][colonne] == numero_joueur && grille[ligne][colonne+1] == numero_joueur && grille[ligne][colonne+2] == numero_joueur && grille[ligne][colonne+3] == numero_joueur)
					return true;
			}
		}
		//Vérification sur la verticale
		for(int colonne=0;colonne<grille[0].length;colonne++) {
			for (int ligne = 0;ligne<((grille.length)-3);ligne++) {
				if (grille[ligne][colonne] == numero_joueur && grille[ligne+1][colonne] == numero_joueur && grille[ligne+2][colonne] == numero_joueur && grille[ligne+3][colonne] == numero_joueur)
					return true;
			}
		}
		//Vérificications sur les diagonales
		for(int colonne=0;colonne<((grille[0].length)-3);colonne++) {
			for (int ligne = 0;ligne<((grille.length)-3);ligne++) {
				if (grille[ligne][colonne] == numero_joueur && grille[ligne+1][colonne+1] == numero_joueur && grille[ligne+2][colonne+2] == numero_joueur && grille[ligne+3][colonne+3] == numero_joueur)
					return true;
			}
		}
		for(int colonne=0;colonne<((grille[0].length)-3);colonne++) {
			for (int ligne = 3;ligne<grille.length;ligne++) {
				if (grille[ligne][colonne] == numero_joueur && grille[ligne-1][colonne+1] == numero_joueur && grille[ligne-2][colonne+2] == numero_joueur && grille[ligne-3][colonne+3] == numero_joueur)
					return true;
			}
		}
		return false;
		
	}
	
	public void afficherDisquesDuJoueur(int numero_joueur) {
		System.out.println(disques_joueurs.get(numero_joueur).size()+" disque(s)");
		ArrayList<Disque> disques_joueur = new ArrayList<Disque>();
		disques_joueurs.forEach((k, v) -> {
			if (k == numero_joueur)
				disques_joueur.addAll(v);
        });
		for (Disque disque : disques_joueur) {
			System.out.println(disque);
		}
	}
	
	public void ajouterDernierDisque (int numero_joueur) {
		if (disques_joueurs.containsKey(numero_joueur)) {
			disques_joueurs.get(numero_joueur).add(plateau.getDernier_disque());
		}
		else {
			ArrayList<Disque> disques_list = new ArrayList<Disque>();
			disques_list.add(plateau.getDernier_disque());
			disques_joueurs.put(numero_joueur, disques_list);
		}
	}
	
	public List<Integer> getColonnesDisponibles() {
		int [][] grille = plateau.getGrille();
		List<Integer> colonnes_disponibles = new ArrayList<Integer>();
		for (int numero_colonne=0; numero_colonne<grille[0].length; numero_colonne++) {
			if (plateau.determinerLigne(numero_colonne)!=-1)
				colonnes_disponibles.add(numero_colonne);
		}
		return colonnes_disponibles;
	}
}
