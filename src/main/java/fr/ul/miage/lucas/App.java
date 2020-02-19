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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
	
	public static final Logger LOG = Logger.getLogger(App.class.getName());
	
	public static Baignoire baignoire;
	
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
		Option fuite = new Option("f", "fuite", true, "Debit de fuite");
		cap.setRequired(false);
		remplissage.setRequired(false);
		fuite.setRequired(false);
		options.addOption(cap);
		options.addOption(remplissage);
		options.addOption(fuite);
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
		baignoire = new Baignoire(c, new Robinet(r), new Fuite(f));	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("Lancement...");
		launch(args);
	}

}
