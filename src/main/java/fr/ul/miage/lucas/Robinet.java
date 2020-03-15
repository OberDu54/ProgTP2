package fr.ul.miage.lucas;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Classe représentant un robinet
 * 
 * @author Lucas Oberhausser
 *
 */
public class Robinet extends Service<Void>{
	
	/**
	 * Débit du robinet en litre/seconde
	 */
	private double debit;
	
	/**
	 * Baignoire associée à ce robinet 
	 */
	private Baignoire baignoire;
	
	/**
	 * Constructeur
	 * 
	 * @param debit Debit du robinet
	 * @param b Baingoire associée
	 */
	public Robinet(double debit, Baignoire b) {
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
	 * Methode principale appellée par start()
	 */
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>(){
			
			@Override
			protected Void call() throws Exception {
				while(!baignoire.estPleine()&&!isCancelled()) {
					baignoire.remplir(debit/1000);
					updateProgress(baignoire.getVolume(), baignoire.getCapacite());
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
