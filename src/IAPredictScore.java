import java.math.BigInteger;

public class IAPredictScore {

	private int numero_colonne;
	private double score;
	
	public IAPredictScore (int numero_colonne, double score) {
		this.numero_colonne=numero_colonne;
		this.score=score;
	}

	public int getNumero_colonne() {
		return numero_colonne;
	}

	public void setNumero_colonne(int numero_colonne) {
		this.numero_colonne = numero_colonne;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public String toString () {
		return "("+numero_colonne+","+score+")";
	}

}


