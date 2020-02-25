package fr.ul.miage.lucas;

public class Baignoire{
	
	private int capacite;
	
	private double volume;

	public Baignoire(int capacite) {
		super();
		this.capacite = capacite;
		this.volume = 0;
	}
	
	public void remplir(double x) {
		volume += x;
	}
	
	public void vider(double x) {
		volume -= x;
	}

	public int getCapacite() {
		return capacite;
	}

	public double getVolume() {
		return volume;
	}
	
	public boolean estPleine() {
		if(volume >= capacite) {
			return true;
		}else {
			return false;
		}
	}
}
