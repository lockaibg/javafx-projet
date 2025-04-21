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
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class jeuControler {
	
	private double size;
	private boolean sombre;
	private OutilsController outilsController;
	private GestionJeu jeu;
	private String name;
	private char lettre;
	private Vector<Text> lettresAffichees = new Vector<>();
	
	@FXML
	private GridPane root;
	@FXML
	private HBox outilsContainer;
	@FXML
	private HBox motContainer;
	@FXML
	private Line p1;
	@FXML
	private Line p2;
	@FXML
	private Line p3;
	@FXML
	private Ellipse p4;
	@FXML
	private Line p5;
	@FXML
	private Line p6;
	@FXML
	private Line p7;
	@FXML
	private Line p8;
	
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
		root.setOnKeyPressed(event -> {
		        char letter = Character.toLowerCase(event.getText().charAt(0));
		        if (Character.isLetter(letter)) {
		        	try {
						traiterLettre(letter);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    });

		root.requestFocus();  
		FXMLLoader loader = new FXMLLoader(getClass().getResource("outils.fxml"));
        Parent includedRoot = loader.load();
        outilsController = loader.getController();
		outilsController.initStyles(sombre, size, root, this.name);
        outilsContainer.getChildren().add(includedRoot);
        System.out.println(jeu.getMotMystere());
        for (int i = 0; i < jeu.getMotMystere().length(); i++) {
            Text lettre = new Text("_");
            lettre.setFont(new Font(size * 3));
            lettre.getStyleClass().add("lettres");
            lettresAffichees.add(lettre);
            motContainer.getChildren().add(lettre);
        }	
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);
        p6.setVisible(false);
        p7.setVisible(false);
        p8.setVisible(false);
	}
	
	@FXML
	public void lettre(ActionEvent event) throws IOException {
		Button source = (Button) event.getSource();  
	    char letter = (source).getText().charAt(0);
	    letter = Character.toLowerCase(letter);
	    traiterLettre(letter);
	}
	
	public void traiterLettre(char lettre) throws IOException {
		jeu.MemoriserLettreChoisie(lettre);
		Vector<Integer> positions = new Vector<>();
		int nbPlace = jeu.ChercherLettreDansMot(lettre, positions);
		Button bouton = getButtonByLetter(lettre);
		if (bouton != null) {
		    if (nbPlace == 0) {
		        bouton.setStyle("-fx-background-color: #C22020;");
		    } else {
		        bouton.setStyle("-fx-background-color: #2CE312;");
		    }
		}
		
		if (nbPlace == 0) {
			jeu.setNbErreurs(jeu.getNbErreurs() + 1);
		    afficherPendu();
		    } else {
		    	for (int i = 0; i < positions.size(); i++) {
		    		Text modif = lettresAffichees.get(positions.get(i));
		    		modif.setText(String.valueOf(lettre));
		        }
		    }
		    verifierVictoireDefaite();
		    }
	
	private void afficherPendu() {
	    switch (jeu.getNbErreurs()) {
	        case 1: p1.setVisible(true); break;
	        case 2: p2.setVisible(true); break;
	        case 3: p3.setVisible(true); break;
	        case 4: p4.setVisible(true); break;
	        case 5: p5.setVisible(true); break;
	        case 6: p6.setVisible(true); break;
	        case 7: p7.setVisible(true); break;
	        case 8: p8.setVisible(true); break;
	    }
	}
	private Button getButtonByLetter(char letter) {
	    return findButtonRecursive(root, letter);
	}

	private Button findButtonRecursive(Parent parent, char letter) {
	    for (Node node : parent.getChildrenUnmodifiable()) {
	        if (node instanceof Button btn) {
	            if (btn.getText() != null && btn.getText().length() > 0 &&
	                Character.toLowerCase(btn.getText().charAt(0)) == letter) {
	                return btn;
	            }
	        } else if (node instanceof Parent child) {
	            Button found = findButtonRecursive(child, letter);
	            if (found != null) return found;
	        }
	    }
	    return null;
	}
	public void verifierVictoireDefaite() throws IOException {
		String message;
		if(jeu.getNbLettresTrouvees() == jeu.getMotMystere().length()) {
			FXMLLoader victoire = new FXMLLoader(getClass().getResource("victoire.fxml"));
			message = "Félicitations " + name;
			VictoireController monControleur = new VictoireController(this.sombre, this.size, this.name, message);
			victoire.setController(monControleur);
			Parent newRoot = victoire.load();
			Scene scene = new Scene(newRoot,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage currentStage = (Stage) root.getScene().getWindow();
			currentStage.setScene(scene);
		}
		else if(jeu.getNbErreurs() == jeu.getNbMaxErreurs()) {
			FXMLLoader victoire = new FXMLLoader(getClass().getResource("defaite.fxml"));
			message = "Dommage " + name;
			VictoireController monControleur = new VictoireController(this.sombre, this.size, this.name, message);
			victoire.setController(monControleur);
			Parent newRoot = victoire.load();
			Scene scene = new Scene(newRoot,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage currentStage = (Stage) root.getScene().getWindow();
			currentStage.setScene(scene);
		}
	}
	
}
