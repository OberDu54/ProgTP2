package fr.ul.miage.lucas;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class WindowController {
	
	private static final Logger LOG = Logger.getLogger(WindowController.class.getName());
	
	@FXML
	private Button button;
	
	@FXML
	private Button stopButton;
	
	@FXML
	private Button pauseButton;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Label label;
	
	public void coulerEau() {
		if(!App.robinet.isRunning()&&!App.fuite.isRunning()) {
			if(App.baignoire.getVolume()!=0) {
				App.baignoire.reinitialiser();
			}
			App.robinet.start();
			App.fuite.start();
		}else {
			LOG.severe("Le service tourne déjà");
		}
	}
	
	public void stopperEau() {
		App.robinet.cancel();
		App.fuite.cancel();
		App.robinet.reset();
		App.fuite.reset();
	}
	
	public void recommencer() {
		stopperEau();
		App.baignoire.reinitialiser();
	}
}
