package fr.ul.miage.lucas;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Fuite extends Service<Void>{
	
	private double debit;
	
	public Baignoire baignoire;
	
	private boolean running;

	public Fuite(double debit, Baignoire b) {
		super();
		this.debit = debit;
		this.baignoire = b;
		this.running = false;
	}
	
	public void stop() {
		running = false;
	}
	
	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}
	
	/*
	public void run() {
		running = true;
		try {
			while(!baignoire.estPleine()&&running) {
				baignoire.vider(debit/1000);
				System.out.println("capacite : " + baignoire.getVolume());
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	@Override
	protected Task createTask() {
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				running = true;
				try {
					while(!baignoire.estPleine()&&running) {
						baignoire.vider(debit/1000);
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
