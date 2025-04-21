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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VictoireController {
	
	@FXML
	private HBox outilsContainer;
	@FXML
	private GridPane root;
	@FXML
	private Button rejouer;
	@FXML
	private HBox center;
	
	private OutilsController outilsController;
	private boolean sombre;
	private double size;
	private String name;
	private String message;
	
	
	public void updateStyleClass(Parent root, String cssClass, double size) {
	    for (Node node : root.lookupAll(cssClass)) {
	    	String currentStyle = node.getStyle();
	        node.setStyle(currentStyle + "-fx-font-size: " + size + "px;");
	    }
	}
	public void updateStyleClass(Parent root, String cssClass, String color, String type) {
		if(type == "BACKGROUND") {
		    for (Node node : root.lookupAll(cssClass)) {
		    	String currentStyle = node.getStyle();
		    	if(cssClass == ".but") {
		    		node.setStyle(currentStyle + "-fx-background-color: #" + color + ";");
		    	} else {
		    		node.setStyle(currentStyle + "-fx-fill: #" + color + ";");
		    	}
		    }
		} else if(type == "BODY") {
			for (Node node : root.lookupAll(cssClass)) {
				String currentStyle = node.getStyle();
		        node.setStyle(currentStyle + "-fx-text-fill: #" + color + ";");
		    }
		}
	}
	
	public VictoireController(boolean sombre, double size, String name, String message){
		this.sombre = sombre;
		this.size = size;
		this.name = name;
		this.message = message;
	}
	
	
	@FXML
	public void initialize() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("outils.fxml"));
        Parent includedRoot = loader.load();
        outilsController = loader.getController();
		outilsController.initStyles(sombre, size, root, name);
        outilsContainer.getChildren().add(includedRoot);
        if(this.name != null) {
        	Text gg = new Text();
        	gg.setText(message);
        	gg.getStyleClass().add("txt");
        	center.getChildren().add(gg);
        }
        
        if(this.sombre) {
	        root.setStyle("-fx-background-color: #4f2f0d;");
			updateStyleClass(root, ".but", "4f4f4f", "BACKGROUND");
			updateStyleClass(root, ".rec", "b5ac92", "BACKGROUND");
			updateStyleClass(root, ".txt", "dfdfdf", "BODY");
        }	
        else {
        	updateStyleClass(root, ".but", "FFF2CC", "BACKGROUND");
    		updateStyleClass(root, ".txt", "000000", "BODY");
        }
        updateStyleClass(this.root, ".txt", this.size);
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
			jeuControler monControleur = new jeuControler(this.size, this.sombre, this.name);
			loader.setController(monControleur);
			Parent root = loader.load();
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
