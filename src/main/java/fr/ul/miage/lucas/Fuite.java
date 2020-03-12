package fr.ul.miage.lucas;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Classe represantant une fuite 
 * 
 * @author Lucas Oberhausser
 *
 */
public class Fuite extends Service<Void>{
	
	/**
	 * Débit de la fuite en litre/seconde
	 */
	private double debit;
	
	/**
	 * Baignoire associée à la fuite
	 */
	public Baignoire baignoire;
	
	/**
	 * Constructeur
	 * @param debit Débit de la fuite
	 * @param b Baignoire associée
	 */
	public Fuite(double debit, Baignoire b) {
		super();
		this.debit = debit;
		this.baignoire = b;
	}
	
	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}
	
	/**
	 * Méthode principale appellée par start()
	 */
	@Override
	protected Task createTask() {
		return new Task<Void>(){
			
			/**
			 * Appel à la méthode qui fait fuir l'eau et vide la baignoire
			 */
			@Override
			protected Void call() throws Exception {
				while(!baignoire.estPleine()) {
					if(baignoire.getVolume()!=0) {baignoire.vider(debit/1000);}
					System.out.println("capacite -- : " + baignoire.getVolume());
					Thread.sleep(1);
					if(isCancelled()) {
						break;
					}
				}
				return null;
			}
		};
	}
	
	
	
	
}
