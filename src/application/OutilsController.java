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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class OutilsController {
	
	private boolean sombre;
	private double size;
	private GridPane root;
	
	public void initStyles(boolean sombre, double size, GridPane root) {
		this.sombre = sombre;
		this.size = size;
		this.root = root;
	}
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
	
	
	@FXML
	public void quitter(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
			menuControler monControleur = new menuControler(this.size, this.sombre);
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
	public void param(ActionEvent event) {
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
            	try {
        			FXMLLoader loader2;
        			if(result.getKey()) {
        				loader2 = new FXMLLoader(getClass().getResource("jeu_sombre.fxml"));
        			}
        			else {
        				loader2 = new FXMLLoader(getClass().getResource("jeu.fxml"));
        			}
        			jeuControler monControleur = new jeuControler(this.size, this.sombre);
        			loader2.setController(monControleur);
        			Parent root = loader2.load();
        			Scene scene = new Scene(root,600,400);
        			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        			currentStage.setScene(scene);
        		} catch(Exception e) {
        			e.printStackTrace();
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
	
	@FXML 
	public void affiche(MouseEvent event) {
		Node source = (Node) event.getSource();
		VBox pane = (VBox) source.getParent();
		Text text = new Text();
		text.setId("textEffacer");
		String id = source.getId();
		switch (id) {
			case "aideBut":
				text.setText("aides");
				break;
			case "paramBut":
				text.setText("paramêtres");
				break;
			case "leaveBut":
				text.setText("retourner au menu principal");
				break;
		}
		pane.getChildren().add(text);
	}
	@FXML
	public void desaffiche(MouseEvent event) {
		Node source = (Node) event.getSource();
		VBox pane = (VBox) source.getParent();
		pane.getChildren().removeIf(node -> node instanceof Text);
	}
	
}
