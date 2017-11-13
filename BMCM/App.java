import java.util.stream.IntStream;

import control.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import visualizer.Controls;
import visualizer.Visualizer;

public class App extends Application{
	public static final String HOST = "http://localhost";
	public static final String MAP_DIR = "/res/gotmapmtn.jpg";
	public static final String UNALTERED_MAP_DIR = "/res/gotmap.jpg";
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {
		BorderPane root = new BorderPane();
		Image i = new Image(HOST + MAP_DIR);
		Image unaltered = new Image(HOST + UNALTERED_MAP_DIR);
		Scene s = new Scene(root);
		Visualizer viz = new Visualizer(root, i, unaltered);
		Controller c = new Controller(viz);
		Controls conts = new Controls(viz, c);
		c.setControls(conts);
		root.setBottom(conts);
		c.process(i);
		arg0.setScene(s);
		arg0.show();
		
	}

}
