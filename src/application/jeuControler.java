package application;

import java.io.IOException;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class jeuControler {
	
	private double size;
	private boolean sombre;
	private OutilsController outilsController;
	private GestionJeu jeu;
	private String name;
	
	@FXML
	private GridPane root;
	@FXML
	private HBox outilsContainer;
	@FXML
	private HBox motContainer;
	
	
	jeuControler(double size, boolean sombre, String name) throws IOException{
		this.size = size;
		this.sombre = sombre;
		this.name = name;
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
		outilsController.initStyles(sombre, size, root, this.name);
        outilsContainer.getChildren().add(includedRoot);
        System.out.println(jeu.getMotMystere());
        for(int i = 0; i < jeu.getMotMystere().length(); i++) {
			Text lettre = new Text();
			lettre.setText("_");
			lettre.setFont(new Font(size*3));
			lettre.getStyleClass().add("lettres");
			motContainer.getChildren().add(lettre);
		}
	}
	
	@FXML
	public void lettre(ActionEvent event) throws IOException {
		Button source = (Button) event.getSource();
		char letter = (source).getText().charAt(0);
		letter = Character.toLowerCase(letter);
		jeu.MemoriserLettreChoisie(letter);
		Vector<Integer> positions = new Vector<Integer>();
		int nbPlace = jeu.ChercherLettreDansMot(letter, positions);
		if(nbPlace == 0) {
			source.setOnAction(null);
			jeu.setNbErreurs(jeu.getNbErreurs()+1);
			String ancien = source.getStyle();
			source.setStyle(ancien +"; -fx-background-color: #C22020;");
		}
		else {
			source.setOnAction(null);
			String ancien = source.getStyle();
			source.setStyle(ancien +"; -fx-background-color: #2CE312;");
			for(int i = 0; i < positions.size(); i++) {
				
				Text modif = (Text) motContainer.getChildren().get(positions.get(i));
				modif.setText(String.valueOf(letter));
			}
		}
		verifierVictoireDefaite();
	}
	
	public void verifierVictoireDefaite() throws IOException {
		if(jeu.getNbLettresTrouvees() == jeu.getMotMystere().length()) {
			FXMLLoader victoire = new FXMLLoader(getClass().getResource("victoire.fxml"));
			VictoireController monControleur = new VictoireController(this.sombre, this.size, this.name);
			victoire.setController(monControleur);
			Parent newRoot = victoire.load();
			Scene scene = new Scene(newRoot,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage currentStage = (Stage) root.getScene().getWindow();
			currentStage.setScene(scene);
		}
		else if(jeu.getNbErreurs() == jeu.getNbMaxErreurs()) {
			FXMLLoader victoire = new FXMLLoader(getClass().getResource("defaite.fxml"));
			VictoireController monControleur = new VictoireController(this.sombre, this.size, this.name);
			victoire.setController(monControleur);
			Parent newRoot = victoire.load();
			Scene scene = new Scene(newRoot,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage currentStage = (Stage) root.getScene().getWindow();
			currentStage.setScene(scene);
		}
	}
	
}
