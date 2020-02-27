package fr.ul.miage.lucas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class WindowController {
	
	@FXML
	private Button button;
	
	@FXML
	private Button stopButton;
	
	@FXML
	private ProgressBar progressBar;
	
	public void coulerEau() {
		App.robinet.start();
		App.fuite.start();
	}
	
	public void stopperEau() {
		App.robinet.cancel();
		App.fuite.cancel();
	}
}
