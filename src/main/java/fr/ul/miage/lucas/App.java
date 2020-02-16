package fr.ul.miage.lucas;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
	
	public static final Logger LOG = Logger.getLogger(App.class.getName());
	
	public static void main(String[] args) {
		launch(args);
	}

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

}
