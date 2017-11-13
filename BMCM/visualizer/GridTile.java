package visualizer;

import control.Controller;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Tile;

public class GridTile extends Pane {
	private Tile t;
	private ImageView v;
	private Controller c;
	private boolean _highlighted = false;

	public GridTile(Tile t, Image i, Controller c) {
		super();
		this.t = t;
		PixelReader reader = i.getPixelReader();
		int x = (int) (t.getX() * Tile.TILE_SIZE_IN_PIXELS);
		int y = (int) (t.getY() * Tile.TILE_SIZE_IN_PIXELS);
		int w = (int) (Tile.TILE_SIZE_IN_PIXELS);
		int w2 = (int) (Tile.TILE_SIZE_IN_PIXELS * Visualizer.SCALE);
		WritableImage newImage = new WritableImage(reader, x, y, w, w);
		v = new ImageView(newImage);
		v.setFitWidth(w2);
		v.setFitHeight(w2);
		this.getChildren().add(v);
		this.setOnMouseClicked(this::clicked);
		this.c = c;
	}

	public boolean isHighlighted() {
		return _highlighted;
	}

	public void highlight() {
		if (!isHighlighted()) {
			this.getChildren().clear();
			Rectangle r = new Rectangle(
			        Tile.TILE_SIZE_IN_PIXELS * Visualizer.SCALE,
			        Tile.TILE_SIZE_IN_PIXELS * Visualizer.SCALE);
			r.setFill(Color.RED);
			this.getChildren().add(r);
		}
		else {
			this.getChildren().clear();
			this.getChildren().add(v);
		}
		_highlighted = !_highlighted;
	}

	public void clicked(MouseEvent e) {
		c.addCity(t.getX(), t.getY());
	}

}
