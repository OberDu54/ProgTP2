package fr.ul.miage.lucas;

public class Baignoire {
	
	private int capacite;
	
	private double volume;
	
	//private Robinet robinet;
	
	//private Fuite fuite;

	public Baignoire(int capacite, Robinet robinet, Fuite fuite) {
		super();
		this.capacite = capacite;
		this.volume = 0;
		/*
		this.robinet = robinet;
		this.fuite = fuite;
		*/
	}
	
	public void remplir(double x) {
		volume += x;
	}
	
	public void vider(double x) {
		volume -= x;
	}
	
}
