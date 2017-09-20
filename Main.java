import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;

public class Main extends Application {
	
	Tileset tileset;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
	    Pane root = new Pane();
	    
    	// set up tileset
	    Image tileImage = new Image("tileset.png");
	    tileset = new Tileset(15,15,tileImage);
	    
	    // set up tileset view
	    GridView tilesetView = new GridView(tileset, 3);
	    root.getChildren().add(tilesetView);
	    tilesetView.setTranslateX(652);
	    tilesetView.setTranslateY(20);
	    
	    // set up painting pane
	    Painting painting = new Painting(34,15, tileset.getTile(0,0));
	    GridView paintingView = new GridView(painting, 3);
	    root.getChildren().add(paintingView);
	    paintingView.setTranslateX(20);
	    paintingView.setTranslateY(20);
	    
	    // set up little one
	    Minimap minimap = new Minimap(painting);
	    root.getChildren().add(minimap);
	    minimap.setTranslateX(20);
	    minimap.setTranslateY(310);
	    
	    // a little helper text
	    Label help = new Label();
	    help.setPrefWidth(290);
	    help.setPrefHeight(94);
	    help.setText("ARROWS navigate image\nCTRL+ARROWS navigate the tileset\nSHIFT+ARROWS shifts the image\nALT+ARROWS draws a line\nSPACE saves png");
	    help.setTranslateX(652);
	    help.setTranslateY(310);
	    help.setAlignment(Pos.TOP_LEFT);
	    root.getChildren().add(help);
	    Label info = new Label();
	    info.setPrefWidth(290);
	    info.setPrefHeight(94);
	    info.setText("ms apint 32 -- sixey.es/20494\n0.2 -> added ctrl, alt and shift stuff\n0.1 -> created the thing. booya");
	    info.setTranslateX(342);
	    info.setTranslateY(310);
	    info.setTextAlignment(TextAlignment.RIGHT);
	    info.setAlignment(Pos.TOP_RIGHT);
	    root.getChildren().add(info);
	    
	    // make a program view
	    Scene scene = new Scene(root, 942,400); // 942 x 310
	    primaryStage.setResizable(false);
	    primaryStage.setTitle("ms apint 32");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	
		Saver savedKey = new Saver();
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		
		    @Override
		    public void handle(KeyEvent event) {
				if (event.isControlDown()) {
					if (event.getCode() == KeyCode.LEFT) {
						tileset.selLeft();
					}
					if (event.getCode() == KeyCode.RIGHT) {
						tileset.selRight();
					}
					if (event.getCode() == KeyCode.UP) {
						tileset.selUp();
					}
					if (event.getCode() == KeyCode.DOWN) {
						tileset.selDown();
					}
					painting.setTile(painting.getSelX(), painting.getSelY(), tileset.getTile(tileset.getSelX(), tileset.getSelY()));
					painting.setTilesetPos(tileset.getSelX(), tileset.getSelY());
					int[] k = {tileset.getSelX(), tileset.getSelY()};
					savedKey.save(k);
					
					
				} else if (event.isShiftDown()) {
					if (event.getCode() == KeyCode.LEFT) {
						painting.rotateLeft();
					}
					if (event.getCode() == KeyCode.RIGHT) {
						painting.rotateRight();
					}
					if (event.getCode() == KeyCode.UP) {
						painting.rotateUp();
					}
					if (event.getCode() == KeyCode.DOWN) {
						painting.rotateDown();
					}
				} else {
					if (event.getCode() == KeyCode.LEFT) {
						painting.selLeft();
					}
					if (event.getCode() == KeyCode.RIGHT) {
						painting.selRight();
					}
					if (event.getCode() == KeyCode.UP) {
						painting.selUp();
					}
					if (event.getCode() == KeyCode.DOWN) {
						painting.selDown();
					}
				}
			    if (event.isAltDown() || event.getCode() == KeyCode.ENTER) {
				    painting.setTile(painting.getSelX(), painting.getSelY(), tileset.getTile(tileset.getSelX(), tileset.getSelY()));
				    painting.setTilesetPos(tileset.getSelX(), tileset.getSelY());
				    int[] k = {tileset.getSelX(), tileset.getSelY()};
				    savedKey.save(k);
			    }
			    int[] t = painting.getTilesetPos();
			    if (t[0] == -1 || t[1] == -1) {
				    tileset.setSel(savedKey.getX(), savedKey.getY());
			    } else {
				    tileset.setSel(t[0], t[1]);
			    }
			    paintingView.Draw();
			    tilesetView.Draw();
			    minimap.Draw();
			    if (event.getCode() == KeyCode.SPACE) {
			    	saveImage(minimap);
			    }
		    }
	    });
    }
    
    private class Saver {
	    int[] savedKey = {0,0};
	    public Saver() {
	    }
	    public int getX() {
	    	return savedKey[0];
	    }
	    public int getY() {
	    	return savedKey[1];
	    }
	    public void save(int[] state) {
	    	savedKey = state;
	    }
	    
    }
    
    private void saveImage(Node save) {
	    MenuItems.imageSaver(save);
    }
}