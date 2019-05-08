import java.util.List;

public class Utils {

	public static int compteurInteger(List<Integer> liste, int i) {
		int compteur=0;
		for (int aux : liste) {
			if (aux==i) 
				compteur++;
		}
		return compteur;	
	}
	
	public static void copierGrille(int [][] grille,int [][] copie_grille) {
		if (copie_grille.length==grille.length && copie_grille[0].length==grille[0].length) {
			for (int i=0;i<copie_grille.length;i++) {
				for (int j=0;j<copie_grille[0].length;j++) {
					copie_grille[i][j] = grille[i][j];
				}
			}
		}
		else {
			System.out.println("Erreur");
		}
	}
	
	public static Jeu copierJeu (Jeu jeu) {
		Jeu copie_jeu = new Jeu();
		copierGrille(jeu.getPlateau().getGrille(),copie_jeu.getPlateau().getGrille());
		return copie_jeu;	
	}
	
}
