package fr.ul.miage.lucas;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Fuite extends Service<Void>{
	
	private double debit;
	
	public Baignoire baignoire;

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
	
	
	@Override
	protected Task createTask() {
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				while(!baignoire.estPleine()) {
					baignoire.vider(debit/1000);
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
