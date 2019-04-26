public class Plateau {
	
	private int[][] grille = new int[6][7];
	private Disque dernier_disque;
	
	public Plateau () {
		initialiser();
		dernier_disque = new Disque(-1,-1);
	}
	
	public void initialiser () {
		for (int i=0;i<grille.length;i++) {
			for (int j=0;j<grille[i].length;j++) {
				grille[i][j]=0;
			}
		}
	}
	
	public void afficher () {
		System.out.println("Affichage du plateau");
		for (int i=0;i<grille.length;i++) {
			for (int j=0;j<grille[i].length;j++) {
				if (grille[i][j]==0) System.out.print("-\t");
				else System.out.print(grille[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public boolean validerColonne(int numero_colonne) {
		for (int i=0;i<6;i++) {
			if ((grille[6-i-1][numero_colonne]==0))
				return true;
		}
		return false;
	}
	
	public void modifierGrille (int numero_colonne, int valeur) {
		if (validerColonne(numero_colonne)) {
			int numero_ligne = determinerLigne(numero_colonne);
			creerDisque(numero_ligne, numero_colonne);
			grille[numero_ligne][numero_colonne]=valeur;
		}
	}
	
	public int determinerLigne (int numero_colonne) {
		for (int i=0;i<6;i++) {
			if ((grille[6-i-1][numero_colonne]==0))
				return (6-i-1);
		}
		return -1;
	}
	
	public void creerDisque (int numero_ligne, int numero_colonne) {
		Disque disque = new Disque(numero_ligne, numero_colonne);
		dernier_disque=disque;
	}
	 
	
	public Disque getDernier_disque() {
		return dernier_disque;
	}
	
	public int[][] getGrille() {
		return grille;
	}
	
	public boolean verifierColonnePleine (int numero_colonne) {
		if (determinerLigne(numero_colonne)==-1) return true;
		else return false;
	}
	
	public boolean verifierGrillePleine () {
		for (int ligne=0;ligne<grille.length;ligne++) {
			for (int colonne=0;colonne<grille[ligne].length;colonne++) {
				if (grille[ligne][colonne]==0) return false;
			}
		}
		return true;
	}
	
}