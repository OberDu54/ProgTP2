package fr.ul.miage.lucas;

public class Baignoire {
	
	private int capacite;
	
	private Robinet robinet;
	
	private Fuite fuite;

	public Baignoire(int capacite, Robinet robinet, Fuite fuite) {
		super();
		this.capacite = capacite;
		this.robinet = robinet;
		this.fuite = fuite;
	}
	
}
