package application;

import java.io.IOException;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;



public class jeuControler {
	
	private double size;
	private boolean sombre;
	private OutilsController outilsController;
	private GestionJeu jeu;
	
	@FXML
	private GridPane root;
	@FXML
	private HBox outilsContainer;
	
	
	jeuControler(double size, boolean sombre) throws IOException{
		this.size = size;
		this.sombre = sombre;
		jeu = new GestionJeu("Dico.txt");
		jeu.InitialiserPartie();
	}
	
	public void updateStyleClass(Parent root, String cssClass, double size) {
	    for (Node node : root.lookupAll(cssClass)) {
	    	String currentStyle = node.getStyle();
	        node.setStyle(currentStyle + "-fx-font-size: " + size + "px;");
	    }
	}
	
	@FXML
	public void initialize() throws IOException {
		updateStyleClass(root, ".txt", size);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("outils.fxml"));
        Parent includedRoot = loader.load();
        outilsController = loader.getController();
		outilsController.initStyles(sombre, size, root);
        outilsContainer.getChildren().add(includedRoot);
	}
	
	@FXML
	public void lettre(ActionEvent event) throws IOException {
		
		String a = jeu.getLettresDejaDonnees();
		Object source = event.getSource();
		char letter = ((Button) source).getText().charAt(0);
		jeu.MemoriserLettreChoisie(letter);
		Vector<Integer> positions = new Vector<Integer>();
		int nbPlace = jeu.ChercherLettreDansMot(letter, positions);
		if(nbPlace == 0) {
			jeu.setNbErreurs(jeu.getNbErreurs()+1);
		}
	}
	
}
