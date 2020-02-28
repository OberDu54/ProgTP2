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
import javafx.stage.Stage;

public class App extends Application{
	
	public static final Logger LOG = Logger.getLogger(App.class.getName());
	
	public static Baignoire baignoire;
	
	public static Fuite fuite;
	
	public static Robinet robinet;
	
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
				@Override
				public void handle(WorkerStateEvent event) {
					fuite.reset();
				}
			});
			robinet.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

				@Override
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
			}
		} catch (ParseException e) {
			LOG.severe("exeption levee");
			e.printStackTrace();
		}
	}
	
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
		return res;
	}

}
