import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int choix = -1;
		while (choix<1 || choix>2) {
			System.out.println("Saisissez 1 pour jouer en 1vs1 ou 2 pour jouer en 1vsIA :");
			choix = sc.nextInt();
		}
		if (choix == 1) {
			JoueurContreJoueur JvsJ = new JoueurContreJoueur();
		}
		else if (choix == 2) {
			JoueurContreIA JvsIA = new JoueurContreIA();
		}
		sc.close();
	}       
}