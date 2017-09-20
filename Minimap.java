import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Created by Mushra on 2017-09-11.
 */
public class Minimap extends GridPane {
	Grid grid;
	public Minimap(Grid grid) {
		super();
		this.setPrefWidth(grid.getWidthInPixels());
		this.setPrefHeight(grid.getHeightInPixels());
		
		this.grid = grid;
		
		
		Draw();
	}
	
	public void Draw() {
		this.getChildren().clear();
		for (int x = 0; x < grid.getWidth(); x++) {
			for (int y = 0; y < grid.getHeight(); y++) {
				this.add(grid.getTile(x,y).getSmall(), x, y);
			}
		}
	}
}
