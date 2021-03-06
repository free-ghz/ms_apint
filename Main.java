import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Main extends Application {
	
	Tileset tileset;
	Painting painting;
	GridView tilesetView;
	Minimap minimap;
	Image tileImage;
	
	// keypress flags
	boolean pressed_z = false;
	boolean pressed_x = false;
	boolean pressed_c = false;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		// some looks
		Pane root = new Pane();
		root.setStyle("-fx-background-color: #fff");
		Image frame = new Image("franme.png");
		ImageView paintBorder = new ImageView(frame);
		root.getChildren().add(paintBorder);
		
		// set up tileset
		tileImage = new Image("tileset.png");
		tileset = new Tileset(15, 15, tileImage);
		
		// set up tileset view
		tilesetView = new GridView(tileset, 3);
		root.getChildren().add(tilesetView);
		tilesetView.setTranslateX(666);
		tilesetView.setTranslateY(18);
		
		// set up painting pane
		painting = new Painting(34, 15, tileset.getTile(0, 0));
		GridView paintingView = new GridView(painting, 3);
		root.getChildren().add(paintingView);
		paintingView.setTranslateX(18);
		paintingView.setTranslateY(18);
		
		// set up little one
		minimap = new Minimap(painting);
		root.getChildren().add(minimap);
		minimap.setTranslateX(20);
		minimap.setTranslateY(324);
		
		// a little helper text
		Label help = new Label();
		help.setPrefWidth(290);
		help.setPrefHeight(94);
		help.setText("ms apint 32 v0.4 -- sixey.es/20494\n\nCTRL+S saves .msapint\nCTRL+O opens .msapint\nCTRL+E exports to .png");
		help.setTranslateX(652);
		help.setTranslateY(310);
		help.setAlignment(Pos.TOP_LEFT);
		root.getChildren().add(help);
		Label info = new Label();
		info.setPrefWidth(290);
		info.setPrefHeight(94);
		info.setText("ARROWS navigate image\nC+ARROWS navigate the tileset\nZ+ARROWS shifts the image\nX+ARROWS draws a line");
		info.setTranslateX(342);
		info.setTranslateY(310);
		info.setTextAlignment(TextAlignment.RIGHT);
		info.setAlignment(Pos.TOP_RIGHT);
		root.getChildren().add(info);
		
		// make a program view
		Scene scene = new Scene(root, 936, 414); // 942 x 310
		primaryStage.setResizable(false);
		primaryStage.setTitle("ms apint 32");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Saver savedKey = new Saver();
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				// check for release of our good friends z, x or c
				if (event.getCode() == KeyCode.C) {
					pressed_c = false;
				}
				if (event.getCode() == KeyCode.X) {
					pressed_x = false;
				}
				if (event.getCode() == KeyCode.Z) {
					pressed_z = false;
				}
			}
		});
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				
				// saving, etc
				if (event.isControlDown()) {
					
					// save
					if (event.getCode() == KeyCode.S) {
						LoaderSaver.saveFile(painting);
					}
					
					// load
					if (event.getCode() == KeyCode.O) {
						LoaderSaver.loadFile(painting);
						painting.resetFromTilesetPos(tileset);
					}
					
					// reset
					if (event.getCode() == KeyCode.N) {
						newImage();
					}
					
					// export image
					if (event.getCode() == KeyCode.E) {
						saveImage(minimap);
					}
				}
				
				// check for our good friends z, x or c
				if (event.getCode() == KeyCode.C) {
					pressed_c = true;
				}
				if (event.getCode() == KeyCode.X) {
					pressed_x = true;
				}
				if (event.getCode() == KeyCode.Z) {
					pressed_z = true;
				}
				
				// change tileset tile
				if (pressed_c) {
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
					
					// shift the whole image around
				} else if (pressed_z) {
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
					
					// move around in painting
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
				
				// place a tile under some conditions,
				// and also get new "saved tile"
				if (pressed_c || pressed_x) {
					painting.setTile(painting.getSelX(), painting.getSelY(), tileset.getTile(tileset.getSelX(), tileset.getSelY()));
					painting.setTilesetPos(tileset.getSelX(), tileset.getSelY());
					int[] k = {tileset.getSelX(), tileset.getSelY()};
					savedKey.save(k);
				}
				
				// save or whatever i forgot
				int[] t = painting.getTilesetPos();
				if (t[0] == - 1 || t[1] == - 1) {
					tileset.setSel(savedKey.getX(), savedKey.getY());
				} else {
					tileset.setSel(t[0], t[1]);
				}
				
				// update views
				paintingView.Draw();
				tilesetView.Draw();
				minimap.Draw();
			}
		});
	}
	
	private class Saver {
		int[] savedKey = {0, 0};
		
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
	
	// because of keys and static and stuff like that...
	// this one prolly has to use the already inited grids,
	// and poke around inside them.
	// same with load and save, i guess.
	private void newImage() {
		for (int x = 0; x < 34; x++) {
			for (int y = 0; y < 15; y++) {
				painting.setTilesetPos(x, y, - 1, - 1);
			}
		}
		painting.resetFromTilesetPos(tileset);
	}
}
