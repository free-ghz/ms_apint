import javafx.scene.image.Image;

/**
 * Created by Mushra on 2017-09-11.
 */
public class Painting extends Grid {
	int[][][] tilesetPos;
	public Painting(int width, int height, Tile wallpaper) {
		super(width, height);
		tilesetPos = new int[width][height][2];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tilesetPos[x][y][0] = -1;
				tilesetPos[x][y][1] = -1;
				setTile(x,y,wallpaper);
			}
		}
	}
	
	@Override
	public void setTile(int x, int y, Tile newTile) {
		map[x][y] = new Tile(newTile, 3);
	}
	public int[] getTilesetPos() {
		return tilesetPos[sel_x][sel_y];
	}
	public void setTilesetPos(int x, int y) {
		tilesetPos[sel_x][sel_y][0] = x;
		tilesetPos[sel_x][sel_y][1] = y;
	}
	
	public void rotateLeft() {
		// moving of the tiles
		Tile[] temp = map[0];
		for (int i = 0; i < map.length-1; i++) {
			map[i] = map[i+1];
		}
		map[map.length-1] = temp;

		// moving of the init grid
		int[][] temp2 = tilesetPos[0];
		for (int i = 0; i < tilesetPos.length-1; i++) {
			tilesetPos[i] = tilesetPos[i+1];
		}
		tilesetPos[tilesetPos.length-1] = temp2;
		
	}
	public void rotateRight() {
		// moving of the tiles
		Tile[] temp = map[map.length-1];
		for (int i = map.length-1; i > 0; i--) {
			map[i] = map[i-1];
		}
		map[0] = temp;
		
		// moving of the init grid
		int[][] temp2 = tilesetPos[tilesetPos.length-1];
		for (int i = tilesetPos.length-1; i > 0; i--) {
			tilesetPos[i] = tilesetPos[i-1];
		}
		tilesetPos[0] = temp2;
	}
	public void rotateUp() {
		// moving of the tiles
		Tile[][] newMap = new Tile[map.length][map[0].length];
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				newMap[x][(y+map[0].length-1)%map[0].length] = map[x][y];
			}
		}
		map = newMap;
		
		// moving of the init grid
		int[][][] newtilesetPos = new int[tilesetPos.length][tilesetPos[0].length][2];
		for (int x = 0; x < tilesetPos.length; x++) {
			for (int y = 0; y < tilesetPos[0].length; y++) {
				newtilesetPos[x][(y+tilesetPos[0].length-1)%tilesetPos[0].length] = tilesetPos[x][y];
			}
		}
		tilesetPos = newtilesetPos;
	}
	public void rotateDown() {
		// moving of the tiles
		Tile[][] newMap = new Tile[map.length][map[0].length];
		for (int x = 0; x < map.length; x++) {
			for (int y = -1; y < map[0].length-1; y++) {
				newMap[x][y+1] = map[x][(y+map[0].length)%map[0].length];
			}
		}
		map = newMap;
		
		// moving of the init grid
		int[][][] newtilesetPos = new int[tilesetPos.length][tilesetPos[0].length][2];
		for (int x = 0; x < tilesetPos.length; x++) {
			for (int y = -1; y < tilesetPos[0].length-1; y++) {
				newtilesetPos[x][y+1] = tilesetPos[x][(y+tilesetPos[0].length)%tilesetPos[0].length];
			}
		}
		tilesetPos = newtilesetPos;
	}
}
