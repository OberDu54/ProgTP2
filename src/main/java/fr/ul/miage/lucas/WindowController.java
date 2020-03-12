package fr.ul.miage.lucas;

import java.awt.TextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

/**
 * Controleur de l'interface
 * 
 * @author Lucas Oberhausser
 *
 */
public class WindowController implements Initializable{
	
	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(WindowController.class.getName());
	
	/**
	 * Bouton pour lancer le processus 
	 */
	@FXML
	private Button button;
	
	/**
	 * Bouton stop 
	 */
	@FXML
	private Button stopButton;
	
	/**
	 * Bouton pause
	 */
	@FXML
	private Button pauseButton;
	
	/**
	 * Barre de progression
	 */
	@FXML
	public ProgressBar progressBar;
	
	/**
	 * Slider reglant la capacité de la baignoire
	 */
	@FXML
	public Slider capSlider;
	
	/**
	 * Label associé au slider de capacité 
	 */
	@FXML
	public Label capLabel;
	
	/**
	 * Slider reglant le debit du robinet
	 */
	@FXML
	public Slider robSlider;
	
	/**
	 * Label associé au slider du robinet 
	 */
	@FXML
	public Label robLabel;
	
	/**
	 * Slider permettant de regler le débit de la fuite 
	 */
	@FXML
	public Slider fuiSlider;
	
	/**
	 * Label associé au slider de la fuite 
	 */
	@FXML
	public Label fuiLabel;
	
	/**
	 * Bouton pour mettre a jour les valeurs 
	 */
	@FXML
	public Button majButton;
	
	/**
	 * Label indiquant l'état du processus
	 */
	@FXML
	public Label resultLabel;
	
	/**
	 * Label indiquant si les mises à jour ont été effectives
	 */
	@FXML
	public Label errorLabel;
	
	/**
	 * Méthode faisant couler l'eau, déclenchée par le bouton lancement
	 */
	public void coulerEau() {
		resultLabel.setTextFill(Color.GREEN);
		resultLabel.setText("Remplissage en cours...");
		progressBar.setStyle("-fx-accent: blue;");
		progressBar.progressProperty().bind(App.robinet.progressProperty());
		if(!App.robinet.isRunning()&&!App.fuite.isRunning()) {
			App.robinet.start();
			App.fuite.start();
		}else {
			errorLabel.setText("Le service tourne d�j�");
		}
	}
	
	/**
	 * Stoppe l'eau, déclenchée par le bouton pause
	 */
	public void stopperEau() {
		if(App.robinet.isRunning()||App.fuite.isRunning()) {
			resultLabel.setTextFill(Color.RED);
			resultLabel.setText("Processus en pause...");
			progressBar.setStyle("-fx-accent: red;");
			progressBar.progressProperty().unbind();
			App.robinet.cancel();
			App.fuite.cancel();
			App.robinet.reset();
			App.fuite.reset();
		}
	}
	
	/**
	 * Stoppe l'eau et vide entièrement la baignoire
	 */
	public void recommencer() {
		if(App.robinet.isRunning()||App.fuite.isRunning()) {
			progressBar.progressProperty().unbind();
			stopperEau();
			resultLabel.setText("Processus stopp� !");
			progressBar.setProgress(0);
			App.baignoire.reinitialiser();
		}
	}
	
	/**
	 * Modifie les variables selon les valeurs des sliders  
	 */
	public void miseAJour() {
		if(!App.robinet.isRunning()&&!App.fuite.isRunning()) {
			int cap = (int) capSlider.getValue();
			double rob = robSlider.getValue();
			double fui = fuiSlider.getValue();
			int verif = App.verification(cap, rob, fui);
			if(verif==0) {
				App.baignoire.setCapacite(cap);
				App.robinet.setDebit(rob);
				App.fuite.setDebit(fui);
				errorLabel.setTextFill(Color.BLUE);
				errorLabel.setText("Valeurs mises � jour");
			}else {
				errorLabel.setTextFill(Color.RED);
				switch(verif) {
				case(1):
					errorLabel.setText("La capacit� de la baignoire doit �tre sup�rieure aux d�bits du robinet et de la fuite");
					break;
				case(2):
					errorLabel.setText("Le d�bit du robinet doit �tre sup�rieur � celui de la fuite");
					break;
				case(3):
					errorLabel.setText("Aucune valeur ne doit �tre nulle");
					
				}
			}
		}else{
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Impossible de changer les valeurs pendant l'execution");
		}
	}
	
	/**
	 * Méthode appellée à l'instanciation de l'interface, initialise certains éléments 
	 */
	public void initialize(URL location, ResourceBundle resources) {
		final DecimalFormat f = new DecimalFormat();
		f.setMaximumFractionDigits(2);
		capSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				capLabel.setText("Capacit� : " + f.format(newValue));
			}
		});
		robSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				robLabel.setText("Robinet : " + f.format(newValue));
			}			
		});
		fuiSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				fuiLabel.setText("Fuite : " + f.format(newValue));				
			}	
		});
		App.robinet.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			public void handle(WorkerStateEvent event) {
				resultLabel.setTextFill(Color.GREEN);
				resultLabel.setText("Termin� !");
				progressBar.setStyle("-fx-accent: green;");
				App.robinet.reset();
				progressBar.progressProperty().unbind();
				App.baignoire.reinitialiser();
			}
			
		});
		App.fuite.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			public void handle(WorkerStateEvent event) {
				App.fuite.reset();
			}
			
		});
		capSlider.adjustValue(App.baignoire.getCapacite());
		robSlider.adjustValue(App.robinet.getDebit());
		fuiSlider.adjustValue(App.fuite.getDebit());
	}
}
