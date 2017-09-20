import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Mushra on 2017-09-11.
 */
public class GridView extends Pane {
	private int ZOOM = 3;
	
	GridPane displayGrid = new GridPane();
	Grid grid;
	private Pane crosshair;
	
	public GridView(Grid grid, int zoom) {
		super();
		this.setPrefWidth(grid.getWidthInPixels()*ZOOM);
		this.setPrefHeight(grid.getHeightInPixels()*ZOOM);
		displayGrid.setPrefWidth(grid.getWidthInPixels()*ZOOM);
		displayGrid.setPrefHeight(grid.getHeightInPixels()*ZOOM);
		this.ZOOM = zoom;
		
		this.grid = grid;
		
		crosshair = getCrosshair();
		
		this.getChildren().add(displayGrid);
		this.getChildren().add(crosshair);
		
		Draw();
	}
	
	public void Draw() {
		displayGrid.getChildren().clear();
		for (int x = 0; x < grid.getWidth(); x++) {
			for (int y = 0; y < grid.getHeight(); y++) {
				displayGrid.add(grid.getTile(x,y), x, y);
			}
		}
		crosshair.setTranslateX(grid.getSelX()*18);
		crosshair.setTranslateY(grid.getSelY()*18);
	}
	
	private Pane getCrosshair() {
		Pane c = new Pane();
		int crossWidth = 4;
		int crosshairSize = (ZOOM*6);
		c.setPrefHeight(crosshairSize+crossWidth);
		c.setPrefWidth(crosshairSize+crossWidth);
		Rectangle upper = new Rectangle(crosshairSize+crossWidth+crossWidth, crossWidth);
		Rectangle lower = new Rectangle(crosshairSize+crossWidth+crossWidth, crossWidth);
		Rectangle left = new Rectangle(crossWidth, crosshairSize+crossWidth+crossWidth);
		Rectangle right = new Rectangle(crossWidth, crosshairSize+crossWidth+crossWidth);
		c.getChildren().addAll(upper,lower,left,right);
		upper.setTranslateX(-crossWidth);
		upper.setTranslateY(-crossWidth);
		left.setTranslateX(-crossWidth);
		left.setTranslateY(-crossWidth);
		lower.setTranslateX(-crossWidth);
		lower.setTranslateY(crosshairSize);
		right.setTranslateX(crosshairSize);
		right.setTranslateY(-crossWidth);
		left.setFill(Color.web("#888888"));
		right.setFill(Color.web("#888888"));
		upper.setFill(Color.web("#888888"));
		lower.setFill(Color.web("#888888"));
		return c;
	}
}
