/**
 * Created by Mushra on 2017-09-11.
 */

import javafx.scene.image.Image;

public class Tileset extends Grid {
	
	public Tileset(int width, int height, Image base) {
		super(width, height);
		loadTiles(base);
	}
	
	private void loadTiles(Image base) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = new Tile(base, x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
			}
		}
	}
}
