package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(root, 905, 555);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		primaryStage.setMaximized(false);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:///C:/Users/youse/Desktop/Programming/University/Algorithm/MinMaxProject/src/assets/icon.png"));
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
