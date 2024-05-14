package application;

import java.io.IOException;
import java.util.Random;

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

public class RandomGameSceneController {
    @FXML
    private Text playerXtxt;
    @FXML
    private Text playerOtxt;
    @FXML
    private Text drawLabel;
    @FXML
    private Text roundTxt;
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
    @FXML
    private Button homeBtn;
    

    public static char[][] board = new char[3][3];
    private int i = 0;

    private int roundCount = 1;
    private int humanWins = 0;
    private int computerWins = 0;
    private int drawCount = 0;

    private int num;

    private Stage stage;

    private Random random = new Random();

    @FXML
    void btn1(ActionEvent event) {
        handleButtons(0, 0);
    }

    @FXML
    void btn2(ActionEvent event) {
        handleButtons(1, 0);
    }

    @FXML
    void btn3(ActionEvent event) {
        handleButtons(2, 0);
    }

    @FXML
    void btn4(ActionEvent event) {
        handleButtons(0, 1);
    }

    @FXML
    void btn5(ActionEvent event) {
        handleButtons(1, 1);
    }

    @FXML
    void btn6(ActionEvent event) {
        handleButtons(2, 1);
    }

    @FXML
    void btn7(ActionEvent event) {
        handleButtons(0, 2);
    }

    @FXML
    void btn8(ActionEvent event) {
        handleButtons(1, 2);
    }

    @FXML
    void btn9(ActionEvent event) {
        handleButtons(2, 2);
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';

        num = ComputerSceneController.num + 1;

        // Check the radio button only in the first round
        if (roundCount == 1) {
            boolean isHumanStart = ComputerSceneController.isHuman;
            if (isHumanStart) {
                // Human starts
                roundTxt.setText("Round: " + roundCount);
            } else {
                // Computer starts
                roundTxt.setText("Round: " + roundCount);
                // Computer's first move using random choice
                makeRandomMove();
            }
        } else {
            if (roundCount % 2 == 0) {
                // Computer starts
                roundTxt.setText("Round: " + roundCount);
                // Computer's first move using random choice
                makeRandomMove();
            } else {
                // Human starts
                roundTxt.setText("Round: " + roundCount);
            }
        }
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

    
    @FXML
    void nextRoundBtn(ActionEvent event) {
        if (roundCount == num - 2) {
            nextRoundBtn.setDisable(true);
        }
        
        i = 0;
        roundCount++;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';

        for (int i = 0; i < pane.getChildren().size(); i++) {
            ((Button) (pane.getChildren().get(i))).setText("");
        }

        pane.setDisable(false);

        if (roundCount <= num) {
            roundTxt.setText("Round: " + roundCount);

            // Determine the starting player for this round
            boolean isHumanStart = (roundCount % 2 == 1); // Alternate starting player
            if (isHumanStart) {
                // Human starts
                roundTxt.setText("Round: " + roundCount);
            } else {
                // Computer starts
                roundTxt.setText("Round: " + roundCount);
                // Computer's first move using random choice
                makeRandomMove();
            }

            // Check for a tie after both moves
            if (i > 8) {
                updateResultLabel('D');
                pane.setDisable(true);

                if (roundCount < num) {
                    roundTxt.setText("Round: " + roundCount);
                }
                return;
            }
        }

        if (roundCount == num) {
            // Display the final results after the last round
            String winner = displayFinalResults();
            System.out.println(winner); // You can replace this with your alert code
            pane.setDisable(true);

            // Reset counts and labels for a new game
            roundCount = 1; // Resetting roundCount for new games
            humanWins = 0;
            computerWins = 0;
            drawCount = 0;
            playerXtxt.setText("0");
            playerOtxt.setText("0");
            drawLabel.setText("0");
            roundTxt.setText("Round: 1");
        }

        // Update the result label after the AI's last move
        updateResultLabel(checkWinner());
    }

    private void handleButtons(int x, int y) {
        int flag = 0;
        Button btn = selectButton(x, y);
        if (board[y][x] == ' ') {
            btn.setText("x");
            board[y][x] = 'x';
            i++;

            char result = checkWinner();
            if (result != ' ') {
                flag = 1;
                updateResultLabel(result);
                pane.setDisable(true);

                if (roundCount < num) {
                    roundTxt.setText("Round: " + (roundCount));
                }
                return;
            }

            if (i > 8) {
                if (flag == 0) {
                    updateResultLabel('D');
                    pane.setDisable(true);

                    if (roundCount < num) {
                        roundTxt.setText("Round: " + (roundCount));
                    }
                    return;
                }
            }

            // Make a random move for the computer
            makeRandomMove();

            result = checkWinner();
            if (result != ' ') {
                flag = 1;
                updateResultLabel(result);
                pane.setDisable(true);

                if (roundCount < num) {
                    roundTxt.setText("Round: " + (roundCount));
                }
                return;
            }
        }
    }

    private void makeRandomMove() {
        int x, y;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        } while (board[y][x] != ' ');

        Button btn = selectButton(x, y);
        btn.setText("o");
        board[y][x] = 'o';
        i++;
    }

    private void updateResultLabel(char result) {
        switch (result) {
            case 'x':
            	humanWins++;
                playerXtxt.setText(String.valueOf(humanWins));
                break;
            case 'o':
            	computerWins++;
                playerOtxt.setText(String.valueOf(computerWins));
                break;
            case 'D':
            	drawCount++;
                drawLabel.setText(String.valueOf(drawCount));
                break;
        }
    }

    private String displayFinalResults() {
        String winner = "";

        int maxWins = Math.max(humanWins, Math.max(computerWins, drawCount));

        if (maxWins == humanWins) {
            winner = "Human Wins: " + humanWins + "\nComputer Wins: " + computerWins;
        } else if (maxWins == drawCount) {
            winner = "It's a Tie! \nHuman Wins: " + humanWins + "\nComputer Wins: " + computerWins + "\nTie: "
                    + drawCount;
        } else {
            winner = "The Computer is The Winner! \nComputer Wins: " + computerWins + "\nHuman Wins: " + humanWins
                    + "\nTie: " + drawCount;
        }
        return winner;
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

    private Button selectButton(int x, int y) {
        Button[] buttons = { btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9 };

        for (int i = 0; i < buttons.length; i++) {
            if (x == i % 3 && y == i / 3) {
                return buttons[i];
            }
        }

        return null;
    }

    
    
//    public void setNumRounds(int num) {
//        this.num = num;
//    }

}
