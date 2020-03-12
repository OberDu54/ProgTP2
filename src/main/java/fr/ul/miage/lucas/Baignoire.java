package fr.ul.miage.lucas;

import javafx.scene.control.ProgressBar;

/**
 * Classe représentant la baignoire
 * 
 * @author Lucas Oberhausser
 *
 */
public class Baignoire{
	
	/**
	 * Capacité de la baignoire (en litres)
	 */
	private int capacite;
	
	/**
	 * Volume rempli de la baignoire (en litres)
	 */
	private double volume;
	
	/**
	 * Constructeur qui instancie une baignoire vide
	 * @param capacite La capacité de la baignoire
	 */
	public Baignoire(int capacite) {
		super();
		this.capacite = capacite;
		this.volume = 0;
		//progressBar = WindowController.progressBar;
	}
	
	/**
	 * Remplit la baignoire
	 * @param x Volume d'eau à ajouter
	 */
	public void remplir(double x) {
		volume += x;
	}
	
	/**
	 * Vide la baignoire
	 * @param x Volume d'eau à retirer
	 */
	public void vider(double x) {
		volume -= x;
	}

	public int getCapacite() {
		return capacite;
	}

	public double getVolume() {
		return volume;
	}
	
	/**
	 * Methode permettant de savoir si la baignoire est pleine
	 * @return Vrai si la baignoire est pleine
	 */
	public boolean estPleine() {
		if(volume >= capacite) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	/**
	 * Vide totalement la baignoire
	 */
	public void reinitialiser() {
		this.volume = 0;
	}

}
