package fr.ul.miage.lucas;

public class Fuite implements Runnable{
	
	public double debit;
	
	public Baignoire baignoire;

	public Fuite(double debit, Baignoire b) {
		super();
		this.debit = debit;
		this.baignoire = b;
	}
	
	public void fuir() {
		
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
				baignoire.vider(debit/1000);
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
