/**
 * Created by Mushra on 2017-09-10.
 */
public class Grid {
	public final int MAX_WIDTH = 500; // arbitrary
	public final int MAX_HEIGHT = 500; // arbitrary
	public final int TILE_WIDTH = 6;
	public final int TILE_HEIGHT = 6;
	
	protected int width;
	protected int height;
	protected Tile[][] map;
	
	protected int sel_x = 0;
	protected int sel_y = 0;
	
	public Grid(int width, int height) {
		construct(width, height);
	}
	
	public Grid() {
		construct(1, 1);
	}
	
	private void construct(int width, int height) {
		setWidth(width);
		setHeight(height);
		map = new Tile[width][height];
	}
	
	/*
	
		capsules
	
	 */
	public void setTile(int x, int y, Tile newTile) {
		map[x][y] = newTile;
	}
	
	public Tile getTile(int x, int y) {
		return map[x][y];
	}
	
	public void setWidth(int width) {
		if (width > 0 && width < MAX_WIDTH) {
			this.width = width;
		}
	}
	
	public void setHeight(int height) {
		if (height > 0 && height < MAX_HEIGHT) {
			this.height = height;
		}
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeightInPixels() {
		return height * TILE_HEIGHT;
	}
	
	public int getWidthInPixels() {
		return height * TILE_WIDTH;
	}
	
	public void selLeft() {
		sel_x = (sel_x + width - 1) % width;
	}
	
	public void selRight() {
		sel_x = (sel_x + width + 1) % width;
	}
	
	public void selUp() {
		sel_y = (sel_y + height - 1) % height;
	}
	
	public void selDown() {
		sel_y = (sel_y + height + 1) % height;
	}
	
	public void setSel(int x, int y) {
		sel_x = (x + width) % width;
		sel_y = (y + height) % height;
	}
	
	public int getSelX() {
		return sel_x;
	}
	
	public int getSelY() {
		return sel_y;
	}
}
