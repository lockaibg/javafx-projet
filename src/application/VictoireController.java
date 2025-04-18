package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
	private void replay() throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("jeu.fxml"));
	    jeuControler jeuCtrl = new jeuControler(size, sombre);
	    loader.setController(jeuCtrl);
	    Parent root = loader.load();
	    outilsContainer.getScene().setRoot(root);
	}
}
