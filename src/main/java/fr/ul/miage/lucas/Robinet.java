package fr.ul.miage.lucas;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Robinet extends Service<Void>{
	
	private double debit;
	
	private Baignoire baignoire;
	
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
	
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>(){
			
			@Override
			protected Void call() throws Exception {
				while(!baignoire.estPleine()&&!isCancelled()) {
					baignoire.remplir(debit/1000);
					System.out.println("capacite ++ : " + baignoire.getVolume());
					//baignoire.updateProgressBar(baignoire.getCapacite()/baignoire.getVolume());
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
