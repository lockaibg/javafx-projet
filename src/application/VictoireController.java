package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class VictoireController {
	
	@FXML
	private HBox outilsContainer;
	@FXML
	private GridPane root;
	
	
	private OutilsController outilsController;
	private boolean sombre;
	private int size;
	
	
	
	VictoireController(boolean sombre, int size){
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
}
