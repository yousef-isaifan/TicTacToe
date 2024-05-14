package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainSceneController {

	@FXML
	private Button nextBtn;
	@FXML
	private RadioButton twoRad;
	@FXML
	private RadioButton randomRad;
	@FXML
	private RadioButton unbeatRad;
	
	private Stage stage;
	
	public static boolean two;
	public static boolean rand;
	public static boolean unbeat;


	@FXML
	private void nextBtn(ActionEvent event) throws IOException {
		two = twoRad.isSelected();
		rand= randomRad.isSelected();
		unbeat = unbeatRad.isSelected();
	    if (randomRad.isSelected() || unbeatRad.isSelected()) {
	    	if (stage == null) {
				// If there's no stage
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			}

			// Load table scene
			Parent tableViewParent = FXMLLoader.load(getClass().getResource("ComputerScene.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);

			// Set the new scene for the current stage
			stage.setScene(tableViewScene);
			stage.show();
	    } else if (twoRad.isSelected()) {
	    	if (stage == null) {
				// If there's no stage
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			}

			// Load table scene
			Parent parent = FXMLLoader.load(getClass().getResource("TwoPlayersScene.fxml"));
			Scene scene = new Scene(parent);

			// Set the new scene for the current stage
			stage.setScene(scene);
			stage.show();
	    }
	}


}
