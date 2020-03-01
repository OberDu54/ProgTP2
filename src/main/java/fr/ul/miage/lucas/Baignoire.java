package fr.ul.miage.lucas;

import javafx.scene.control.ProgressBar;

public class Baignoire{
	
	private int capacite;
	
	private double volume;
	
	private ProgressBar progressBar;
	
	public Baignoire(int capacite) {
		super();
		this.capacite = capacite;
		this.volume = 0;
		//progressBar = WindowController.progressBar;
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
	
	public void reinitialiser() {
		this.volume = 0;
	}
	
	public void updateProgressBar(double x) {
		System.out.println("=========MAJ DE LA BARRE==========");
		progressBar.setProgress(x);
	}
}
