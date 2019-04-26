
public class Disque {

	private int numero_ligne;
	private int numero_colonne;
	
	public Disque(int numero_ligne, int numero_colonne) {
		this.numero_ligne=numero_ligne;
		this.numero_colonne=numero_colonne;
	}

	public int getNumero_ligne() {
		return numero_ligne;
	}

	public void setNumero_ligne(int numero_ligne) {
		this.numero_ligne = numero_ligne;
	}

	public int getNumero_colonne() {
		return numero_colonne;
	}

	public void setNumero_colonne(int numero_colonne) {
		this.numero_colonne = numero_colonne;
	}

	public String toString() {
		return "("+numero_ligne+","+numero_colonne+")";
	}
	
}
