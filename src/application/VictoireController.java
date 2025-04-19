package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VictoireController {
	
	@FXML
	private HBox outilsContainer;
	@FXML
	private GridPane root;
	@FXML
	private Button rejouer;
	
	private OutilsController outilsController;
	private boolean sombre;
	private double size;
	
	
	public VictoireController() {
	}
	
	public VictoireController(boolean sombre, double size){
		this.sombre = sombre;
		this.size = size;
	}
	
	public void initialize() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("outils.fxml"));
        Parent includedRoot = loader.load();
        outilsController = loader.getController();
		outilsController.initStyles(sombre, size, root);
        outilsContainer.getChildren().add(includedRoot);
	}
	
	@FXML
	private void replay(ActionEvent event) {
		try {
			FXMLLoader loader;
			if(this.sombre) {
				loader = new FXMLLoader(getClass().getResource("jeu_sombre.fxml"));
			}
			else {
				loader = new FXMLLoader(getClass().getResource("jeu.fxml"));
			}
			jeuControler monControleur = new jeuControler(this.size, this.sombre);
			loader.setController(monControleur);
			Parent root = loader.load();
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
