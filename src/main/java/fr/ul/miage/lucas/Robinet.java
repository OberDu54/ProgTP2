package fr.ul.miage.lucas;

public class Robinet implements Runnable{
	
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

	public void run() {
		try {
			while(!baignoire.estPleine()) {
				baignoire.remplir(debit/1000);
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
