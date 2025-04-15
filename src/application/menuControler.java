package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;


public class menuControler {
	
	private double size;
	private boolean sombre;
	
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
		        node.setStyle(currentStyle + "-fx-background-color: #" + color + ";");
		    }
		} else if(type == "BODY") {
			for (Node node : root.lookupAll(cssClass)) {
				String currentStyle = node.getStyle();
		        node.setStyle(currentStyle + "-fx-text-fill: #" + color + ";");
		    }
		}
	}
	
	menuControler(){
		this.size = 12;
		this.sombre = false;
	}
	menuControler(double size, boolean sombre){
		this.size = size;
		this.sombre = sombre;
	}
	
	@FXML
	public void initialize() {
		updateStyleClass(root, ".txt", this.size);
		if(this.sombre) {
			root.setStyle("-fx-background-color: #4f2f0d;");
    		updateStyleClass(root, ".but", "4f4f4f", "BACKGROUND");
    		updateStyleClass(root, ".txt", "dfdfdf", "BODY");
		}
	}
	
	@FXML
	private GridPane root;
	@FXML
	private TextField enter_name;
	
	@FXML
	public void play(ActionEvent event) throws IOException { 
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
	
	@FXML
	public void readParam(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("parametre.fxml"));
			ParametreControler param = new ParametreControler();
			loader.setController(param);
			DialogPane dialogPane = loader.load();
			Dialog<Pair<Boolean, Double>> dialog = new Dialog<>();
            dialog.setTitle("Parametre");
            dialog.setDialogPane(dialogPane);
            
            dialog.setResultConverter(button -> {
                if (button == ButtonType.OK) {
                    return new Pair<>(
                        param.isOptionChecked(),
                        param.getSliderValue()
                    );
                }
                return null;
            });
            dialog.showAndWait().ifPresent(result -> {
            	double fontSize = result.getValue();
            	this.size = fontSize;
            	updateStyleClass(root, ".txt", fontSize);
            	if(result.getKey()) {
            		root.setStyle("-fx-background-color: #4f2f0d;");
            		updateStyleClass(root, ".but", "4f4f4f", "BACKGROUND");
            		updateStyleClass(root, ".txt", "dfdfdf", "BODY");
            		this.sombre = true;
            	} else if(this.sombre) {
            		root.setStyle("-fx-background-color: #FFFAED;");
            		updateStyleClass(root, ".but", "ebebeb", "BACKGROUND");
            		updateStyleClass(root, ".txt", "000000", "BODY");
            		this.sombre = false;
            	}
            });
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void aide(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("aide.fxml"));
		ParametreControler param = new ParametreControler();
		loader.setController(param);
		DialogPane dialogPane = loader.load();
		Dialog<Pair<Boolean, Double>> dialog = new Dialog<>();
        dialog.setTitle("Parametre");
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
	}
}
