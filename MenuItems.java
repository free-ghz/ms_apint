import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;

/**
 * Created by Mushra on 2017-09-19.
 */
public class MenuItems {
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
}
