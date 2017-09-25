import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Mushra on 2017-09-19.
 */
public class MenuItems {
	
	// it would be cool
	// if this one could save wherever we saved last
	// and suggest that for next time
	// both in session and maybe i guess between sessions?
	public static void imageSaver(Node save) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("pictuer (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		
		Stage stage = new Stage();
		File saveLocation;
		try {
			saveLocation = fileChooser.showSaveDialog(stage);
			if (saveLocation != null) {
				try {
					WritableImage writableImage = new WritableImage(204,94);
					save.snapshot(null,writableImage);
					RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
					// createfile
					try {
						ImageIO.write(renderedImage, "png", saveLocation);
					} catch(Exception e) {
						System.out.println("exception in file saving");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (NullPointerException npe) {
		}
	}
	
	public static void fileSaver(byte[] saveme) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("thing (*.msapint)", "*.msapint");
		fileChooser.getExtensionFilters().add(extFilter);
		
		Stage stage = new Stage();
		File saveLocation;
		try {
			saveLocation = fileChooser.showSaveDialog(stage);
			if (saveLocation != null) {
				try {
					
					// create file
					try {
						FileOutputStream fos = new FileOutputStream(saveLocation);
						fos.write(saveme);
						fos.close();
					} catch(Exception e) {
						System.out.println("exception in file saving");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (NullPointerException npe) {
		}
	}
	
	public static byte[] fileLoader() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("thing (*.msapint)", "*.msapint");
		fileChooser.getExtensionFilters().add(extFilter);
		
		Stage stage = new Stage();
		File loadLocation;
		try {
			loadLocation = fileChooser.showOpenDialog(stage);
			if (loadLocation != null) {
				try {
					try {
						byte[] input = new byte[1022];
						FileInputStream inStream = new FileInputStream(loadLocation);
						inStream.read(input);
						inStream.close();
						return input;
					} catch(Exception e) {
						System.out.println("exception in file loading");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (NullPointerException npe) {
		}
		return null;
	}
}
