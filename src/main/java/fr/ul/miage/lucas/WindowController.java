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

public class WindowController implements Initializable{
	
	private static final Logger LOG = Logger.getLogger(WindowController.class.getName());
	
	@FXML
	private Button button;
	
	@FXML
	private Button stopButton;
	
	@FXML
	private Button pauseButton;
	
	@FXML
	public ProgressBar progressBar;
	
	@FXML
	public Slider capSlider;
	
	@FXML
	public Label capLabel;
	
	@FXML
	public Slider robSlider;
	
	@FXML
	public Label robLabel;
	
	@FXML
	public Slider fuiSlider;
	
	@FXML
	public Label fuiLabel;
	
	@FXML
	public Button majButton;
	
	@FXML
	public Label resultLabel;
	
	@FXML
	public Label errorLabel;
	
	public void coulerEau() {
		resultLabel.setTextFill(Color.GREEN);
		resultLabel.setText("Remplissage en cours...");
		progressBar.setStyle("-fx-accent: blue;");
		progressBar.progressProperty().bind(App.robinet.progressProperty());
		if(!App.robinet.isRunning()&&!App.fuite.isRunning()) {
			App.robinet.start();
			App.fuite.start();
		}else {
			errorLabel.setText("Le service tourne déjà");
		}
	}

	public void stopperEau() {
		resultLabel.setTextFill(Color.RED);
		resultLabel.setText("Processus en pause...");
		progressBar.setStyle("-fx-accent: red;");
		progressBar.progressProperty().unbind();
		App.robinet.cancel();
		App.fuite.cancel();
		App.robinet.reset();
		App.fuite.reset();
	}
	
	public void recommencer() {
		progressBar.progressProperty().unbind();
		stopperEau();
		resultLabel.setText("Processus stoppé !");
		progressBar.setProgress(0);
		App.baignoire.reinitialiser();
	}
	
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
				errorLabel.setText("Valeurs mises à jour");
			}else {
				errorLabel.setTextFill(Color.RED);
				switch(verif) {
				case(1):
					errorLabel.setText("La capacité de la baignoire doit être supérieure aux débits du robinet et de la fuite");
					break;
				case(2):
					errorLabel.setText("Le débit du robinet doit être supérieur à celui de la fuite");
					break;
				case(3):
					errorLabel.setText("Aucune valeur ne doit être nulle");
				}
			}
		}else{
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Impossible de changer les valeurs pendant l'execution");
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		final DecimalFormat f = new DecimalFormat();
		f.setMaximumFractionDigits(2);
		capSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				capLabel.setText("Capacité : " + f.format(newValue));
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
				resultLabel.setText("Terminé !");
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
