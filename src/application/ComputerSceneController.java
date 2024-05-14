package application;

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

import java.io.IOException;

import javafx.event.ActionEvent;

public class ComputerSceneController {
	@FXML
	private Button startBtn;
	@FXML
	private TextField numTF;
	@FXML
	private RadioButton humanRad;
	@FXML
	private RadioButton computerRad;
	@FXML
	private RadioButton oRad;
	@FXML
	private RadioButton xRad;

	private Stage stage;

	public static int num;
	
	public static boolean isHuman;
	
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
    	if(xRad.isSelected()) {
    		firstSymbol = 'X';
    		secondSymbol = 'O';
    	}
    	else {
    		firstSymbol = 'O';
    		secondSymbol = 'X';
    	}
		isHuman = humanRad.isSelected();
		if (stage == null) {
			// If there's no stage
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		}

		// Load game scene
		Parent parent;
		if(MainSceneController.unbeat)
			parent = FXMLLoader.load(getClass().getResource("GameScene.fxml"));
		else 
			parent = FXMLLoader.load(getClass().getResource("RandomGameScene.fxml"));

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

		// Load game scene
		Parent parent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(parent);

		// Set the new scene for the current stage
		stage.setScene(scene);
		stage.show();
	}


}
