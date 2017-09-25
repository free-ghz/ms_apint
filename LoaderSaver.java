import java.util.ArrayList;

/**
 * Created by Mushra on 2017-09-25.
 */
public class LoaderSaver {
	
	public static void saveFile(Painting painting) {
		// convert to byte array
		int place = 0;
		byte[] output = new byte[(15*34*2)+2]; // 1022
		output[place++] = 13; // haha this is a newline
		output[place++] = 37;
		for (int x = 0; x < 34; x++) {
			for (int y = 0; y < 15; y++) {
				int[] a = painting.getSpecificTilesetPos(x, y);
				output[place++] = (byte)a[0];
				output[place++] = (byte)a[1];
			}
		}
		
		// display file picker
		MenuItems.fileSaver(output);
	}
	
	public static void loadFile(Painting painting) {
		byte[] input = MenuItems.fileLoader();
		int place = 2;
		if (input != null) {
			for (int x = 0; x < 34; x++) {
				for (int y = 0; y < 15; y++) {
					painting.setTilesetPos(x, y, input[place++], input[place++]);
				}
			}
		}
	}
}
