import com.sun.javafx.sg.prism.NGImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.sun.javafx.sg.prism.NGNode;

import com.sun.prism.Graphics;
import com.sun.prism.Texture;
import com.sun.prism.impl.BaseResourceFactory;
/**
 * Created by Mushra on 2017-09-10.
 */
public class Tile extends ImageView {
	private Tile liten;
	private int ofs_x;
	private int ofs_y;
	private int width;
	private int height;
	public Tile(Image image, int offset_x, int offset_y, int width, int height) {
		super(image);
		this.width = width;
		this.height = height;
		this.ofs_x = offset_x;
		this.ofs_y = offset_y;
		Rectangle2D crop = new Rectangle2D(offset_x, offset_y, width, height);
		this.setViewport(crop);
		this.setFitHeight(6*3);
		this.setFitWidth(6*3);
	}
	public Tile(Tile copyme, int scale) {
		super(copyme.getImage());
		Rectangle2D crop = new Rectangle2D(copyme.ofs_x, copyme.ofs_y, 6, 6);
		this.ofs_x = copyme.ofs_x;
		this.ofs_y = copyme.ofs_y;
		this.setViewport(crop);
		this.setFitHeight(6*scale);
		this.setFitWidth(6*scale);
	}
	
	public Tile getSmall() {
		return new Tile(this,1);
	}
	
	
	// stack overflow-hack, thanks Martin Sojka
	// this removes smoothing.
	@Override protected NGNode impl_createPeer() {
		return new NGImageView() {
			private com.sun.prism.Image image;
			
			@Override public void setImage(Object img) {
				super.setImage(img);
				image = (com.sun.prism.Image) img;
			}
			
			@Override protected void renderContent(Graphics g) {
				BaseResourceFactory factory = (BaseResourceFactory) g.getResourceFactory();
				Texture tex = factory.getCachedTexture(image, Texture.WrapMode.CLAMP_TO_EDGE);
				tex.setLinearFiltering(false);
				tex.unlock();
				super.renderContent(g);
			}
		};
	}
	
	
}
