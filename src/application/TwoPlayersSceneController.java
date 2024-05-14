package application;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class TwoPlayersSceneController {
   
    @FXML
    private Button startBtn;
    @FXML
    private Button backBtn;
    @FXML
    private TextField numTF; 
    @FXML
    private RadioButton firstRad; 
    @FXML
    private RadioButton secondRad; 
    @FXML
    private RadioButton oRad;
    @FXML
    private RadioButton xRad;
    
    private Stage stage; 
    
	public static int num;
	
	public static boolean isFirstRad;
	
	public static char firstSymbol;
	public static char secondSymbol;



    @FXML
	private void initialize() {
		TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter());
		numTF.setTextFormatter(formatter);

		// Add an event filter to the TextField to validate input
		numTF.addEventFilter(KeyEvent.KEY_TYPED, event -> {
			if (!isValidInput(event.getCharacter())) {

				// To prevent the character from being entered
				event.consume();
			}
		});
	}

	// Method to check if the input is an integer
	private boolean isValidInput(String input) {
		return input.matches("\\d"); // Allow only digits
	}
	
	
    
    @FXML
    void startBtn(ActionEvent event) throws IOException {
    	num = Integer.parseInt(numTF.getText());
    	isFirstRad = firstRad.isSelected();
    	if(xRad.isSelected()) {
    		firstSymbol = 'X';
    		secondSymbol = 'O';
    	}
    	else {
    		firstSymbol = 'O';
    		secondSymbol = 'X';
    	}
    		
		if (stage == null) {
			// If there's no stage
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		}

		// Load game scene
		Parent parent = FXMLLoader.load(getClass().getResource("TowGameScene.fxml"));

		Scene scene = new Scene(parent);

		// Set the new scene for the current stage
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void backBtn(ActionEvent event) throws IOException {
    	if (stage == null) {
			// If there's no stage
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		}
    	
		// Load table scene
		Parent parent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(parent);

		// Set the new scene for the current stage
		stage.setScene(scene);
		stage.show();

    }
}
