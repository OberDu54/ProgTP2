package fr.ul.miage.lucas;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Robinet extends Service<Void>{
	
	private double debit;
	
	private Baignoire baignoire;
	
	private boolean running;

	public Robinet(double debit, Baignoire b) {
		super();
		this.debit = debit;
		this.baignoire = b;
		this.running = false;
	}
	
	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}
	
	public void stop() {
		running = false;
	}

	public void run() {
		try {
			while(!baignoire.estPleine()) {
				baignoire.remplir(debit/1000);
				System.out.println("Capacite : " + baignoire.getVolume());
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				running = true;
				try {
					while(!baignoire.estPleine()&&running) {
						baignoire.remplir(debit/1000);
						System.out.println("capacite : " + baignoire.getVolume());
						Thread.sleep(1);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		};
	}
	
}
