package util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import model.Tile;

public class ImageUtils {
	public static void toPixelArray(Image i, Tile[][] t, int mx, int my, int tile_width) {
		PixelReader r = i.getPixelReader();
		int imgWidth = (int) i.getWidth();
		int imgHeight = (int) i.getHeight();
		for (int x = 0; x < mx; x++) {
			for (int y = 0; y < my; y++) {
				t[x][y] = new Tile(x, y, 0, 0, 0, 0);
				for (int yi = 0; yi < tile_width; yi++) {
					for (int xi = 0; xi < tile_width; xi++) {
						
						Color c = r
						        .getColor(x * tile_width + xi, y * tile_width + yi);
						t[x][y].getRawArray()[xi][yi] = c;
						if (c == null) {
							throw new RuntimeException("NULL COLOR");
						}
					}
				}
			}
		}
	}
}
