package fr.ul.miage.lucas;

import java.util.logging.Logger;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.text.TextFlow;

public class WindowController {
	
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
	public Slider robSlider;
	
	@FXML
	public Slider fuiSlider;
	
	@FXML
	public Button majButton;
	
	@FXML
	public TextFlow text;
	
	public void coulerEau() {
		progressBar.setStyle("-fx-accent: blue;");
		progressBar.progressProperty().bind(App.robinet.createTask().progressProperty());
		if(!App.robinet.isRunning()&&!App.fuite.isRunning()) {
			App.robinet.start();
			App.fuite.start();
		}else {
			LOG.severe("Le service tourne déjà");
		}
	}

	public void stopperEau() {
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
		progressBar.setProgress(0);
		App.baignoire.reinitialiser();
	}
}
