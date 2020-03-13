package fr.ul.miage.lucas;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * Classe principale
 * 
 * @author Lucas Oberhausser
 *
 */
public class App extends Application{
	
	/**
	 * Logger 
	 */
	public static final Logger LOG = Logger.getLogger(App.class.getName());
	
	/**
	 * Baignoire statique pour être visible par toutes les autres classes 
	 */
	public static Baignoire baignoire;
	
	/**
	 * Robinet statique
	 */
	public static Fuite fuite;
	
	/**
	 * Fuite statique
	 */
	public static Robinet robinet;
	
	/**
	 * Méthode qui initialise l'interface
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("TP2_Oberhausser");
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Window.fxml"));
		}catch(IOException e) {
			System.out.println("Erreur de chargement de l'interface");
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Méthode principale
	 * @param args
	 */
	public static void main(String[] args) {
		Options options = new Options();
		Option cap = new Option("c", "capacity", true, "Capacite de la baignoire");
		Option remplissage = new Option("r", "remp", true, "Debit de remplissage de la baignoire");
		Option debitFuite = new Option("f", "fuite", true, "Debit de fuite");
		cap.setRequired(false);
		remplissage.setRequired(false);
		debitFuite.setRequired(false);
		options.addOption(cap);
		options.addOption(remplissage);
		options.addOption(debitFuite);
		CommandLineParser parser = new DefaultParser();
		final ProgressBar progressBar = new ProgressBar();
		int c = 100;	//capacite par defaut
		double r = 2;	//remplissage par defaut
		double f = 0.5;	//fuite par defaut
		try {
			CommandLine line = parser.parse(options, args);
			if(line.hasOption('c')) {
				c = Integer.parseInt(line.getOptionValue('c'));
			}
			if(line.hasOption('r')) {
				r = Double.parseDouble(line.getOptionValue('r'));
			}
			if(line.hasOption('f')) {
				f = Double.parseDouble(line.getOptionValue('f'));
			}
			baignoire = new Baignoire(c);
			fuite = new Fuite(f,baignoire);
			robinet = new Robinet(r,baignoire);
			fuite.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				
				public void handle(WorkerStateEvent event) {
					
					fuite.reset();
				}
			});
			robinet.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

				public void handle(WorkerStateEvent event) {

					robinet.reset();
				}
			});

			int verif = verification(c, r, f);
			switch(verif) {
			case(0):
				LOG.info("Donn�es valid�es");
				LOG.info("Lancement...");
				launch(args);
				break;
			case(1):
				LOG.severe("La capacite de la baignoire doit �tre sup�rieure au debit du robinet et de la fuite");
				break;
			case(2):
				LOG.severe("Le d�bit du robinet ne doit pas �tre inf�rieur � celui de la fuite");
				break;
			case(3):
				LOG.severe("Aucun valeur ne doit �tre nulle");
				break;
			case(4):
				LOG.severe("Le debit du robinet doit �tre inf�rieur � 500");
				break;
			case(5):
				LOG.severe("Le debit de la fuite doit �tre inf�rieur � 500");
				break;
			case(6):
				LOG.severe("La capacit� de la baignoire doit �tre inf�rieure � 5000");
				break;
			}

		} catch (ParseException e) {
			LOG.severe("Exeption levee");
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode utilisée pour verifier que les règles des variables sont respectées
	 * @param cap Capacité de la baignoire
	 * @param rob Débit du robinet 
	 * @param fuite Débit de la fuite 
	 * @return Un code d'erreur, 0 si les conditions sont bonnes
	 */
	public static int verification(int cap, double rob, double fuite) {
		int res = 0;
		if(cap<=rob || cap<=fuite) {
			res = 1;
		}
		if(rob<=fuite) {
			res = 2;
		}
		if(rob==0||fuite==0||cap==0) {
			res = 3;
		}
		if(rob>500) {
			res = 4;
		}
		if(fuite>500) {
			res = 5;
		}
		if(cap<50 || cap>5000) {
			res = 6;
		}
		return res;
	}

}
