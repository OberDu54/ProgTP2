package fr.ul.miage.lucas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class WindowController {
	
	@FXML
	private Button button;
	
	@FXML
	private ProgressBar progressBar;
	
	public void coulerEau() {
		App.robinet.run();
		App.fuite.run();
	}
}
