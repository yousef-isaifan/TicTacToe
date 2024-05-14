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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TwoGameSceneController {
	@FXML
	private Text roundTxt;
	@FXML
	private Text playerXtxt;
	@FXML
	private Text playerOtxt;
	@FXML
	private Text drawTxt;
	@FXML
	private Button btn1;
	@FXML
	private Button btn2;
	@FXML
	private Button btn3;
	@FXML
	private Button btn4;
	@FXML
	private Button btn5;
	@FXML
	private Button btn6;
	@FXML
	private Button btn7;
	@FXML
	private Button btn8;
	@FXML
	private Button btn9;
	@FXML
	private Button nextRoundBtn;
	@FXML
	private GridPane pane;

	private boolean isPlayerXTurn = true;

	private Stage stage;

	public static char[][] board = new char[3][3];
	private int i = 0;

	private int roundCount = 1;
	private int playerXScore = 0;
	private int playerOScore = 0;
	private int drawCount = 0;
	
	
//	private char firstSybmbol = TwoPlayersSceneController.firstSymbol;
//	private char secondSybmbol = TwoPlayersSceneController.firstSymbol;

	private int num;


	@FXML
	void btn1(ActionEvent event) {
		handleButtonClick(0, 0);
	}

	@FXML
	void btn2(ActionEvent event) {
		handleButtonClick(1, 0);
	}

	@FXML
	void btn3(ActionEvent event) {
		handleButtonClick(2, 0);
	}

	@FXML
	void btn4(ActionEvent event) {
		handleButtonClick(0, 1);
	}

	@FXML
	void btn5(ActionEvent event) {
		handleButtonClick(1, 1);
	}

	@FXML
	void btn6(ActionEvent event) {
		handleButtonClick(2, 1);
	}

	@FXML
	void btn7(ActionEvent event) {
		handleButtonClick(0, 2);
	}

	@FXML
	void btn8(ActionEvent event) {
		handleButtonClick(1, 2);
	}

	@FXML
	void btn9(ActionEvent event) {
		handleButtonClick(2, 2);
	}

	@FXML
	public void initialize() {
		num = TwoPlayersSceneController.num + 1;
		 for (int i = 0; i < 3; i++)
	            for (int j = 0; j < 3; j++)
	                board[i][j] = ' ';
		 
	}


	@FXML
	void nextRoundBtn(ActionEvent event) {
		if (roundCount == num - 2)
            nextRoundBtn.setDisable(true);
		
	    resetBoard();
	    roundCount++;
	    roundTxt.setText("Round " + roundCount);

	    pane.setDisable(false);
	}


	@FXML
	void homeBtn(ActionEvent event) throws IOException {
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

	
	

	
	private void handleButtonClick(int x, int y) {
	    Button btn = selectButton(x, y);

	    if (board[y][x] == ' ' && !pane.isDisabled()) {
	        // Toggle between X and O for each click
	        if (isPlayerXTurn) {
	            btn.setText("X");
	            board[y][x] = 'X';
	        } else {
	            btn.setText("O");
	            board[y][x] = 'O';
	        }
	        i++;

	        char result = checkWinner();
	        if (result != ' ') {
	            // Update labels and disable the pane
	            updateResultLabel(result);
	            pane.setDisable(true);

	            if (roundCount < num) {
	                roundTxt.setText("Round: " + (roundCount));
	            }
	            return;
	        }

	        if (i > 8) {
	            // Update labels and disable the pane
	            updateResultLabel('D');
	            pane.setDisable(true);

	            if (roundCount < num) {
	                roundTxt.setText("Round: " + (roundCount));
	            }
	            return;
	        }

	        // Toggle player turn
	        isPlayerXTurn = !isPlayerXTurn;
	    }
	}


	private void updateResultLabel(char result) {
	    switch (result) {
	        case 'X':
	        case 'x':
	        	playerXScore++;
	            playerXtxt.setText(String.valueOf(playerXScore));
	            break;
	        case 'O':
	        case 'o':
	        	playerOScore++;
	            playerOtxt.setText(String.valueOf(playerOScore));
	            break;
	        case 'D':
	        	drawCount++;
	            drawTxt.setText(String.valueOf(drawCount));
	            break;
	    }
	}


	private Button selectButton(int x, int y) {
		Button[] buttons = { btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9 };

		for (int i = 0; i < buttons.length; i++) {
			if (x == i % 3 && y == i / 3) {
				return buttons[i];
			}
		}

		return null;
	}

	private void updateButton(int row, int col, String value) {
		// Update the corresponding button with 'X' or 'O'
		Button button;
		switch (row * 3 + col) {
		case 0:
			button = btn1;
			break;
		case 1:
			button = btn2;
			break;
		case 2:
			button = btn3;
			break;
		case 3:
			button = btn4;
			break;
		case 4:
			button = btn5;
			break;
		case 5:
			button = btn6;
			break;
		case 6:
			button = btn7;
			break;
		case 7:
			button = btn8;
			break;
		case 8:
			button = btn9;
			break;
		default:
			return;
		}
		button.setText(value);
	}

	private char checkWinner() {
		// Check rows, columns, and diagonals for a winner
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != ' ') {
				return board[i][0]; // Row winner
			}
			if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != ' ') {
				return board[0][i]; // Column winner
			}
		}

		if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != ' ') {
			return board[0][0]; // Diagonal winner
		}

		if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != ' ') {
			return board[0][2]; // Diagonal winner
		}

		return ' '; // No winner
	}


	private void resetBoard() {
		// Reset the board and clear the button texts
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = ' ';
				updateButton(i, j, "");
			}
		}
		i = 0;
	}
	
	
	
	
	
	
	
//	private void handleButtonClick(int x, int y) {
//	int flag = 0;
//	Button btn = selectButton(x, y);
//	if (board[y][x] == ' ') {
//		// Toggle between X and O for each click
//		if (isPlayerXTurn) {
//			btn.setText("X");
//			board[y][x] = 'X';
//		} else {
//			btn.setText("O");
//			board[y][x] = 'O';
//		}
//		i++;
//
//		char result = checkWinner();
//		if (result != ' ') {
//			flag = 1;
//			updateResultLabel(result);
//			pane.setDisable(true);
//
//			if (roundCount < num) {
//				roundTxt.setText("Round: " + (roundCount));
//			}
//			return;
//		}
//
//		if (i > 8) {
//			if (flag == 0) {
//				updateResultLabel('D');
//				pane.setDisable(true);
//
//				if (roundCount < num) {
//					roundTxt.setText("Round: " + (roundCount));
//				}
//				return;
//			}
//		}
//
//		result = checkWinner();
//		if (result != ' ') {
//			flag = 1;
//			updateResultLabel(result);
//			pane.setDisable(true);
//
//			if (roundCount < num) {
//				roundTxt.setText("Round: " + (roundCount));
//			}
//			return;
//		}
//
//		// Toggle player turn
//		isPlayerXTurn = !isPlayerXTurn;
//	}
//}
	
	
	
	
}
