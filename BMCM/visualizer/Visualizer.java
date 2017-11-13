package visualizer;


import control.Controller;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.ColorMap;
import model.Tile;

public class Visualizer {
	private BorderPane _root;
	private GridPane _mapPane;
	private GridPane _imgPane;
	private Image _img;
	private Image _unalteredImg;
	private Controller _control;
	private GridTile tiles[][];
	public static final double SCALE = 0.25;
	
	public Visualizer(BorderPane root, Image i, Image unaltered) {
		this.setRoot(root);
		_unalteredImg = unaltered;
		_mapPane = new GridPane();
		_imgPane = new GridPane();
		root.setLeft(_mapPane);
		root.setRight(_imgPane);
		_img = i;
	}
	public void initializeGridPane(Tile[][] ti, Controller c) {
		_control = c;
		tiles = new GridTile[c.getWidth()][c.getHeight()];
		for (int i = 0; i < ti.length; i++) {
			for (int j = 0; j < ti[0].length; j++) {
				GridTile t = new GridTile(ti[i][j], _unalteredImg, c);
				_imgPane.add(t, i, j);
				tiles[i][j] = t;
			}
		}
		
		//_imgPane.setHgap(1);
		//_imgPane.setVgap(1);
		_imgPane.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		//_mapPane.setHgap(1);
		//_mapPane.setVgap(1);
		_mapPane.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		
	}
	
	public void displayColorMap(ColorMap c, int x, int y) {
		_mapPane.getChildren().clear();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				Rectangle r = new Rectangle(Tile.TILE_SIZE_IN_PIXELS*Visualizer.SCALE, Tile.TILE_SIZE_IN_PIXELS*Visualizer.SCALE);
				r.setFill(c.getColor(i, j));
				Pane p = new StackPane();
				p.getChildren().add(r);
				Label l = new Label(String.format("%.2f", c.getValue(i, j)));
				l.setFont(Font.font(Tile.TILE_SIZE_IN_PIXELS/4*Visualizer.SCALE));
				p.getChildren().add(l);
				_mapPane.add(p, i, j);
			}
		}
	}
	private void setRoot(BorderPane root) {
		_root = root;
	}
	public Node getRoot() {
		return _root;
	}
	
	private void setImage(Image i) {
		_img = i;
	}
	public Image getImage() {
		return _img;
	}
	public void toggleCity(int x, int y) {
		tiles[x][y].highlight();
	}
	
	
	
}
